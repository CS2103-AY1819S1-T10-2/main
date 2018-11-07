package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

/**
 * edits remark of index of person in addressbook
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits remark of the patient. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_PATIENT_NAME + "PATIENT_NAME "
            + PREFIX_REMARK + "REMARK "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT_NAME + "John Doe "
            + PREFIX_REMARK + "Has chronic heart disease ";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Deleted remark of %1$s";
    public static final String MESSAGE_INVALID_PATIENT_FAILURE = "This patient does not exist in HealthBook";

    private final Name patientName;
    private final Remark remark;

    /**
     * @param patientName of the patient in the filtered person list to edit remark
     * @param remark to be updated
     */

    public RemarkCommand(Name patientName, Remark remark) {
        requireAllNonNull(patientName, remark);

        this.patientName = patientName;
        this.remark = remark;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Patient patientToEdit = null;

        for (Person person : lastShownList) {
            if (person.getName().equals(patientName) && person instanceof Patient) {
                patientToEdit = (Patient) person;
            }
        }

        if (patientToEdit == null) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_FAILURE);
        }

        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), remark, patientToEdit.getTags(),
                patientToEdit.getTelegramId(), patientToEdit.getUpcomingAppointments(),
                patientToEdit.getPastAppointments(), patientToEdit.getMedicalHistory());

        model.updatePerson(patientToEdit, editedPatient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        /**
         * Remark is deleted if input field is empty
         */
        return !(remark.value.isEmpty()) ? new CommandResult(String.format(MESSAGE_ADD_REMARK_SUCCESS, editedPatient))
                : new CommandResult(String.format(MESSAGE_DELETE_REMARK_SUCCESS, editedPatient));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { //if same object
            return true;
        } else if (!(o instanceof RemarkCommand)) {
            return false;
        } else {
            RemarkCommand r = (RemarkCommand) o;
            return patientName.equals(r.patientName) && remark.equals(r.remark);
        }

    }
}
