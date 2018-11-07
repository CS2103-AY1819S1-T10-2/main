package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ID_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONSUMPTION_PER_DAY_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOSAGE_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.getTypicalAddressBookWithPatientAndDoctorWithAppt;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PrescriptionBuilder;



/**
 * Contains Integration tests and unit tests for AddPrescriptionCommand
 */

public class AddPrescriptionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctorWithAppt(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecified_success() {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(0);
        Prescription toAdd = new PrescriptionBuilder()
                .withAppointmentId(firstAppointment.getAppointmentId())
                .withMedicineName(VALID_MEDICINE_NAME_VICODIN).build();

        Appointment editedAppointment = new AppointmentBuilder(firstAppointment).build();
        editedAppointment.getPrescriptions().add(toAdd);

        List<Person> personList = model.getFilteredPersonList();
        Doctor doctorToEdit = null;
        Patient patientToEdit = null;

        for (Person person : personList) {
            if (person instanceof Doctor) {
                if (firstAppointment.getDoctor().equals(person.getName().toString())) {
                    doctorToEdit = (Doctor) person;
                }
            }
            if (person instanceof Patient) {
                if (firstAppointment.getPatient().equals(person.getName().toString())) {
                    patientToEdit = (Patient) person;
                }
            }
            if (doctorToEdit != null && patientToEdit != null) {
                break;
            }
        }

        Patient editedPatient = new PatientBuilder(patientToEdit).build();
        editedPatient.setAppointment(firstAppointment, editedAppointment);

        Doctor editedDoctor = new DoctorBuilder(doctorToEdit).build();
        editedDoctor.setAppointment(firstAppointment, editedAppointment);

        AddPrescriptionCommand addPrescriptionCommand = new AddPrescriptionCommand(toAdd.getId(), toAdd);

        String expectedMessage = String.format(AddPrescriptionCommand.MESSAGE_SUCCESS, toAdd.getMedicineName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateAppointment(firstAppointment, editedAppointment);
        expectedModel.updatePerson(patientToEdit, editedPatient);
        expectedModel.updatePerson(doctorToEdit, editedDoctor);
        expectedModel.commitAddressBook();
        assertCommandSuccess(addPrescriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePrescriptionUnfilteredList_failure() {
        Appointment appointmentInList = model.getAddressBook().getAppointmentList().get(0);
        Prescription toAdd = new PrescriptionBuilder()
                .withAppointmentId(appointmentInList.getAppointmentId())
                .withMedicineName(appointmentInList.getPrescriptions().get(0).getMedicineName().toString()).build();

        AddPrescriptionCommand addPrescriptionCommand = new AddPrescriptionCommand(toAdd.getId(), toAdd);
        assertCommandFailure(addPrescriptionCommand, model, commandHistory,
                addPrescriptionCommand.MESSAGE_DUPLICATE_PRESCRIPTION);
    }

    @Test
    public void execute_invalidAppointmentId_failure() {
        int outOfBoundsIndex = 1000000;
        Prescription prescriptionWithOutOfBoundIndex = new PrescriptionBuilder()
                .withAppointmentId(outOfBoundsIndex).build();
        AddPrescriptionCommand addPrescriptionCommand = new AddPrescriptionCommand(outOfBoundsIndex,
                prescriptionWithOutOfBoundIndex);

        assertCommandFailure(addPrescriptionCommand, model, commandHistory,
                addPrescriptionCommand.MESSAGE_APPOINTENT_DOES_NOT_EXIST);
    }

    @Test
    public void execute_patientAllergicToMedicine_failure() {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(0);
        Prescription prescriptionToAdd = new PrescriptionBuilder()
                .withAppointmentId(firstAppointment.getAppointmentId()).build();
        AddPrescriptionCommand addPrescriptionCommand = new AddPrescriptionCommand(firstAppointment.getAppointmentId(),
                prescriptionToAdd);

        assertCommandFailure(addPrescriptionCommand, model, commandHistory,
                String.format(addPrescriptionCommand.MESSAGE_PATIENT_ALLERGIC_TO_MEDICINE,
                        prescriptionToAdd.getMedicineName()));
    }


    @Test
    public void equals() {
        Prescription toAdd = new PrescriptionBuilder().build();
        final AddPrescriptionCommand standardCommand = new AddPrescriptionCommand(toAdd.getId(), toAdd);

        // same values -> returns true
        AddPrescriptionCommand commandWithSameValues = new AddPrescriptionCommand(toAdd.getId(), toAdd);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new FilterDoctorCommand()));

        //different values -> returns false
        Prescription other = new PrescriptionBuilder()
                .withAppointmentId(VALID_APPOINTMENT_ID_SECOND)
                .withMedicineName(VALID_MEDICINE_NAME_VICODIN)
                .withDosage(VALID_DOSAGE_VICODIN)
                .withConsumptionPerDay(VALID_CONSUMPTION_PER_DAY_VICODIN).build();
        assertFalse(standardCommand.equals(other));
    }
}
