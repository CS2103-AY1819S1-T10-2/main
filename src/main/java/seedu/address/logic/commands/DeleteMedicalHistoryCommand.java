package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.MedicalHistory;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * delete medical history for a patient specified by index in healthbook
 */
public class DeleteMedicalHistoryCommand extends Command {
    public static final String COMMAND_WORD = "delete-medical-history";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": delete medical history for a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ALLERGY + "ALLERGIES (separated by comma) "
            + PREFIX_CONDITION + "CONDITIONS (separated by comma) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ALLERGY + "penicillin,milk "
            + PREFIX_CONDITION + "sub-healthy,hyperglycemia ";

    public static final String MESSAGE_DELETE_MEDICAL_HISTORY_SUCCESS = "Medical history deleted for: %1$s";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_WRONG_TYPE = "This command is only for patients";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_ALLERGY = "Non Exist Allergy: ";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_CONDITION = "Non Exist Condition: ";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_INFO = "Please provide valid info";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_MATCH_NAME =
            " does not exist in the healthbook";


    private final Name name;
    private String allergy;
    private String condition;

    /**
     * Creates an DeleteMedicalHistoryCommand for the specified {@code Person}
     */
    public DeleteMedicalHistoryCommand(Name name, String allergy, String condition) {
        requireNonNull(name);
        this.name = name;
        this.condition = condition;
        this.allergy = allergy;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit = null;
        boolean personExist = false;
        for (Person person : lastShownList) {
            if (person.getName().equals(name)) {
                personToEdit = person;
                personExist = true;
                break;
            }
        }
        if (!personExist) {
            throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_MATCH_NAME);
        }

        if (!(personToEdit.getTags().contains(new Tag("Patient")))) {
            throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_WRONG_TYPE);
        }
        Patient patientToEdit = (Patient) personToEdit;
        MedicalHistory editedMedicalHistory = new MedicalHistory(patientToEdit.getMedicalHistory());
        if (allergy.equals("") && condition.equals("")) {
            throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_INFO);
        }
        if (!(allergy.equals(""))) {
            ArrayList<String> allergiesToDelete = new ArrayList<>(Arrays.asList(allergy.split(",")));
            for (int index = 0; index < allergiesToDelete.size(); index++) {
                if (editedMedicalHistory.getAllergies().contains(allergiesToDelete.get(index))) {
                    editedMedicalHistory.getAllergies().remove(allergiesToDelete.get(index));
                } else {
                    throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_ALLERGY
                            + allergiesToDelete.get(index));
                }
            }
        }
        if (!(condition.equals(""))) {
            ArrayList<String> conditionsToDelete = new ArrayList<>(Arrays.asList(condition.split(",")));
            for (int index = 0; index < conditionsToDelete.size(); index++) {
                if (editedMedicalHistory.getConditions().contains(conditionsToDelete.get(index))) {
                    editedMedicalHistory.getConditions().remove(conditionsToDelete.get(index));
                } else {
                    throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_CONDITION
                            + conditionsToDelete.get(index));
                }
            }
        }
        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), patientToEdit.getRemark(), patientToEdit.getTags(),
                patientToEdit.getTelegramId(), patientToEdit.getUpcomingAppointments(),
                patientToEdit.getPastAppointments(), editedMedicalHistory);
        model.updatePerson(patientToEdit, editedPatient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(editedPatient));
        return new CommandResult(String.format(MESSAGE_DELETE_MEDICAL_HISTORY_SUCCESS, patientToEdit));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { //if same object
            return true;
        } else if (!(o instanceof DeleteMedicalHistoryCommand)) {
            return false;
        } else {
            DeleteMedicalHistoryCommand r = (DeleteMedicalHistoryCommand) o;
            return name.equals(r.name) && allergy.equals(r.allergy) && condition.equals(r.condition);
        }

    }
}
