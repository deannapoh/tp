package hirehive.address.logic.commands;

import static java.util.Objects.requireNonNull;

import hirehive.address.commons.util.ToStringBuilder;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.model.Model;
import hirehive.address.model.person.PersonContainsTagPredicate;

/**
 * Finds and lists all persons in address book whose tags contain any of the argument tags.
 * Tag matching is case insensitive.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String NOT_IMPLEMENTED_TEXT = "Command not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "Tag: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons with the given tag "
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: t/ TAG\n"
            + "Example: " + COMMAND_WORD + " applicant";

    private final PersonContainsTagPredicate predicate;

    public FilterCommand(PersonContainsTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        String message;
        if (!model.getFilteredPersonList().isEmpty()) {
            message = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size());
        } else {
            message = Messages.MESSAGE_NO_SUCH_PERSON;
        }
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
