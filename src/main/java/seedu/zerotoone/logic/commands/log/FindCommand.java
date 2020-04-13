package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.logic.commands.util.Commands;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.log.PredicateFilterLogWorkoutName;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Finds and lists all exercises in exercise list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends LogCommand {

    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_USAGE = "Usage: " + Commands.LOG_FIND;
    public static final String MESSAGE_LOGS_LISTED_OVERVIEW = "Listed %1$d logged workout session(s) found!";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);
    private final Optional<LocalDateTime> startRange;
    private final Optional<LocalDateTime> endRange;
    private final Optional<WorkoutName> workoutNameOptional;

    public FindCommand(Optional<LocalDateTime> startRange,
        Optional<LocalDateTime> endRange,
        Optional<WorkoutName> workoutNameOptional) {
        requireNonNull(startRange);
        requireNonNull(endRange);
        requireNonNull(workoutNameOptional);
        this.startRange = startRange;
        this.endRange = endRange;
        this.workoutNameOptional = workoutNameOptional;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing log find command");

        if (model.isInSession()) {
            throw new CommandException(Command.MESSAGE_SESSION_STARTED);
        }

        Predicate<CompletedWorkout> predicate = session -> true;

        if (workoutNameOptional.isPresent()) {
            predicate = predicate.and(new PredicateFilterLogWorkoutName(workoutNameOptional.get().fullName));
        }

        if (startRange.isPresent()) {
            predicate = predicate.and(session -> session.getStartTime().isAfter(startRange.get()));
        }

        if (endRange.isPresent()) {
            predicate = predicate.and(session -> session.getEndTime().isBefore(endRange.get()));
        }

        model.updateFilteredLogList(predicate);

        String outputMessage = String.format(MESSAGE_LOGS_LISTED_OVERVIEW,
                model.getFilteredLogList().size());
        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && workoutNameOptional.equals(((FindCommand) other).workoutNameOptional)); // state check
    }
}
