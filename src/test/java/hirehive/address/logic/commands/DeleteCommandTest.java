package hirehive.address.logic.commands;

import static hirehive.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static hirehive.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hirehive.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static hirehive.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import hirehive.address.commons.core.index.Index;
import hirehive.address.logic.Messages;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.commands.queries.NameQuery;
import hirehive.address.model.Model;
import hirehive.address.model.ModelManager;
import hirehive.address.model.UserPrefs;
import hirehive.address.model.person.NameContainsKeywordsPredicate;
import hirehive.address.model.person.Person;
import hirehive.address.testutil.TypicalIndexes;
import hirehive.address.testutil.TypicalPersons;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String nameToDelete = personToDelete.getName().fullName;

        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(nameToDelete));
        DeleteCommand deleteCommand = new DeleteCommand(nameQuery);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, new CommandResult(expectedMessage, true), expectedModel);
    }

    @Test
    public void execute_nonexistentName_throwsCommandException() {
        String nonexistentKeywords = "Nonexistent";
        NameQuery nameQuery = new NameQuery(new NameContainsKeywordsPredicate(nonexistentKeywords));
        DeleteCommand deleteCommand = new DeleteCommand(nameQuery);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        model = new ModelManager();
        assertThrows(CommandException.class, () -> new DeleteCommand(TypicalIndexes.INDEX_FIRST_PERSON).execute(model));
    }

    @Test
    public void equals() {
        NameQuery firstQuery = new NameQuery(new NameContainsKeywordsPredicate("Alice"));
        NameQuery secondQuery = new NameQuery(new NameContainsKeywordsPredicate("Bob"));

        DeleteCommand deleteFirstCommand = new DeleteCommand(firstQuery);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondQuery);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstQuery);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
