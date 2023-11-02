---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# MedBook Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a patient).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/AY2324S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Records feature

#### General Implementation Details

<puml src="diagrams/RecordClassDiagram.puml"/>

A `Record` object is composed of classes that represent the various attributes available in each `Record`. To enforce uniqueness between records, all records are stored in a `UniqueRecordList`.

The related attributes of a `Record` are:

- `DateTime`: The date and time of the patient's visit to the clinic
- `List<Condition>`: The list of conditions the patient has
- `List<Medication>`: The list of medications that the doctor prescribes to the patient

#### Add a Record

##### Overview

The `addrecord` command adds a new `Record` object to the patient in MedBook.

#### Implementation

Step 1. `AddRecordCommandParser` first parses the user input and verifies all necessary parameters are present in the user input and are valid.
Step 2. A `Record` object is created and passed as a parameter of `AddRecordCommand` object's constructor.
Step 3. After `AddRecordCommand` is created, `AddRecordCommand#execute()` is executed, which adds a new `Record` to the `UniqueRecordList` that belongs to each patient.

<puml src="diagrams/AddRecordSequenceDiagram.puml" width="450" />

#### Design Consideration

**Aspect: Structure of Appointment class:**

- **Alternative 1 (current choice):** Each `Person` contains a `UniqueRecordList` with all the medical records of the patient from the past until now.
  - Pros: Easy to retrieve all the records of a single `Person`.
  - Cons: Difficult to retrieve all the records of every `Person` in the `UniquePersonList` to view.
- **Alternative 2:** `Model` contains a `UniqueRecordList` which contains all records of every patient. Each `Record` then contains the `Person` it is scheduled with.
  - Pros: Easy to retrieve all records to view.
  - Cons: Difficult to retrieve all the records associated with a single `Person`.

### editpatient Feature

The proposed editpatient mechanism is facilitated by `EditCommand`. It receives an `PATIENTINDEX` and the `FIELD` and `NEWVALUE`, and then it edits the
specified field of the specific Patient at that index in the `AddressBook`. Additionally, the following classes, methods and UI component
are implemented:

- `EditCommandParser` - Read the command information and create a EditCommand with the specified `PATIENTINDEX`, `FIELD` and `NEWVALUE`.
- `EditPersonDescriptor` - This class in EditCommand Stores the details to edit the patient with. The specified non-empty field value will replace the
  corresponding field value of the patient.
- `ModelManager#setPerson(Person,Person)`, `AddressBook#SetPerson(Person,Person)`, `UniquePersonList#setPerson(Person,Person)`
  - Sets the target patient in the AddressBook with the edited patient.

This newly implemented `EditCommandParser` class will then be used in the `AddressBookParser#parseCommand(String)`
and return the `EditCommand` with the `PATIENTINDEX` and the `EditPersonDescriptor` as parameters.
The `EditCommand#execute(Model)` method will then update the current state of the `Model` according to the edit.

Given below is an example usage scenario and how the editpatient mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `AddressBook` will be initialized
with the sample data.

Step 2. The user execute `editpatient 1/name/Josh` command to edit name of the 1st patient in the Medbook to **Josh**.
The `editpatient` command calls `Model#setPerson(Person, Person)` to edit the Person in the model, which then calls `AddressBook#SetPerson(Person,Person)`,
which in turn calls `UniquePersonList#setPerson(Person)` to set the target patient in the uniquePersonList with the edited patient.

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#setPerson(Person,Person)`, so the Medbook will not
edit the `Person` in the UniquePersonList.

</box>

Step 3. The `Model` then calls `AddressBook#setPerson(Person,Person)` to update the Person in the `AddressBook`.

Step 4. The `AddressBook` then calls `UniquePersonList#(Person,Person)` to set the target patient
in the uniquePersonList with the edited patient in the UniquePersonList.

Step 5. The newly updated AddressBook will then be shown.

The following sequence diagram shows how the editpatient operation works:

**Will be done soon**

#### Design considerations:

**Aspect: How editpatient executes:**

- **Alternative 1 (current choice):** Create a defensive copy of the Person and then editing it, then replace the original patient with the edited patient.

  - Pros: Allows for more complex operations and functionalities in the future, like tracking edit histories. This immutability maintains integrity of the data.
  - Cons: Introduces added complexity and potential performance overhead.

- **Alternative 2:** Edit the patient directly in the AddressBook.
  - Pros: Direct and straightforward implementation.
  - Cons: May not be flexible if there are future requirements to retain edit history or additional patient properties.

### Appointments feature

#### General Implementation Details

<puml src="diagrams/AppointmentClassDiagram.puml"/>

An `Appointment` object is composed of classes that represent the various attributes available in each `Appointment`. To enforce uniqueness between appointments, all appointments are stored in a `UniqueAppointmentList`.

The related attributes of an `Appointment` are:

- `Name`: Name of the Appointment
- `DateTime`: Date and Time of the Appointment
- `Person`: The Person the Appointment is scheduled with

#### Add an Appointment

##### Overview

The `addappointment` command adds a new `Appointment` object to Medbook.

