package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Adds a patient to the health book.
 */
public class RegisterPatientCommand extends Command {

    public static final String COMMAND_WORD = "register-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Register a patient to the HealthBook. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "New patient registered: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "There exists a patient/doctor with this name in the HealthBook";

    private final Patient patientToRegister;

    /**
     * Creates an RegisterPatientCommand to add the specified {@code Patient}
     */
    public RegisterPatientCommand(Patient patient) {
        requireNonNull(patient);
        patientToRegister = patient;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(patientToRegister)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPatient(patientToRegister);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, patientToRegister));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterPatientCommand // instanceof handles nulls
                && patientToRegister.equals(((RegisterPatientCommand) other).patientToRegister));
    }
}
