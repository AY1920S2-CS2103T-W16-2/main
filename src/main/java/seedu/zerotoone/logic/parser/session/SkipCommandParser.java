package seedu.zerotoone.logic.parser.session;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.zerotoone.logic.commands.SkipCommand;
import seedu.zerotoone.logic.parser.Parser;
import seedu.zerotoone.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DoneCommand object
 */
public class SkipCommandParser implements Parser<SkipCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SkipCommand
     * and returns a SkipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SkipCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (!args.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SkipCommand.MESSAGE_USAGE));
        }

        return new SkipCommand();
    }
}
