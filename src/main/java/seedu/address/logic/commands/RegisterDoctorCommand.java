package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.io.IOException;
import java.security.GeneralSecurityException;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.doctor.Doctor;

/**
 * Adds a doctor to the health book.
 */
public class RegisterDoctorCommand extends Command {

    public static final String COMMAND_WORD = "register-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Register a doctor to the HealthBook. "
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

    public static final String MESSAGE_SUCCESS = "New doctor registered: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "There exists a patient/doctor with this name in the HealthBook";
    public static final String MESSAGE_WRONG_INPUT = "Wrong input provided. Please try again";
    public static final String MESSAGE_SECURTIY_BREACH = "Unable to create doctor due to security breach";

    private final Doctor doctorToRegister;

    /**
     * Creates an AddDoctorCommand to add the specified {@code Doctor}
     */
    public RegisterDoctorCommand(Doctor doctor) {
        requireNonNull(doctor);
        doctorToRegister = doctor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(doctorToRegister)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        try {
            googleCalendar.registerDoctor(doctorToRegister.getName().toString());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_WRONG_INPUT);
        } catch (GeneralSecurityException e) {
            throw new CommandException(MESSAGE_SECURTIY_BREACH);
        }
        model.addDoctor(doctorToRegister);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, doctorToRegister));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterDoctorCommand // instanceof handles nulls
                && doctorToRegister.equals(((RegisterDoctorCommand) other).doctorToRegister));
    }
}
