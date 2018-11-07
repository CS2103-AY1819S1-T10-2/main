package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueAppointmentList appointments;
    private int appointmentCounter;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        appointments = new UniqueAppointmentList();
        appointmentCounter = 10000;
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }

    /**
     * Replaces the contents of the appointment counter with {@code appointmentCounter}.
     */
    public void setAppointmentCounter(int appointmentCounter) {
        this.appointmentCounter = appointmentCounter;
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setAppointments(newData.getAppointmentList());
        setAppointmentCounter(newData.getAppointmentCounter());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Adds a patient to the address book.
     * The person must not already exist in the address book.
     */
    public void addPatient(Patient p) {
        persons.add(p);
    }

    /**
     * Adds a doctor to the address book.
     * The person must not already exist in the address book.
     */
    public void addDoctor(Doctor d) {
        persons.add(d);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the address book.
     * The appointment identity of {@code editedAppointment} must not be the same as another existing appointment
     * in the address book.
     */
    public void updateAppointment(Appointment target, Appointment editedAppointment) {
        requireNonNull(editedAppointment);

        appointments.setAppointment(target, editedAppointment);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        if (key.getTags().contains(new Tag("Patient"))) {
            for (Appointment patientApp : ((Patient) key).getUpcomingAppointments()) {
                for (Person person : persons) {
                    if (person.getTags().contains(new Tag("Doctor"))) {
                        Doctor doctor = ((Doctor) person);
                        for (Appointment doctorApp : doctor.getUpcomingAppointments()) {
                            if (patientApp.getAppointmentId() == doctorApp.getAppointmentId()) {
                                doctor.deleteAppointment(patientApp);
                                appointments.remove(patientApp);
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            for (Appointment doctorApp : ((Doctor) key).getUpcomingAppointments()) {
                for (Person person : persons) {
                    if (person.getTags().contains(new Tag("Patient"))) {
                        Patient patient = ((Patient) person);
                        for (Appointment patientApp : patient.getUpcomingAppointments()) {
                            if (patientApp.getAppointmentId() == doctorApp.getAppointmentId()) {
                                patient.deleteAppointment(doctorApp);
                                appointments.remove(doctorApp);
                                break;
                            }
                        }
                    }
                }
            }
        }
        persons.remove(key);
    }

    /**
     * Adds appointment to patient {@code patient, appointment} to this {@code HealthBook}.
     * {@code patient} must exist in the health book.
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Deletes a patient's {@code appointment} from this {@code HealthBook}.
     */
    public void deleteAppointment(Appointment appointment, Patient patient, Doctor doctor) {
        Patient targetPatient = persons.findPatient(patient);
        Doctor targetDoctor = persons.findDoctor(doctor);
        patient.deleteAppointment(appointment);
        doctor.deleteAppointment(appointment);
        persons.setPerson(targetPatient, patient);
        persons.setPerson(targetDoctor, doctor);
        appointments.remove(appointment);
    }

    /**
     * Completes a patient's {@code appointment} from this {@code HealthBook}.
     */
    public void completeAppointment(Appointment appointment, Patient patient, Doctor doctor) {
        Patient targetPatient = persons.findPatient(patient);
        Doctor targetDoctor = persons.findDoctor(doctor);
        patient.completeUpcomingAppointment(appointment);
        doctor.completeUpcomingAppointment(appointment);
        persons.setPerson(targetPatient, patient);
        persons.setPerson(targetDoctor, doctor);
        appointments.setToComplete(appointment);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    @Override
    public int getAppointmentCounter() {
        return appointmentCounter;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons))
                && (other instanceof AddressBook
                && appointments.equals(((AddressBook) other).appointments))
                && (other instanceof AddressBook // instanceof handles nulls
                && appointmentCounter == (((AddressBook) other).appointmentCounter));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
