package seedu.zerotoone.logic.parser.session;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseFailure;
import static seedu.zerotoone.testutil.CommandParserTestUtil.assertParseSuccess;
import static seedu.zerotoone.testutil.CommandTestUtil.PREAMBLE_WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.StopCommand;

class StopCommandParserTest {
    private StopCommandParser parser = new StopCommandParser();

    @Test
    public void parse_hasArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StopCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }

    @Test
    public void parse_success() {
        assertParseSuccess(parser, "", new StopCommand());
    }
}
