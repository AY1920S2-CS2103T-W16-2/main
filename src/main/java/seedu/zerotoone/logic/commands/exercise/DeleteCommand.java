package seedu.zerotoone.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.zerotoone.commons.core.Messages;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;

/**
 * Deletes a exercise identified using it's displayed index from the exercise list.
 */
public class DeleteCommand extends ExerciseCommand {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = "Usage: exercise delete EXERCISE_ID";
    public static final String MESSAGE_DELETE_EXERCISE_SUCCESS = "Deleted Exercise: %1$s";
    private final Index exerciseId;

    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.exerciseId = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (exerciseId.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        Exercise exerciseToDelete = lastShownList.get(exerciseId.getZeroBased());
        model.deleteExercise(exerciseToDelete);

        String outputMessage = String.format(MESSAGE_DELETE_EXERCISE_SUCCESS,
                exerciseToDelete.getExerciseName().toString());
        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && exerciseId.equals(((DeleteCommand) other).exerciseId)); // state check
    }
}
