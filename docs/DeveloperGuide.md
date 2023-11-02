---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# MedBook Developer Guide
## Acknowledgements

This project has benefited from the use of several third-party libraries, as well as ideas and code snippets from various sources. We express our gratitude to the creators and maintainers of these resources.

### Java Cryptography Extension (JCE)

The project utilizes the Java Cryptography Extension (JCE) for implementing encryption and decryption functionalities. Specific classes such as `Cipher`, `IvParameterSpec`, and `SecretKeySpec` have been used to perform cryptographic operations.

- `javax.crypto.Cipher`: This class provides the functionality of a cryptographic cipher, used here for encryption and decryption.
- `javax.crypto.spec.IvParameterSpec`: This class specifies an initialization vector (IV) to provide an additional parameter to algorithms.
- `javax.crypto.spec.SecretKeySpec`: This class specifies a secret key in a provider-independent fashion.

These classes are part of the Java Cryptography Extension (JCE) framework, which is included in the Java Standard Edition (Java SE) from Oracle. More information can be found on the [Official Oracle Documentation](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/javax/crypto/Cipher.html).

We ensure that all used and redistributed libraries are credited appropriately, and we encourage users and developers to also adhere to the terms and conditions of each library's license.

---

## Setting up and Getting Started

For initial setup and getting started with the development, please refer to the guide: [_Setting up and getting started_](SettingUp.md).

---

## Design Overview

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" alt="Architecture Diagram"/>

The **_Architecture Diagram_** above provides a high-level design overview of the application.

Below is a quick overview of the main components and their interactions:

#### Main Components:

- **`Main`**: Responsible for initializing other components in the correct sequence upon application launch, and ensures proper shut down procedures are followed. It consists of [`Main`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/MainApp.java) classes.

