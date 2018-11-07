package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PATIENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} and {@code Appointment} objects to be used in tests.
 */
public class TypicalPatientsAndDoctorsWithAppt {

    // Appointment objects
    public static final Appointment FIRST = new AppointmentBuilder().withAppointmentId(10000)
            .withDoctor("Fiona Kunz").withPatient("Alice Pauline").withDateTime("2018-12-11 12:00")
            .withComments("Heart check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10000).withMedicineName("Aspirin").build()))).build();
    public static final Appointment SECOND = new AppointmentBuilder().withAppointmentId(10001)
            .withDoctor("George Best").withPatient("Benson Meier").withDateTime("2018-12-12 12:00")
            .withComments("Flu check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10001).withMedicineName("Tamiflu").build()))).build();
    public static final Appointment THIRD = new AppointmentBuilder().withAppointmentId(10002)
            .withDoctor("Fiona Kunz").withPatient("Carl Kurz").withDateTime("2018-12-13 12:00")
            .withComments("Flu check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10002).withMedicineName("Tamiflu").build()))).build();
    public static final Appointment FOURTH = new AppointmentBuilder().withAppointmentId(10003)
            .withDoctor("George Best").withPatient("Daniel Meier").withDateTime("2018-12-14 12:00")
            .withComments("Cough check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10003).withMedicineName("Dextromethorphan").build()))).build();
    public static final Appointment FIFTH = new AppointmentBuilder().withAppointmentId(10004)
            .withDoctor("Fiona Kunz").withPatient("Elle Meyer").withDateTime("2018-12-15 12:00")
            .withComments("Heart check up")
            .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                    .withAppointmentId(10004).withMedicineName("Aspirin").build()))).build();

    // Persons objects
    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withRemark("")
            .withTags("Patient")
            .withMedicalHistory("Paracetamol", "sub-health")
            .withAppointment(FIRST).build();

    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withRemark("")
            .withTags("Patient", "friends")
            .withMedicalHistory("", "having cold")
            .withAppointment(SECOND).build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withRemark("")
            .withTags("Patient")
            .withMedicalHistory("fish", "")
            .withAppointment(THIRD).build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withRemark("")
            .withTags("Patient", "friends")
            .withMedicalHistory("", "")
            .withAppointment(FOURTH).build();
    public static final Patient ELLE = new PatientBuilder()
            .withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withRemark("")
            .withTags("Patient")
            .withMedicalHistory("", "")
            .withAppointment(FIFTH).build();
    public static final Doctor FIONA = new DoctorBuilder()
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withRemark("")
            .withTags("Doctor")
            .withAppointment(FIRST, THIRD, FIFTH).build();
    public static final Doctor GEORGE = new DoctorBuilder()
            .withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withRemark("")
            .withTags("Doctor")
            .withAppointment(SECOND, FOURTH).build();

    // Manually added
    public static final Patient HOON = new PatientBuilder()
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withRemark("")
            .withTags("Patient")
            .withMedicalHistory("", "").build();
    public static final Patient IDA = new PatientBuilder()
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withRemark("")
            .withTags("Patient")
            .withMedicalHistory("", "").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withRemark("")
            .withTags(VALID_TAG_PATIENT)
            .withMedicalHistory(VALID_ALLERGY, VALID_CONDITION).build();
    public static final Patient BOB = new PatientBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withRemark("")
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND, VALID_TAG_PATIENT)
            .withMedicalHistory(VALID_ALLERGY, VALID_CONDITION).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatientsAndDoctorsWithAppt() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBookWithPatientAndDoctorWithAppt() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPatientsAndDoctorsWithAppt()) {
            ab.addPerson(person);
        }

        for (Appointment appointment : getTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    public static List<Person> getTypicalPatientsAndDoctorsWithAppt() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(FIRST, SECOND, THIRD, FOURTH, FIFTH));
    }
}
