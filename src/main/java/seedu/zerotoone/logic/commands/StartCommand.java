package seedu.zerotoone.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.session.OngoingSession;
import seedu.zerotoone.model.session.OngoingWorkout;
import seedu.zerotoone.model.workout.Workout;

/**
 * Starts a new session based on a displayed index from the exercise list.
 */
public class StartCommand extends Command {
    public static final String COMMAND_WORD = "start";
    public static final String MESSAGE_USAGE = "Usage: start WORKOUT_ID";
    public static final String MESSAGE_START_WORKOUT_SUCCESS = "Started workout session: %1$s at ";
    public static final String MESSAGE_IN_SESSION = "There is a workout session already in progress!";
    public static final String MESSAGE_EMPTY_WORKOUT = "Unable to start an empty workout!";
    public static final String MESSAGE_EMPTY_EXERCISE = "Some exercises in this workout are invalid!";
    private final Index workoutID;
    private final FormatStyle formatStyle = FormatStyle.MEDIUM;

    public Index getWorkoutID() {
        return workoutID;
    }

    public StartCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.workoutID = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Workout> lastShownList = model.getFilteredWorkoutList();

        if (workoutID.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        Workout workoutToStart = lastShownList.get(workoutID.getZeroBased());
        List<Exercise> exercises = workoutToStart.getWorkoutExercises();
        if (exercises.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_WORKOUT);
        }

        for (Exercise each: exercises) {
            if (each.getExerciseSets().isEmpty()) {
                throw new CommandException(MESSAGE_EMPTY_EXERCISE);
            }
        }

        if (model.isInSession()) {
            throw new CommandException(MESSAGE_IN_SESSION);
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        OngoingWorkout current = model.startSession(workoutToStart, currentDateTime);

        String formatted = currentDateTime.format(DateTimeFormatter.ofLocalizedDateTime(this.formatStyle));

        String outputMessage = String.format(MESSAGE_START_WORKOUT_SUCCESS,
                workoutToStart.getWorkoutName().toString()) + formatted;
        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartCommand // instanceof handles nulls
                && workoutID.equals(((StartCommand) other).workoutID)); // state check
    }
}
