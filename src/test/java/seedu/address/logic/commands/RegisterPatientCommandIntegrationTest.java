package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code RegisterPatientCommand}.
 */
public class RegisterPatientCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPatient_success() {
        Patient validPatient = new PatientBuilder()
                .withName("Helena")
                .withPhone("92142122")
                .withEmail("helena@example.com")
                .withAddress("20th street")
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPatient(validPatient);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new RegisterPatientCommand(validPatient), model, commandHistory,
                String.format(RegisterPatientCommand.MESSAGE_SUCCESS, validPatient), expectedModel);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient validPatient = new PatientBuilder().build();
        assertCommandFailure(new RegisterPatientCommand(validPatient), model, commandHistory,
                RegisterPatientCommand.MESSAGE_DUPLICATE_PERSON);
    }
}
