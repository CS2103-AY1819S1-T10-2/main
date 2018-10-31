package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * View a patient's information.
 */
public class ViewPatientCommand extends Command {

    public static final String COMMAND_WORD = "view-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the patient identified by the name. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "Viewing Patient: %1$s";
    public static final String MESSAGE_INVALID_PATIENT = "This patient does not exist in the HealthBook";

    private final Name name;

    public ViewPatientCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        Patient patient = (Patient) personList.stream()
                .filter(person -> person.getName().equals(name) && person instanceof Patient)
                .findFirst()
                .orElse(null);

        if (patient == null) {
            throw new CommandException(MESSAGE_INVALID_PATIENT);
        }

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(patient));
        return new CommandResult(String.format(MESSAGE_SUCCESS, patient));
    }
}
