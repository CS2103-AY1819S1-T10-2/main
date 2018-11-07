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
import seedu.address.model.doctor.Doctor;
import seedu.address.testutil.DoctorBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code RegisterDoctorCommand}.
 */
public class RegisterDoctorCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newDoctor_success() {
        Doctor validDoctor = new DoctorBuilder()
                .withName("Helena")
                .withPhone("92142122")
                .withEmail("helena@example.com")
                .withAddress("20th street")
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addDoctor(validDoctor);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new RegisterDoctorCommand(validDoctor), model, commandHistory,
                String.format(RegisterDoctorCommand.MESSAGE_SUCCESS, validDoctor), expectedModel);
    }

    @Test
    public void execute_duplicateDoctor_throwsCommandException() {
        Doctor validDoctor = new DoctorBuilder().build();
        assertCommandFailure(new RegisterDoctorCommand(validDoctor), model, commandHistory,
                RegisterDoctorCommand.MESSAGE_DUPLICATE_PERSON);
    }
}
