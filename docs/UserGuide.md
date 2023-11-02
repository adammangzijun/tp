---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# User Guide

Medbook is a **desktop application** crafted specifically for **doctors** and **medical administrative assistants** to manage patient details and medical records.

Our address book offers a streamlined interface for tracking patient details and medical records, ensuring that vital information is organized, accessible, and secure at all times.

<!-- * Table of Contents -->
<page-nav-print />

---

## How to Navigate the User Guide

This user guide serves to provide clear instructions and insights, empowering users to effectively utilize all features and functionalities with confidence and ease.

The table of contents can be used at the side to seamlessly jump between sections.

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.
   - [How do I check my version of Java?](#faq)
2. Download the latest jar file (`medbook.jar`) from [here](https://github.com/AY2324S1-CS2103T-T12-4/tp/releases).

3. Move the file to the folder you want to use as the _home folder_ for your MedBook.

4. Open a command terminal
   - For Windows Users:
     - Press the Windows icon and search for and open up`Powershell`.
   - For Mac Users:
     - Open a new Terminal window (Command + Space > type Terminal > Enter).

Navigate to the folder you put the jar file in using `cd` and use the `java -jar medbook.jar` command to run the application.

The application should open up similar to the image below.
![Ui](images/MedBookUi.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   - `list` : Lists all patients.

   - `addpatient n/John Doe i/T1234567A e/johndoe@gmail.com p/12345678 g/M a/26 bt/AB+ al/Penicillin` : Adds a new patient named John Doe with important information about the patient

   - `delete 3` : Deletes the 3rd patient shown in the current list.

   - `clear` : Deletes all patient.

   - `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

---

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

- Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

- Items in square brackets are optional.<br>
  e.g `n/NAME [al/ALLERGIES]` can be used as `n/John Doe al/Pencillin` or as `n/John Doe`.

- Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[al/ALLERGIES]…​` can be used as ` ` (i.e. 0 times), `al/Pencillin`, `al/Pollen al/Dust` etc.

- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

- Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- **PATIENTID**: refers to the unique patient ID shown in the displayed patients list. The ID **must be a positive integer** 1, 2, 3, …​ <br>

- **RECORDID**: refers to the unique record ID shown in the displayed **RECORDS** list of a specific patient. The ID **must be a positive integer** 1, 2, 3, …​ <br>

- **APPOINTMENTID**: refers to the unique appointment ID shown in the displayed **APPOINTMENTS** list. The ID **must be a positive integer** 1, 2, 3, …​ <br>

- **PINNEDID**: refers to the unique ID shown in the displayed **PINNED PATIENT** list. The ID **must be a positive integer** 1, 2, 3, …​ <br>

### PARAMETERS

| FIELD        | PREFIX | CONSTRAINTS                                                                                                      |
|--------------|--------|------------------------------------------------------------------------------------------------------------------|
| **NAME**     | n      | Up to 256 alphanumeric characters                                                                               |
| **NRIC**     | i      | Starts with a letter, followed by seven digits, and ends with another letter (letters are not case-sensitive) |
| **EMAIL**    | e      | Valid email format: **email@domain.com**                                                                      |
| **GENDER**   | g      | Either **M** or **F**                                                                                         |
| **PHONE**    | p      | Must be 8 digits                                                                                                |
| **AGE**      | a      | Must be a positive integer                                                                                      |
| **BLOODTYPE**| bt     | Must be one of **A-**, **A+**, **B-**, **B+**, **AB-**, **AB+**, **O-**, **O+**                             |
| **ALLERGY**  | al     | Up to 256 alphanumeric characters                                                                               |
| **DATE**     | d      | Must be in the form of 'dd-MM-yyyy HHmm'                                                                       |
| **CONDITION**| c      | Up to 256 alphanumeric characters                                                                               |
| **MEDICATION**| m     | Up to 256 alphanumeric characters                                                                               |

### Viewing help : `help`

Shows a message explaning how to access the help page.

Format: `help`

### Adding a patient: `addpatient`

Adds a patient's contact and medical details.

Format: `addpatient n/NAME e/EMAIL p/PHONE g/GENDER a/AGE bt/BLOODTYPE [al/ALLERGY]...`

- Adds a patient with information including: **NAME**, **NRIC**, **EMAIL**, **GENDER**, **PHONE**, **AGE**, **BLOODTYPE**, **ALLERGY**.

Examples:

`addpatient n/John Doe i/T1234567A e/johndoe@gmail.com p/12345678 g/M a/26 bt/AB+ al/Penicillin`
Adds a **Male** patient named **John Doe** whose NRIC is **T1234567A** , **26** years old, has **AB+** Blood Type, and is allergic to **Penicillin**.
His email and phone number is **johndoe@gmail.com** and **12345678**, respectively.

### Adding Medical Records : `addrecord`

Adds a patient's medical record.

Format: `addrecord PATIENTID d/DATETIME c/CONDITIONS... m/MEDICATION...`

- Adds a medical record to the patient with the corresponding **PATIENTID**. <br>

- Constraints of each fied are as stated in the **CONSTRAINTS** section above. <br>

Examples:

- `addrecord 3 d/18-09-2023 1800 c/Fever m/Paracetamol`
  Adds a record to the patient with the **PATIENTID** of **3**.
  The record says that the patient visited the clinic on **September 18th, 2023** at **6PM** with a **Fever** and was prescribed **Paracetamol**.

### Adding an Appointment : `addappointment`

Adds an appointment.

Format: `addappointment PATIENTID n/NAME d/DATETIME`

- Adds an appointment to the patient with the corresponding **PATIENTID**.

-The appointment will then appear in the **APPOINTMENTS** list with patient's **NRIC**. <br>

- Constraints of each fied are as stated in the **CONSTRAINTS** section above. <br>

Examples:

- `addappointment 2 n/Eye Examination d/10-10-2023 1800` adds an **Eye Examination** to the **2nd patient** in the patients list. The appointment is set on **10th October 2023** at **6:00pm**.

### Listing all patients : `list`

Shows a list of all patients in the Medbook.

Format: `list`

- **All** patients in the Medbook will always be visible on the screen in the **MIDDLE** column.

Examples:

- `list` lists all the patients in the **MIDDLE** column.

### View patient records : `view`

Displays the medical records of the specified patient in the **RECORDS** list

Format: `view PATIENTID`

- The medical records of the patient with the corresponding `PATIENTID` will be displayed on screen in the **RECORDS** list. <br>

- Information about the patient will be displayed in the **PATIENT BEING VIEWED** section. <br>

Examples:

- `view 2` displays the medical records of the **2nd patient** in the patients list. The displayed records will be in the **RECORDS** list and patient information will be in the **PATIENT BEING VIEWED** section.

### View appointments : `viewappointment`

Displays all the medical appointments in a separate window.

Format: `viewappointment`

- The appointments of all the patients in the patients list will be displayed on a new window in the **APPOINTMENTS** list. <br>

- The **description**, **date and time**, and **NRIC** of the patient involved in appointment will be displaye in the **APPOINTMENTS** list. <br>

- The **description** of appointments will also appear on the calendar at the corresponding **date** . <br>
**note**: Only the **first two** appointments according to the **APPOINTMENTS** list will be displayed on the calendar per date.

### Editing detail of a patient : `editpatient`

Edits the detail of an existing patient in MedBook.

Format: `editpatient PATIENTID PREFIX/NEWVALUE...`

- Edits the patient at the specified `PATIENTID`. <br>

- Acceptable fields : **name**, **email**, **phone**, **gender**, **age**, **bloodtype**, **allergy**. <br>
**note**: NRIC can't be changed.

- Existing values in the respective fields will be updated to the **NEWVALUE** input. <br>

- Constraints of each fied are as stated in the **CONSTRAINTS** section above. <br>

Examples:

- `editdetail 1 e/johndoe_updated@gmail.com` Edits the email address of the 1st patient to be **johndoe_updated@gmail.com**. <br>

- `editdetail 2 p/92345678` Edits the name of the 2nd patient to be **92345678**. <br>

- `editdetail 3 al/aspirin` Edits the allergy of the 3rd patient to be **aspirin**. <br>

- `editdetail 2 p/92345678 al/aspirin` Edits the name and allergy of the 2nd patient to be **92345678** and **aspirin** respectively. <br>

### Editing a record of a patient : `editrecord`

Edits a record of an existing patient in MedBook.

Format: `editrecord PATIENTID/RECORDID PREFIX/NEWVALUE...`

- Edits a record at the specific **RECORDID** of the patient at the specified **PATIENTID**. <br>

- Acceptable fields : **date** , **condition**, **medication**. <br>

- Existing values in the field will be updated to the **NEWVALUE** input. <br>

- Multiple entries of date fields are not allowed. <br>
S
- Multiple entries of condition/medication fields are allowed to represent a new set of conditions. <br>
- Constraints of each fied are as stated in the **CONSTRAINTS** section above. <br>

Examples:

- `editrecord 1/1 d/25-10-2023 1200` Edits the dateTime of the 1st record of the 1st patient to be **25-10-2023 1200**. <br>

- `editrecord 1/1 c/Headache c/Flu` Edits the conditions of the 1st record of the 1st patient to be **Headache, Flu**. <br>

- `editrecord 1/1 c/Headache m/Paracetamol` Edits the conditions and medications of the 1st  of the 1st patient to be **Headache** and **Paracetamol** respectively. <br>

### Locating patients by keywords: `search`

Searches the patient with the corresponding **KEYWORD**

Format: `search KEYWORD...`

- The search is not case-sensitive. e.g **Penicillin** will match **penicillin**. <br>

- A patient's name and details will be searched. <br>
**note**: This does not include searching through the details of records.

- Patients matching at least one keyword will be returned. <br>

- Patients with detail that are not exactly the same as the `KEYWORD` searched will not appear. <br>

Examples:

- `search Alex` returns a list of patients with **Alex** in their details. <br>

- `search Alex M` will return patients with **Alex** or **M** in their details.

### Locating records by keywords: `searchrecord`

Searches the record with the corresponding **KEYWORD**

Format: `searchrecord KEYWORD...`

- The search is not case-sensitive. e.g **Penicillin** will match **penicillin**. <br>

- A record's details will be searched. <br>

- Records matching at least one **KEYWORD** will be returned. <br>

- Records with detail that are not exactly the same as the **KEYWORD** searched will not appear. <br>

Examples:

- `searchrecord Penicillin` returns a list of records with **Penicillin** in their details. <br>

- `searchrecord Fever Cough` will return records with **Fever** or **Cough** in their details. <br>

- `searchrecord 19-10-2023` will return records with date **19-10-2023** in their details. <br>

### Deleting a patient : `delete`

Deletes the specified patient.

Format: `delete PATIENTID`

- Deletes the patient at the specified **PATIENTID**.

Examples:

- `delete 2` deletes the **2nd patient** from the patients list.

### Deleting a record : `deleterecord`

Deletes the specified record of the patient.

Format: `deleterecord PATIENTID/RECORDID`

- Deletes the record at the specific **RECORDID** of the patient at the specified **PATIENTID**.

Examples:

- `deleterecord 2/1` deletes the **1st record** of the **2nd patient** in the patients list.

### Deleting an appointment : `deleteappointment`

Deletes an appointment from the **APPOINTMENTS** list.

Format: `deleteappointment APPOINTMENTID`

- Deletes the appointment at the specific **APPOINTMENTID** in the **APPOINTMENTS** list.

Examples:

- `deleteappointment 1` deletes the **1st appointment** in the **APPOINTMENTS** list.

### Pinning a Patient : `pin`

Pins the specified patient to the **PINNED PATIENT** list.

Format: `pin PATIENTID`

- The pinned patient will always be visible on screen in the **PINNED PATIENT** list.

Examples:

- `pin 2` pins the **2nd patient** in the patients list to the **PINNED PATIENT** list.

### Unpinning a Patient : `unpin`

Unpins the specified patient from the **PINNED PATIENT** list.

Format: `unpin PINNEDID`

- Removes the patient at the specified **PINNEDID** from the **PINNED PATIENT** list.

Examples:

- `unpin 2` unpins the **2nd patient** in the **PINNED PATIENT** list.

### Clearing all entries : `clear`

Clears all entries from the medbook.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

MedBook data is securely encrypted and saved to the hard disk automatically after any command that changes the data. This ensures the privacy and safety of your data. When MedBook is restarted, the data is decrypted and loaded back into the application.

### Editing the data file

MedBook data are saved automatically as a JSON file [JAR file location]/data/medbook.json. As the data is encrypted, users will not be able edit this files to directly make changes to the data files. This also prevents unauthorised access of the system.

<box type="warning" seamless>
Caution:
Do not make changes to the data file.
</box>

## FAQ

**Q**: How do I check my java version?<br>
**A**: Open a command prompt and type `java -version`. If you do not have Java installed, you can download it [here](https://www.oracle.com/java/technologies/downloads/#java11).

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and copy the data folder over to the same location in the other computer.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

---

## Command summary


| Action              | Format, Examples                                                                                                                       |
| ------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| **Help**     | `help` |
| **Add Patient**     | `addpatient n/NAME i/NRIC e/EMAIL p/PHONE …​` <br> e.g., `addpatient n/John Doe i/T1234567A e/johndoe@gmail.com p/12345678 g/M a/26 bt/AB+ al/Penicillin` |
| **Add Record**      | `addrecord PATIENTID d/DATETIME c/CONDITION... m/MEDICATION...`<br> e.g., `addrecord 2 d/10-10-2020 1900 c/Fever m/Painkiller`       |
| **Add Appointment** | `addappointment PATIENTID n/APPOINTMENTNAME d/DATETIME`<br> e.g., `addappointment 2 n/Eye Exam d/10-10-2020 1900`                      |
| **List**            | `list`                                                                                                |
| **View Records**            | `view PATIENTID`<br> e.g., `view 2`                                                           |
| **View Appointments**            | `viewappointment`                                                                        |
| **Edit Patient** | `editpatient PATIENTID PREFIX/NEWVALUE...`<br> e.g.,`editpatient 1 e/johndoe_updated@gmail.com`           |
| **Edit Record**  | `editrecord PATIENTID/RECORDID PREFIX/NEWVALUE...`<br> e.g.,`editrecord 1/1 d/25-10-2023 1200`            |
| **Search Patients**          | `search KEYWORDS...`<br> e.g., `search James`                                           |
| **Search Records**          | `searchrecord KEYWORDS...`<br> e.g., `searchrecord Headache`                                  |
| **Delete Patient**          | `delete PATIENTID`<br> e.g., `delete 3`                                                       |
| **Delete Record**          | `deleterecord PATIENTID/RECORDID`<br> e.g., `delete 2/1`                                       |
| **Delete Appointment**          | `deleteappointment APPOINTMENTID`<br> e.g., `deleteappointment 1`                         |
| **Pin**             | `pin PATIENTID`<br> e.g., `pin 2`                                                                     |
| **Unpin**           | `unpin PINNEDID`<br> e.g. `unpin 2`                                                                  |
| **Clear**           | `clear`                                                                                               |
| **Exit**            | `exit`                                                                                                |

