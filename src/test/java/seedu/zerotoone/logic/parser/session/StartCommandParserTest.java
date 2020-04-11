package seedu.zerotoone.logic.parser.session;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.logic.parser.util.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseFailure;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseSuccess;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.StartCommand;

class StartCommandParserTest {
    private StartCommandParser parser = new StartCommandParser();

    @Test
    public void parse_hasNoArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "asdpa askd", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_success() {
        assertParseSuccess(parser, "1", new StartCommand(INDEX_FIRST_OBJECT));
    }
}