#### Implementation

The first stage of the implementation is to parse the user input. `AddAppointmentCommandParser` is used to parse and validate the user input for each attributes of `Appointment`. An `Appointment` object is then created with the validated attributes and used to create an `AddAppointmentCommand` object.

Next, the `AddCommand#execute()` method is executed to add the new `Appointment` object to the `UniqueAppointmentList`.

The following sequence diagram shows how an `Appointment` is added:

<puml src="diagrams/AddAppointmentSequenceDiagram.puml" width="450" />

#### Design considerations:

**Aspect: Structure of Appointment class:**

- **Alternative 1 (current choice):** `Model` contains a `UniqueAppointmentList` which contains all appointments. Each `Appointment` then contains the `NRIC` of the patient it is scheduled with.

  - Pros: Easy to retrieve all appointments to view.
  - Cons:
    - Difficult to retrieve all the appointments scheduled with a single `Person`.
    - If the `NRIC` is updated through `editpatient`, it is not automatically updated in the `Appointment`.

- **Alternative 2:** Each `Person` contains a `UniqueAppointmentList` with all the appointments he is scheduled with.
  - Pros: Easy to retrieve all the appointments scheduled with a single `Person`.
  - Cons: Difficult to retrieve all appointments to view.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th patient in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new patient. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the patient was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how the undo operation works:

<puml src="diagrams/UndoSequenceDiagram.puml" alt="UndoSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

- **Alternative 1 (current choice):** Saves the entire address book.

  - Pros: Easy to implement.
  - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  - Pros: Will use less memory (e.g. for `delete`, just save the patient being deleted).
  - Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### View feature

#### Implementation

The proposed view mechanism is facilitated by `ViewCommand`. It receives an `PATIENTINDEX`, and then it updates the
`records` and `personBeingViewed` in the `AddressBook`. Additionally, the following classes, methods and UI component
are implemented:

- `ViewCommandParser` - Read the command information and create a ViewCommand with the specified `PATIENTINDEX`.
- `AddressBook#setRecords(Person)` - Assign value to the `records` and `personBeingViewed`.
- `AddressBook#getRecordList()` - return the `records`.
- `AddressBook#getPersonBeingViewed()` - return the `personBeingViewed`.
- `RecordCard` - a UI component that holds the information of single record.
- `RecordListPanel` - a UI component that holds a place at the Main Window and stores a list of `RecordCard`.

The newly implemented methods in `AddressBook` are exposed in the `Model` interface as `Model#updateRecords(Person)`,
`Model#getRecordList()` and `Model#getPersonBeingViewed()`. The get methods are also exposed in `Logic` interface as
`Logic#getRecordList()`, and `Logic#getPersonBeingViewed()`.

Given below is an example usage scenario and how the view mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `AddressBook` will be initialized
with the sample data. The `MainWindow` calls `Logic#getRecordList()`, and `Logic#getPersonBeingViewed()` so that
`recordListPanel` and `personBeingViewedPanel` can safely occupy their destined places.

Step 2. The user execute `view 1` command to view the medical records of the 1st patient in the Medbook.
The `view` command calls `Model#updateRecords(Person)`.

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#updateRecrods(Person)`, so the Medbook will not
update the `record` and `personBeingViewed` variable.

</box>

Step 3. The `Model` then calls `AddressBook#setRecords(Person)` to update the variable in the `AddressBook`. The medical
records of the patient is displayed at the left column in the `recordListPanel`. The `personBeingViewedPanel` contains
the patient card of the patient.

The following sequence diagram shows how the undo operation works:

<puml src="diagrams/ViewSequenceDiagram.puml" alt="ViewSequenceDiagram" />

#### Design considerations:

**Aspect: How view executes:**

- **Alternative 1 (current choice):** Saves the `records` as `UniqueRecordList` and `personBeingViewed`
  as `UniquePersonList`.
  _ Pros: Easy to implement.
  _ Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Saves the `records` as `UniqueRecordList` and `personBeingViewed`as `Person`.
  _ Pros: Will use less memory (e.g. for `delete`, just save the patient being deleted).
  _ Cons: A lot of extra work need to be done (e.g. need to have an empty patient object and need to make it as a node
  before passing into the `personBeingViewedPanel`).

Certainly! Here is a portion that you can add to your developer guide to explain the encryption/decryption feature:

---

### [Implemented] Encryption/Decryption Feature

#### Implementation

MedBook ensures the privacy and safety of user data by encrypting the data before saving it to the hard disk. The data is then decrypted when loaded back into the application upon restart. The implementation of this feature is transparent to the user, providing a seamless experience.

#### How it works

##### Encryption

When a command that changes the data is executed, MedBook automatically triggers the encryption process before saving the data to the disk. The process is as follows:

1. The data, which is in the form of an object, is converted to a JSON string.
2. The JSON string is then encrypted using Advanced Encryption Standard (AES) in Cipher Block Chaining (CBC) mode with PKCS5 padding.
3. The encrypted string is saved to the data file.

The encryption key is securely managed to ensure that only MedBook can decrypt the data.

