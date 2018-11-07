package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSUMPTION_PER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.GoogleCalendarStub;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_PATIENT = "Patient";
    public static final String VALID_REMARK_AMY = "Likes coffee";
    public static final String VALID_REMARK_BOB = "Likes tea";
    public static final String VALID_ALLERGY = "milk";
    public static final String VALID_CONDITION = "healthy";
    public static final String VALID_ALLERGY_TO_DELETE = "egg";
    public static final String VALID_CONDITION_TO_DELETE = "sub-health";
    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_NAME_BENSON = "Benson Meier";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PATIENT_NAME_DESC_AMY = " " + PREFIX_PATIENT_NAME + VALID_NAME_AMY;
    public static final String PATIENT_NAME_DESC_BOB = " " + PREFIX_PATIENT_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PATIENT_NAME_DESC =
            " " + PREFIX_PATIENT_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final int VALID_APPOINTMENT_ID_FIRST = 10000;
    public static final int VALID_APPOINTMENT_ID_SECOND = 10001;
    public static final String VALID_MEDICINE_NAME_PARACETAMOL = "Paracetamol";
    public static final String VALID_MEDICINE_NAME_VICODIN = "Vicodin";
    public static final String VALID_DOSAGE_PARACETAMOL = "2";
    public static final String VALID_DOSAGE_VICODIN = "1";
    public static final String VALID_CONSUMPTION_PER_DAY_PARACETAMOL = "3";
    public static final String VALID_CONSUMPTION_PER_DAY_VICODIN = "2";

    public static final String APPOINTMENT_ID_DESC_FIRST = " " + PREFIX_INDEX + VALID_APPOINTMENT_ID_FIRST;
    public static final String APPOINTMENT_ID_DESC_SECOND = " " + PREFIX_INDEX + VALID_APPOINTMENT_ID_SECOND;
    public static final String MEDICINE_NAME_DESC_PARACETAMOL = " " + PREFIX_MEDICINE_NAME
            + VALID_MEDICINE_NAME_PARACETAMOL;
    public static final String MEDICINE_NAME_DESC_VICODIN = " " + PREFIX_MEDICINE_NAME
            + VALID_MEDICINE_NAME_VICODIN;
    public static final String DOSAGE_DESC_PARACETAMOL = " " + PREFIX_DOSAGE + VALID_DOSAGE_PARACETAMOL;
    public static final String DOSAGE_DESC_VICODIN = " " + PREFIX_DOSAGE + VALID_DOSAGE_VICODIN;
    public static final String CONSUMPTION_PER_DAY_DESC_PARACETAMOL = " " + PREFIX_CONSUMPTION_PER_DAY
            + VALID_CONSUMPTION_PER_DAY_PARACETAMOL;
    public static final String CONSUMPTION_PER_DAY_DESC_VICODIN = " " + PREFIX_CONSUMPTION_PER_DAY
            + VALID_CONSUMPTION_PER_DAY_VICODIN;

    public static final String INVALID_APPOINTMENT_ID_DESC = " " + PREFIX_INDEX + "$"; //'$' not allowed
    public static final String INVALID_MEDICINE_NAME_DESC = " " + PREFIX_MEDICINE_NAME + "P@n@dol"; // '@' not allowed
    public static final String INVALID_DOSAGE_DESC = " " + PREFIX_DOSAGE + "2#"; // '#' not allowed
    public static final String INVALID_CONSUMPTION_PER_DAY_DESC = " " + PREFIX_CONSUMPTION_PER_DAY + "-4"; // < 0

    public static final String VALID_DOCTOR_JOHN = "John Doe";
    public static final LocalDateTime VALID_DATE_TIME = LocalDateTime.of(
            2018, 5, 12, 14, 30);

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String NON_EXIST_ALLERGY = "non_exist_allergy";
    public static final String NON_EXIST_CONDITION = "non_exist_condition";
    public static final String NON_EXIST_NAME = "non exist name";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final GoogleCalendarStub GOOGLE_CALENDAR_STUB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        GOOGLE_CALENDAR_STUB = new GoogleCalendarStub();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory, GOOGLE_CALENDAR_STUB);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory, GOOGLE_CALENDAR_STUB);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }
}
