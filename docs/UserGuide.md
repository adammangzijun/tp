---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# User Guide

## Streamline Patient Management and Medical Records with Just a Few Taps!

Medbook is a **desktop application** crafted specifically for **doctors** and **medical administrative assistants** of private clinics to manage patient details and medical records.

Medbook provides a streamlined and user-friendly interface that empowers doctors and medical administrative assistants to effortlessly track patient details and medical records. This ensures that critical information is well-organized, easily accessible, and fully secure for your peace of mind.

Here's how Medbook can streamline your tasks:

- Quickly save, edit, and remove patient details.
- Effortlessly handle patient appointments and records.
- Search for specific information in a snap.
- Get a comprehensive view of patient data, medical records, and appointments at a glance.

Discover the power of Medbook:

- **Digitalize Your Clinic**: Effortlessly transition to a digital clinic with our user-friendly features.
- **Optimized for Speed**: Medbook offers both a Command Line Interface (CLI) and an intuitive Graphical User Interface, making it ideal for fast typists like you.
- **QuickStart**: New to Medbook? Click [here](#quick-start-) to start your journey.

<!-- * Table of Contents -->
<page-nav-print />

---


## How to Navigate the User Guide

Welcome to the Medbook User Guide! Our goal is to empower you with the knowledge and confidence to make the most of Medbook's features.

- **Effortless Navigation**: Use the table of contents for seamless navigation between sections.
- **Quick Start for New Users**: If you're new to Medbook, start with the [Quick Start](#quick-start-) section to get up and running.
- **Explore Features**: For those who have already set up Medbook, dive into the [Features](#features) section to discover all that our application has to offer.

We're here to make your experience as user-friendly as possible. Let's get started!

## Quick start 

1. Ensure you have Java `11` or above installed in your Computer.
   - [How do I check my version of Java?](#faq)
1. Download the latest jar file (`medbook.jar`) from our [github release](https://github.com/AY2324S1-CS2103T-T12-4/tp/releases).

1. Move the jar file to the folder you want to store the data of the patients.

    - Create a folder with your preferred name (you can call it Medbook) where you would like to house the application.
    - Move the downloaded jar file to the folder as shown below.
      - For Mac users:![Ui](images/MacUserDir.png)
      - For Windows users:![Ui](images/WinUserDir.png)

1. Run Medbook application

    - **On Windows**:
      - Double-click medbook.jar to run the application.
      - If the GUI doesn't show up, try the following operations.
      - **Open PowerShell**: Search for "Powershell" in the Windows search bar and open it.
      - Navigate to the folder where you have stored the jar file using the cd command.
      - Enter java -jar medbook.jar and press Enter. This command will execute the application.<br> 
      <box type="info" seamless>
        Please note that for Windows users, you can navigate to the folder where you house Medbook. In the file address bar, click on the path to the current folder to highlight it. Type powershell and press enter. This will open a PowerShell window with the current folder as the working directory.
      </box>
   
    - **On Mac**:
      - **Open Terminal**: You can find Terminal in the "Utilities" folder within the "Applications" folder. Or, you can use Spotlight Search by pressing Command + Spacebar and typing "Terminal."
      - Navigate to the folder where you have stored the jar file using the cd command
      - Run the Application: Enter java -jar medbook.jar and press Enter. This command will execute the application.<br>
      <box type="info" seamless>
        Please note that for Mac users,  in the Terminal on a Mac, you can also drag and drop the "medbook.jar" file directly into the Terminal window after typing `java -jar` to auto-fill the file path.
      </box>

The application should open up similar to the image below.
![Ui](images/MedBookUi.png)
1. Learn more about navigating the GUI [here](#navigating-the-graphical-user-interface-gui).
2. For new users, learn how to use Medbook [here](#medbook-tutorial).
3. For advanced users, view all the available features [here](#features).

## Glossary

### Definitions

Here are some description of the terms used throughout this user guide.

| **Term**      | **Definition**                                                                                                                                                                  |
|---------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Parameter     | A parameter allow you to customize the behavior of the command by providing specific values                                                                                     |
| Command       | A command is a textual input that users type into the command-line interface to communicate with the application                                                                |
| GUI           | GUI is graphical user interface. It manages the interaction between user and the application and also gives a visual display of Medbook.                                        |
| GUI component | GUI components are the interactive parts of the application that you can see and interact with. For more information of different GUI components. Please refer to below section |
| CLI           | CLI or Command Line Interface,  is a text-based way to interact with the application.                                                                                           |

## Navigating the Graphical User Interface (GUI)

![Ui Component](images/UiComponent.png)

Here is a brief summary of GUI component of the main window of Medbook.

| **Name of component**    | **Description**                                                                                             |
|--------------------------|-------------------------------------------------------------------------------------------------------------|
| Menu Bar                 | Contains the help and file drop down menu for Medbook                                                       |
| Message Display Box      | Display the message of every command. Either can be an error message or success message                     |
| Patient List             | Contains all the Patient cards stored in Medbook                                                            |
| Patient information card | Contains information of the Patient including age, blood type, gender, contact information and allergies    |
| Patient being viewed     | Contains the patient card of currently being viewed patient                                                 |
| Medical record List      | Contains all the Record card of the patient currently being viewed                                          |
| Record card              | Contains information of the medical record including appointment date, condition, and prescribed medication |
| Pinned Patient List      | Contains all the patient cards after command pin                                                            |
| Command Input Box        | Allow users to input commands                                                                               |


![Appointment Window](images/ViewAppointmentWindow.png)

| **Name of component** | **Description**                                                           |
|-----------------------|---------------------------------------------------------------------------|
| Appointment card      | Contains information of appointments including name, date and NRIC number |
| Appointment list      | Holds a list of Appointment card                                          |
| Calendar              | A calendar that displays appointments at certain date                     |
| Previous Button       | Shift the month shown in the calendar to previous month                   |
| Next Button           | Shift the month shown in the calendar to next month                       |

## Medbook Tutorial

1. Launch the Medbook application. You may refer to [here](#quick-start-).
   - Note on launch, Medbook will populate some sample data.

2. Let us try invoking the help window. Type help in the command box and press Enter to execute it. 
Please refer to the first section of [feature](#features) to have a more clear understanding of requirements of the parameters. 
A similar help box as below should pop up.<br> <img src="images/HelpWindow.png" width="300"/>
3. Now, let us try adding a new patient. In the command box, type in `addpatient n/John Doe e/johndoe@gmail.com p/12345678 g/M a/26 bt/AB+ al/Penicillin` and press Enter. 
A success message should be displayed.<br><img src="images/AddPatientSuccess.png" width="500"/>
4. Try adding a few more patient to Medbook by yourself!
5. Now, let us try adding a record to a patient. In the command box, type in `addrecord 3 d/18-09-2023 1800 c/Fever m/Paracetamol` and press Enter. 
This will create a new medical record to the third patient shown in the middle column. 
A similar message output should be shown in the Message Display Box.<br> <img src="images/AddRecordSuccess.png" width="500">
6. Try adding a few more records to patients in the Medbook by yourself!
7. Let us view medical records of the first patient. Key in `view 1` to see all the records of the first patient.
If you haven't added any medical records to the first patient, a similar **Record List** should be shown. 
<br><img src="images/View.png" height="300"/>

You may now have a basic understanding of this software. Try out the features in the following section on your own and explore the potential of Medbook!

<box type="info" seamless>
    Take a moment to review these notes on the command format, and it will help you navigate and use Medbook more smoothly!
</box>


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