##### Decryption

When MedBook is started, it attempts to load the data from the disk. The process is as follows:

1. MedBook reads the encrypted string from the data file.
2. The encrypted string is decrypted using the same AES algorithm and key.
3. The decrypted JSON string is converted back into an object, and the data is loaded into the application.

#### Design Considerations

##### Aspect: Choice of Encryption Algorithm

- **Alternative 1 (chosen)**: Use AES in CBC mode with PKCS5 padding.

  - _Pros_: Strong encryption that is widely recognized and trusted.
  - _Cons_: May be computationally expensive for very large datasets.

- **Alternative 2**: Use a lighter encryption algorithm.
  - _Pros_: Faster encryption and decryption times, which might be noticeable for very large datasets.
  - _Cons_: Potentially less secure, depending on the algorithm chosen.

### Attaching Files to Patient Records

This section provides an overview of the 'Attach Files' feature, which is already implemented in our application. This feature enables users to attach files to specific patient records, enhancing the user experience and functionality of the application.

#### Implementation Details

The 'Attach Files' feature has been implemented by extending the `Record` class to include a `filePath` attribute, which stores the path to the attached file on the local computer. Each patient can have multiple records, but each record is limited to one attachment.

##### Record Class

```java
public class Record {
    // Other attributes
    private String filePath;

    // Constructors, getters, and setters
}
```

The `Record` class includes:

- `filePath`: A `String` that stores the path to the attached file.

##### User Interface

A 'Attach File' button is present in the record card on the specific patient's view page. The workflow for attaching a file is as follows:

1. User clicks the 'Attach File' button.
2. A file explorer window opens, allowing the user to navigate and select the desired file.
3. Upon file selection, the application retrieves the file's path and updates the `filePath` attribute of the corresponding `Record` object.
4. The updated record, including the file path, is then saved to the address book.

##### Opening Attached Files

Clicking on the displayed file path in the patient's record card triggers the application to:

1. Retrieve the file path from the `filePath` attribute of the `Record` object.
2. Attempt to open the file using the default program associated with the file type on the user's computer.
3. If the file is inaccessible (moved or deleted), an error message is displayed to the user.

#### Design Considerations

##### Attach Files Implementation

**Current Implementation**: Using CUI instead of CLI.

- Pros:
  - Easy to navigate and learn.
  - More efficient as compared to typing in absolute file paths
- Cons:
  - Not particularly suited for fast typers

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- doctors
- has a need to manage a significant number of patients
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**:

- streamline patient management
- easy access to patients' details such as medical records and contact information
- manage patients faster than a typical mouse/GUI driven app

### User stories

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

_{More to be added}_

### Use cases

(For all use cases below, the **System** is the `MedBook` and the **Actor** is the `user`, unless
specified otherwise)

**Use case: UC01 - Viewing help**

**MSS**

**Use case: UC02 - Adding a patient**

**MSS**

**Use case: UC03 - Listing all patient**
**Actors**: User (typically a healthcare professional)
_Preconditions_:

1. Patient list is displayed and has at least one patient entry.

**MSS**

1.  User requests to list patients.
2.  Medbook shows a list of patients.

    Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

**Use case: UC04 - Editing a patient**

1. User <u>lists all patients (UC03)</u>
2. User provides the required input: `patient ID`, `detail field`, and `updated patient details`.
3. MedBook updates the patient entry with new detail.
4. MedBook shows successful edit details.

   Use case ends.

**Extensions**

- 2a. User gives an invalid input in any of the given field.

  - 2a1. MedBook shows an error message.

    Use case ends.

**MSS**

**Use case: UC05 - Locating a specific patient**

**MSS**

**Use case: UC06 - Delete a patient**

**MSS**

1. User <u>lists all patients (UC03)</u>
2. User requests to delete a specific patient in the list
3. MedBook deletes the patient

   Use case ends.

**Extensions**

- 2a. The given id is invalid.

  - 2a1. MedBook shows an error message.~~

    Use case ends.

**Use case: UC07 - Pin a patient**

**MSS**

1.  User <u>lists all patients (UC03)</u>
2.  User requests to pin a specific patient in the list
3.  MedBook pins the patient

    Use case ends.

**Extensions**

- 2a. The given id is invalid.

  - 2a1. MedBook shows an error message.

    Use case ends.

## Use Case: UC08 - Searching for Patients

**Main Success Scenario (MSS)**

1. User initiates a search for patients based on specific keywords using the `search` command.
2. MedBook performs a case-insensitive search of patient names and details.
3. MedBook returns a list of patients matching at least one keyword.
4. The user views the list of matching patients.

**Extensions**

- 3a. No matches found.
  - 3a1. MedBook displays a message: "No matches found. Try using a different keyword."
  - Use case ends.

_{More to be added}_

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 patients without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The system should be backward compatible with data produced by earlier versions of the system.
5.  The system should be usable by a novice who has never used an address book.

_{More to be added}_

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, OS-X
- **Private contact detail**: A contact detail that is not meant to be shared with others

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a patient

1. Deleting a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No patient is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
