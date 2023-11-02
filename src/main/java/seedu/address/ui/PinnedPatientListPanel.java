package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;

/**
 * Panel containing the list of pinned patients.
 */
public class PinnedPatientListPanel extends UiPart<Region> {
    private static final String FXML = "PinnedPatientListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PinnedPatientListPanel.class);

    @FXML
    private ListView<Patient> pinnedPatientListView;

    /**
     * Creates a {@code PinnedPersonListPanel} with the given {@code ObservableList}.
     */
    public PinnedPatientListPanel(ObservableList<Patient> pinnedPatientList) {
        super(FXML);
        pinnedPatientListView.setItems(pinnedPatientList);
        pinnedPatientListView.setCellFactory(listView -> new PinnedPatientListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patient} using a {@code PatientCard}.
     */
    class PinnedPatientListViewCell extends ListCell<Patient> {
        @Override
        protected void updateItem(Patient patient, boolean empty) {
            super.updateItem(patient, empty);

            if (empty || patient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PatientCard(patient, getIndex() + 1).getRoot());
            }
        }
    }

}
