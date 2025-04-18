package hirehive.address.logic;

import java.nio.file.Path;

import hirehive.address.commons.core.GuiSettings;
import hirehive.address.logic.commands.CommandResult;
import hirehive.address.logic.commands.exceptions.CommandException;
import hirehive.address.logic.parser.exceptions.ParseException;
import hirehive.address.model.Model;
import hirehive.address.model.ReadOnlyAddressBook;
import hirehive.address.model.person.Note;
import hirehive.address.model.person.Person;
import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    Note getPersonNote();
    int getFilteredPersonListSize();
}