- **`UI`** ([Details](#ui-component)): Manages the User Interface of the app.
- **`Logic`** ([Details](#logic-component)): Handles command execution.
- **`Model`** ([Details](#model-component)): Manages in-memory data.
- **`Storage`** ([Details](#storage-component)): Handles reading from and writing to the hard disk.

- **`Commons`**: A collection of classes used by multiple components.

#### Component Interactions:

The sequence diagram below shows the interactions between components for the `delete 1` command:

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" alt="Architecture Sequence Diagram" />

Each of the four main components:

- Defines its API in an interface named after the component.
- Implements its functionality using a `{Component Name}Manager` class, following the corresponding API interface.

For example, the `Logic` component's API is defined in `Logic.java`, and its functionality is implemented in `LogicManager.java`.

#### Detailed Component Descriptions:

[More details about each component are provided in the subsequent sections.](#detailed-component-descriptions)

---

### Detailed Component Descriptions

#### UI Component

- **API**: [`Ui.java`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)
- The UI component is responsible for handling all user interface operations.

<details>
<summary>Click to expand details</summary>

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI is composed of various components such as `MainWindow`, `CommandBox`, `ResultDisplay`, `PersonListPanel`, and `StatusBarFooter`, all of which inherit from the `UiPart` class.

The UI layouts are defined in corresponding `.fxml` files located in the `src/main/resources/view` folder.

Key responsibilities include:

- Executing user commands via the `Logic` component.
- Listening for data changes in the `Model` and updating the UI accordingly.
- Maintaining a reference to the `Logic` component for command execution.
- Depending on certain `Model` classes to display `Person` objects.

</details>

---

#### Logic Component

- **API**: [`Logic.java`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)
- The Logic component is responsible for interpreting and executing user commands.

<details>
<summary>Click to expand details</summary>

Here's a partial class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550" alt="Logic Component Class Diagram"/>

Key functionalities include:

- Interpreting user input and generating the corresponding `Command` object.
- Executing the command and generating a `CommandResult` object.
- Depending on the `Model` component to perform data operations.
- Managing various command parsers to handle specific command formats.

</details>

---

#### Model Component

- **API**: [`Model.java`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)
- The Model component manages the application's in-memory data.

<details>
<summary>Click to expand details</summary>

<puml src="diagrams/ModelClassDiagram.puml" width="450" alt="Model Component Class Diagram"/>

Key responsibilities include:

- Storing address book data and selected `Person` objects.
- Managing user preferences.
- Providing an unmodifiable view of lists of `Person` objects for UI binding.

</details>

---

#### Storage Component

- **API**: [`Storage.java`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)
- The Storage component manages data persistence.

<details>
<summary>Click to expand details</summary>

<puml src="diagrams/StorageClassDiagram.puml" width="550" alt="Storage Component Class Diagram"/>

Key functionalities include:

- Saving and retrieving address book data and user preferences in JSON format.
- Implementing both `AddressBookStorage` and `UserPrefStorage` for flexibility.
- Depending on certain `Model` classes for data object definitions.

</details>

---
## Common Classes

Classes used by multiple components are housed in the `seedu.addressbook.commons` package.

---

## Implementation

This section delves into the implementation details of various noteworthy features.

### Records Feature

#### General Implementation Details

<puml src="diagrams/RecordClassDiagram.puml"/>

A `Record` object encapsulates various attributes:

- `DateTime`: Date and time of the patient's clinic visit
- `List<Condition>`: Patient's health conditions
- `List<Medication>`: Medications prescribed to the patient

Uniqueness of records is maintained through the `UniqueRecordList`.

#### Adding a Record

##### Overview

The `addrecord` command integrates a new `Record` object with the patient's details in MedBook.

#### Implementation Steps

1. **Parse User Input**: `AddRecordCommandParser` checks for necessary parameters and their validity.
2. **Create Record Object**: A `Record` object is instantiated and handed over to the `AddRecordCommand`.
3. **Execute Command**: `AddRecordCommand#execute()` adds the new `Record` to the patient's `UniqueRecordList`.

<puml src="diagrams/AddRecordSequenceDiagram.puml" width="450" />

#### Design Considerations

**Aspect: Structure of the Appointment class:**

- **Alternative 1 (Current Choice)**: Each `Person` object holds a `UniqueRecordList`.
   - *Pros*: Simplifies the retrieval of a person's records.
   - *Cons*: Fetching records across all individuals can be cumbersome.
- **Alternative 2**: The `Model` holds a `UniqueRecordList` for records of all patients.
   - *Pros*: Convenient for displaying all records.
   - *Cons*: Hard to fetch records associated with a specific `Person`.

### Edit Patient Feature

The `editpatient` mechanism is primarily handled by `EditCommand`.

#### Workflow

1. **Initialization**: On startup, the `AddressBook` is populated with sample data.
2. **Execution**: The user modifies a patient’s details using the `editpatient` command, triggering updates in the `Model` and `AddressBook`.
3. **Update**: The patient’s details are updated and the new AddressBook is displayed.

**Related Classes and Methods:**

- `EditCommandParser`: Parses command input.
- `EditPersonDescriptor`: Holds editable patient details.
- `ModelManager#setPerson(Person,Person)`, `AddressBook#SetPerson(Person,Person)`, `UniquePersonList#setPerson(Person,Person)`: Updates patient details.

**Sequence Diagram**: *Pending Implementation*

#### Design Considerations

**Aspect: Edit Patient Execution:**

- **Alternative 1 (Current Choice)**: Create a copy of the `Person`, edit, then replace.
   - *Pros*: Future-proof, maintains data integrity.
   - *Cons*: Adds complexity, potential performance issues.
- **Alternative 2**: Directly edit the `Person` in the AddressBook.
   - *Pros*: Straightforward.
   - *Cons*: Limits future functionalities, potential data integrity issues.

### Appointments Feature

#### General Implementation Details

<puml src="diagrams/AppointmentClassDiagram.puml"/>

An `Appointment` is comprised of:

- `Name`: Appointment’s title.
- `DateTime`: Scheduling details.
- `Person`: The patient involved.

Uniqueness is enforced through a `UniqueAppointmentList`.

#### Adding an Appointment

##### Overview

`addappointment` adds a new `Appointment` to MedBook.

#### Implementation Steps

1. **Parse User Input**: Utilize `AddAppointmentCommandParser` for attribute validation.
2. **Create and Execute**: Instantiate an `Appointment` and execute `AddAppointmentCommand`.

<puml src="diagrams/AddAppointmentSequenceDiagram.puml" width="450" />

#### Design Considerations

**Aspect: Structure of the Appointment class:**

- **Alternative 1 (Current Choice)**: `Model` holds a `UniqueAppointmentList`, each `Appointment` has a `Person`’s NRIC.
   - *Pros*: Simplifies displaying all appointments.
   - *Cons*: Hard to fetch a specific `Person`’s appointments, issues with NRIC updates.
- **Alternative 2**: Each `Person` holds their `UniqueAppointmentList`.
   - *Pros*: Easy to retrieve a person's appointments.
   - *Cons*: Fetching all appointments can be complex.
## View Feature

### Implementation

The view mechanism is facilitated through `ViewCommand`, which takes a `PATIENTINDEX` as input and updates the `records` and `personBeingViewed` attributes in `AddressBook`. 

Key components implemented include:

- `ViewCommandParser`: Parses command input to create a `ViewCommand` with the specified `PATIENTINDEX`.
- `AddressBook`: Contains methods such as `setRecords(Person)`, `getRecordList()`, and `getPersonBeingViewed()`.
- `RecordCard`: A UI component displaying a single record’s information.
- `RecordListPanel`: A UI component housing a list of `RecordCard`s.

These methods in `AddressBook` are exposed via the `Model` interface (`updateRecords(Person)`, `getRecordList()`, `getPersonBeingViewed()`), and their get variants are also available through the `Logic` interface.

### Usage Scenario

1. **Initialization**: Upon launch, `AddressBook` is populated with sample data. `MainWindow` invokes `getRecordList()` and `getPersonBeingViewed()` to initialize the `recordListPanel` and `personBeingViewedPanel`.

2. **View Command**: User executes `view 1`, initiating a `Model#updateRecords(Person)` call. 

   > **Note**: A failed command will not trigger a `Model#updateRecords(Person)` call, preventing any updates to `records` and `personBeingViewed`.

3. **Update and Display**: `Model` updates `AddressBook`’s variables, displaying the patient's medical records and personal card in their respective UI panels.

The following sequence diagram shows how the view operation works:

<puml src="diagrams/ViewSequenceDiagram.puml" alt="ViewSequenceDiagram" />

### Design Considerations

#### How View Executes

- **Alternative 1**: (Chosen) Utilize `UniqueRecordList` for `records` and `UniquePersonList` for `personBeingViewed`.
  - _Pros_: Simpler implementation.
  - _Cons_: Potential memory usage issues.
- **Alternative 2**: Utilize `UniqueRecordList` for `records` and `Person` for `personBeingViewed`.
  - _Pros_: Reduced memory usage.
  - _Cons_: Increased complexity and required additional object manipulations.

---

## Encryption/Decryption Feature

### Implementation

MedBook secures user data through automatic encryption before saving to disk and decryption upon loading.

#### How it Works

1. **Encryption**: Upon executing a data-altering command:
   - Convert data object to JSON.
   - Encrypt the JSON string using AES (CBC mode, PKCS5 padding).
   - Save encrypted string to disk.
2. **Decryption**: Upon application start:
   - Read and decrypt the stored string.
   - Convert the decrypted JSON back into a data object.

### Design Considerations

#### Choice of Encryption Algorithm

- **Alternative 1**: (Chosen) Use AES in CBC mode with PKCS5 padding.
  - _Pros_: Strong, trusted encryption.
  - _Cons_: Potentially high computational cost for large datasets.
- **Alternative 2**: Use a lighter encryption algorithm.
  - _Pros_: Faster processing times.
  - _Cons_: Potentially less secure.

---

## Attach Files to Patient Records

### Implementation Details

This feature extends the `Record` class to include a `filePath`, allowing file attachments to patient records.

#### Record Class

```java
public class Record {
    // Additional filepath field
    private String filePath;
}
```

#### User Interface

- **Attach File**: Users can attach files through a button click, which opens a file explorer for file selection. The `filePath` in `Record` gets updated accordingly.
- **Opening Attached Files**: Clicking the file path attempts to open the file with the default associated program.

### Design Considerations

- **Current Implementation**: Using GUI for file attachments.
  - _Pros_: User-friendly, especially for non-CLI users.
  - _Cons_: May be slower for users proficient with CLI.

---

### User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​     | I want to …​                                                                                               | So that I can…​                                                   |
| -------- | ----------- | ---------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------- |
| `* * *`  | user        | _add_ a patient’s medical records and contact details                                                      | keep track of them efficiently                                    |
| `* * *`  | user        | _view_ a list of all the patients in the app                                                               | quickly see all my patients at once                               |
| `* * *`  | user        | _delete_ a specific patient’s details from the ap                                                          | remove patients that are no longer relevant or needed             |
| `* * *`  | user        | _edit_ an existing patient’s details in the app                                                            | keep the information accurate and up-to-date                      |
| `* * *`  | user        | _search_ for specific patients using keywords such as patient’s name or medical record                     | easily search for and locate specific patients in the app         |
| `* * *`  | user        | see the app populated with sample data                                                                     | easily see how the app will look when it is in use                |
| `* * *`  | new user    | access a “help” page to view the app’s functionalities                                                     | learn how to use the application effectively                      |
| `* * *`  | new user    | _clear all_ current data                                                                                   | get rid of sample/experimental data I used for exploring the app  |
| `* * *`  | user        | exit the application and save the address book automatically                                               |                                                                   |
| `* *`    | busy user   | _pin_ a specific patient                                                                                   | remember to contact them                                          |
| `* *`    | user        | import my patient details into the app                                                                     | efficiently manage my existing patient information                |
| `* * `   | user        | reorganize the address book in terms of appointment date/time and/or alphabetical order of patients’ names | have a sorted list to for other purposes                          |
| `* *`    | user        | receive regular updates and bug fixes for the app                                                          | ensure that it remains functional and bug-free                    |
| `* *`    | user        | attach files such as lab reports and prescription images to a patient's profile                            | maintain a comprehensive record of all patient information        |
| `* *`    | user        | view a history log of all the changes made to a patient's record                                           | track updates and maintain a reliable record                      |
| `* *`    | expert user | separate my patients into different categories                                                             | easily filter out my patients accordingly                         |
| `* *`    | busy user   | clear the contacts related to a specific patient                                                           | remove them all at one go                                         |
| `*`      | user        | view a daily schedule of patient appointments within the app                                               | prepare for my daily patient consultations                        |
| `*`      | user        | reschedule or cancel appointments within the app                                                           | have flexibility in appointment dates                             |
| `*`      | user        | set access permissions(password protected)                                                                 | allow only authorized personnel to view or modify patient details |
| `*`      | user        | print a patient’s medical record directly from the app                                                     | facilitate physical record keeping and sharing of information     |
| `*`      | expert user | create reminders for my patients to follow up                                                              | make sure that patients remember their follow up appointment      |
| `*`      | expert user | record patients who didn’t show up                                                                         | to keep track of patients who tend to miss appointment dates      |
| `*`      | expert user | export patient data to a CSV file or other common formats                                                  | easily share or transfer data between different systems           |
| `*`      | busy user   | reminders for upcoming patient appointments                                                                | remember to attend all the consultations                          |
| `*`      | busy user   | blacklist certain patients                                                                                 | remove absurd patients                                            |

## Use Cases

### UC01 - Viewing Help
- **Actor**: User
- **System**: MedBook
- **Main Success Scenario (MSS)**:
  - User requests for help.
  - MedBook displays help information.
  - Use case ends.

### UC02 - Adding a Patient
- **Actor**: User
- **System**: MedBook
- **Main Success Scenario (MSS)**:
  1. User selects the option to add a new patient.
  2. MedBook prompts the user to enter the patient's details.
  3. User enters the patient's details.
  4. MedBook validates the input and adds the patient to the list.
  5. MedBook confirms the addition to the user.
- **Extensions**:
  - 3a. User enters invalid patient details.
    - 3a1. MedBook shows an error message and prompts the user to enter the details again.
    - Use case resumes at step 3.

### UC03 - Listing All Patients
- **Actors**: User (typically a healthcare professional)
- **Preconditions**: Patient list is displayed and has at least one patient entry.
- **Main Success Scenario (MSS)**:
  1. User requests to list patients.
  2. MedBook shows a list of patients.
- **Extensions**:
  - 2a. The list is empty.
    - 2a1. MedBook informs the user that the list is empty.
    - Use case ends.

### UC04 - Editing a Patient
- **Actor**: User
- **System**: MedBook
- **Main Success Scenario (MSS)**:
  1. User lists all patients (UC03).
  2. User provides patient ID, detail field, and updated patient details.
  3. MedBook updates the patient entry.
  4. MedBook shows successful edit details.
- **Extensions**:
  - 2a. User gives an invalid input in any field.
    - 2a1. MedBook shows an error message.
    - Use case ends.

### UC05 - Locating a Specific Patient
- **Actor**: User
- **System**: MedBook
- **Main Success Scenario (MSS)**:
  1. User requests to search for a patient.
  2. MedBook prompts the user to enter search criteria.
  3. User enters search criteria.
  4. MedBook performs a search and displays matching patients.
- **Extensions**:
  - 4a. No matches found.
    - 4a1. MedBook informs the user that there were no matches.
    - Use case ends.

### UC06 - Delete a Patient
- **Actor**: User
- **System**: MedBook
- **Main Success Scenario (MSS)**:
  1. User lists all patients (UC03).
  2. User requests to delete a specific patient.
  3. MedBook confirms deletion with the user.
  4. User confirms.
  5. MedBook deletes the patient and informs the user.
- **Extensions**:
  - 2a. Given ID is invalid.
    - 2a1. MedBook shows an error message.
    - Use case ends.
  - 3a. User cancels the deletion.
    - Use case ends.

### UC07 - Pin a Patient
- **Actor**: User
- **System**: MedBook
- **Main Success Scenario (MSS)**:
  1. User lists all patients (UC03).
  2. User requests to pin a specific patient.
  3. MedBook pins the patient and informs the user.
- **Extensions**:
  - 2a. Given ID is invalid.
    - 2a1. MedBook shows an error message.
    - Use case ends.

### UC08 - Searching for Patients
- **Actor**: User
- **System**: MedBook
- **Main Success Scenario (MSS)**:
  1. User initiates a patient search using specific keywords.
  2. MedBook performs a case-insensitive search.
  3. MedBook returns a list of matching patients.
  4. User views the list.
- **Extensions**:
  - 3a. No matches found.
    - 3a1. MedBook displays a message: "No matches found."
    - Use case ends.

---

## Non-Functional Requirements

1. Should work on any mainstream OS with Java 11 or above.
   - Rationale: Ensures accessibility for users on different platforms.
   - Metric: Application functions correctly on Windows, macOS, and Linux.
2. Supports up to 1000 patients without performance issues.
   - Rationale: Ensures scalability for clinics and hospitals.
   - Metric: Application performs smoothly with a database of 1000 patients.
3. Faster operation with commands than mouse for proficient typists.
   - Rationale: Enhances productivity for users familiar with the command line.
   - Metric: Common tasks are completed faster using keyboard shortcuts than GUI.
4. Backward compatible with data from previous versions.
   - Rationale: Ensures smooth transition for existing users upgrading to a new version.
   - Metric: Users can open and interact with data files from previous versions without issues.
5. Usable by novices.
   - Rationale: Ensures the application is accessible to new users.
   - Metric: New users can perform basic tasks without referring to the user manual.
6. Ensures data integrity and security.
   - Rationale: Protects patient data from corruption and unauthorized access.
   - Metric: Application employs data validation, encryption, and access controls.
7. Provides comprehensive error messages and guidance for recovery.
   - Rationale: Helps users understand what went wrong and how to fix it.
   - Metric: Error messages include a description of the issue and steps for resolution.
8. Responsive design that adjusts to different screen sizes and resolutions.
   - Rationale: Ensures usability across various devices.
   - Metric: UI elements are usable and aesthetically pleasing on screens ranging from 13" laptops to 27" monitors.
9. Regular updates and maintenance.
   - Rationale: Ensures the application stays up-to-date with the latest features and security patches.
   - Metric: Application receives updates at least once every three months.
10. Comprehensive documentation and user guides available.
    - Rationale: Provides users with resources to understand and use the application effectively.
    - Metric: Documentation covers all features, includes screenshots, and is easy to navigate.

---

## Glossary

- **Mainstream OS**: Popular operating systems such as Windows, Linux, Unix, and OS-X.

- **Private Contact Detail**: Any contact-related information that is meant to remain confidential and not be disclosed to unauthorized individuals.

- **CLI (Command Line Interface)**: A user interface that allows users to interact with the software using text commands via a console or terminal.

- **GUI (Graphical User Interface)**: A user interface that allows users to interact with the software through graphical icons and visual indicators, as opposed to text-based interfaces.

- **Patient ID**: A unique identifier assigned to each patient for quick and error-free retrieval of their records.

- **Streamlined Workflow**: A smooth, efficient workflow designed to minimize unnecessary steps and optimize productivity.

- **Backward Compatibility**: The ability of the system to work with data and interfaces from earlier versions of the software.

- **Healthcare Professional**: An individual who provides healthcare services, such as doctors, nurses, and medical staff.

- **Typical Usage**: The standard or most common way that the software is utilized by the end-users.

- **Usability**: The ease with which users can learn to use the software and the efficiency they can achieve.

- **Performance Issues**: Any lag, delay, or inefficiency in the software’s response or processing time, especially noticeable when handling a large amount of data.

- **Search Algorithm**: The method used by the software to search for and retrieve patient information based on input keywords or parameters.

- **Error Message**: A notification displayed by the software to inform the user that an error has occurred, often accompanied by information on how to resolve the issue.

- **User**: An individual who interacts with the software, typically a healthcare professional in this context.

- **Patient Information**: Data related to a patient, including but not limited to their personal details, medical history, contact information, and any other relevant information.

- **Case-Insensitive Search**: A type of search that does not differentiate between uppercase and lowercase letters, ensuring that results are returned regardless of the case used in the search query.

- **Data Compatibility**: The ability of the software to properly read, interpret, and use data formatted or created in other versions or different configurations.

- **Novice User**: A user with limited experience and knowledge of the software or similar applications.

Adding to the glossary ensures that all potential users, regardless of their level of expertise, have a resource to refer to when they come across terms they are unfamiliar with. This helps in making the software more accessible and user-friendly.


---

## Documentation, Logging, Testing, Configuration, Dev-Ops

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---
## Appendix: Requirements

### Product Scope

**Target User Profile**:

- **Primary Users**: Doctors and healthcare professionals who handle a substantial number of patients on a regular basis.
- **Platform Preference**: Has a strong inclination towards using desktop applications, particularly those that support command line interfaces (CLI).
- **Technical Proficiency**: Is adept at utilizing CLI applications and prefers them for their speed and efficiency.

**Value Proposition**:

- **Efficient Patient Management**: Provides a streamlined and efficient solution for managing patient information, ensuring that healthcare professionals can access and modify patient data quickly.
- **Speed and Accessibility**: Designed to be significantly faster than conventional GUI applications, allowing users to execute commands and retrieve patient information in a matter of seconds.
- **Command Line Efficiency**: Leverages the power of CLI to offer advanced users the ability to perform tasks in a more direct and efficient manner, while still maintaining accessibility for those who prefer graphical interfaces.


## Appendix: Instructions for Manual Testing

### Launch and Shutdown

#### Initial Launch
1. Download and place the jar file in an empty folder.
2. Double-click the jar file.
   - Expected: GUI opens with sample contacts.

#### Saving Window Preferences
1. Resize and reposition the window, then close it.
2. Reopen the application.
   - Expected: Window retains its size and position.

---

### Deleting a Person

#### When All Persons are Shown
1. Prerequisites: Use `list` to show all persons.
2. Test Case: `delete 1`
   - Expected: First contact deleted, details shown in status message.
3. Test Case: `delete 0`
   - Expected: Error message displayed, status bar unchanged.
4. Other Test Cases: `delete`, `delete x` (where x > list size)
   - Expected: Error message displayed, status bar unchanged.

---

### Saving Data

#### Dealing with Missing/Corrupted Data Files
1. _[Provide instructions and expected behavior]_

---

### Adding a New Patient

#### Standard Procedure
1. Test Case: `add John Doe; Age: 30; Address: 123 Main St`
   - Expected: New patient "John Doe" is added to the list, details are shown in status message.
2. Test Case: `add; Age: 30; Address: 123 Main St`
   - Expected: Error message displayed, patient not added.
3. Test Case: `add John Doe; Age: thirty; Address: 123 Main St`
   - Expected: Error message displayed, patient not added.

---

### Editing a Patient's Details

#### When the Patient Exists
1. Prerequisites: Ensure the patient list is displayed and contains the entry you wish to edit.
2. Test Case: `edit 1; Age: 35`
   - Expected: Patient at index 1 has their age updated to 35. Details shown in status message.
3. Test Case: `edit x; Age: 35` (where x > list size)
   - Expected: Error message displayed, patient's details unchanged.

---

### Searching for Patients

#### When There Are Matching Entries
1. Prerequisites: Ensure the patient list contains entries that will match your search term.
2. Test Case: `search John`
   - Expected: List of patients with "John" in their name or details is displayed.

---

### Verifying Patient Data Integrity

#### After Operations
1. Prerequisites: Perform operations like adding, deleting, and editing patients.
2. Navigate to the folder where data is stored and open the data file.
   - Expected: All changes should be accurately reflected in the data file.

---

### Handling Invalid Commands

#### Input Mistakes
1. Test Case: `ad John Doe; Age: 30; Address: 123 Main St`
   - Expected: Error message displayed, suggesting the correct command format.
2. Test Case: `delet 1`
   - Expected: Error message displayed, suggesting the correct command format.

---

These additional sections aim to cover more aspects of the application, providing a thorough guideline for manual testing. Each section outlines the prerequisites, the test cases, and the expected outcomes to guide testers through the process.
