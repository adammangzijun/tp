package seedu.address.testutil;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.shared.DateTime;
import seedu.address.model.shared.Name;
import seedu.address.model.shared.Nric;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final String DEFAULT_NAME = "Nose Exam";
    public static final String DEFAULT_DATETIME = "27-10-2010 1200";
    public static final String DEFAULT_NRIC = "B1234567A";

    private Name name;
    private DateTime dateTime;
    private Nric nric;

    /**
     * Creates a {@code AppoointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        name = new Name(DEFAULT_NAME);
        dateTime = new DateTime(DEFAULT_DATETIME);
        nric = new Nric(DEFAULT_NRIC);
    }

    /**
     * InitializesAppointment with the data of {@code personToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        name = appointmentToCopy.getName();
        dateTime = appointmentToCopy.getDateTime();
        nric = appointmentToCopy.getNric();
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public AppointmentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withNric(String nric) {
        if (nric == null) {
            this.nric = null;
            return this;
        }
        this.nric = new Nric(nric);
        return this;
    }

    public Appointment build() {
        return new Appointment(name, dateTime, nric);
    }

}
