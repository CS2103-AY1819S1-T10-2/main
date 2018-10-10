package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

/**
 * edits remark of index of person in addressbook
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits remark of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_REMARK + "REMARK] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to drink coffee ";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Deleted remark of %1$s";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the person in the filtered person list to edit remark
     * @param remark to be updated
     */

    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);

        this.index = index;
        this.remark = remark;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getTags());

        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        /**
         * Remark is deleted if input field is empty
         */
        return !(remark.value.isEmpty()) ? new CommandResult(String.format(MESSAGE_ADD_REMARK_SUCCESS, editedPerson))
                : new CommandResult(String.format(MESSAGE_DELETE_REMARK_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { //if same object
            return true;
        } else if (!(o instanceof RemarkCommand)) {
            return false;
        } else {
            RemarkCommand r = (RemarkCommand) o;
            return index.equals(r.index) && remark.equals(r.remark);
        }

    }
}
