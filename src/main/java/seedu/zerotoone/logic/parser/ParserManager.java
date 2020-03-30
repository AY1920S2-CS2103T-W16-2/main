package seedu.zerotoone.logic.parser;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.DoneCommand;
import seedu.zerotoone.logic.commands.ExitCommand;
import seedu.zerotoone.logic.commands.HelpCommand;
import seedu.zerotoone.logic.commands.SkipCommand;
import seedu.zerotoone.logic.commands.StartCommand;
import seedu.zerotoone.logic.commands.StopCommand;
import seedu.zerotoone.logic.commands.exercise.ExerciseCommand;
import seedu.zerotoone.logic.commands.schedule.ScheduleCommand;
import seedu.zerotoone.logic.commands.workout.WorkoutCommand;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.exercise.ExerciseCommandParser;
import seedu.zerotoone.logic.parser.schedule.ScheduleCommandParser;
import seedu.zerotoone.logic.parser.session.StartCommandParser;
import seedu.zerotoone.logic.parser.workout.WorkoutCommandParser;

/**
 * Parses user input.
 */
public class ParserManager {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param input full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String input) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(input.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case StartCommand.COMMAND_WORD:
            return new StartCommandParser().parse(arguments);
        case StopCommand.COMMAND_WORD:
            return new StopCommand();
        case DoneCommand.COMMAND_WORD:
            return new DoneCommand();
        case SkipCommand.COMMAND_WORD:
            return new SkipCommand();
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case ExerciseCommand.COMMAND_WORD:
            return new ExerciseCommandParser().parse(arguments);
        case WorkoutCommand.COMMAND_WORD:
            return new WorkoutCommandParser().parse(arguments);
        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
