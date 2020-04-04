package seedu.zerotoone.logic.parser.session;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.zerotoone.logic.commands.StopCommand;
import seedu.zerotoone.logic.parser.Parser;
import seedu.zerotoone.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StopCommand object
 */
public class StopCommandParser implements Parser<StopCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StopCommand
     * and returns a StopCommand object for execution.
     * @throws ParseException if the user inputs does not conform the expected format
     */
    public StopCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (!args.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StopCommand.MESSAGE_USAGE));
        }

        return new StopCommand();
    }
}
