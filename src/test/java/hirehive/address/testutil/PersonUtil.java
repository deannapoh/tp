package hirehive.address.testutil;

import java.util.Set;

import hirehive.address.logic.commands.AddCommand;
import hirehive.address.logic.commands.EditCommand;
import hirehive.address.logic.parser.CliSyntax;
import hirehive.address.model.person.Person;
import hirehive.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + person.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(CliSyntax.PREFIX_ROLE + person.getRole().fullRole + " ");
        sb.append(CliSyntax.PREFIX_TAG + person.getTag().getTagName() + " ");
        sb.append(CliSyntax.PREFIX_NOTE + person.getNote().value + " ");
        sb.append(CliSyntax.PREFIX_DATE + person.getDate().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditCommand.EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(CliSyntax.PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(CliSyntax.PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(CliSyntax.PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getRole().ifPresent(role -> sb.append(CliSyntax.PREFIX_ROLE).append(role.fullRole).append(" "));
        descriptor.getNote().ifPresent(note -> sb.append(CliSyntax.PREFIX_NOTE).append(note.value).append(" "));
        descriptor.getTag().ifPresent(tag -> sb.append(CliSyntax.PREFIX_TAG).append(tag.getTagName()).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(CliSyntax.PREFIX_DATE).append(date.toString()).append(" "));
        return sb.toString();
    }
}
