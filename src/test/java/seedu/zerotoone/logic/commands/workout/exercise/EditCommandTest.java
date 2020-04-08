package seedu.zerotoone.logic.commands.workout.exercise;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutName;
import seedu.zerotoone.testutil.workout.WorkoutBuilder;

public class EditCommandTest {
    // private Model model = new ModelManager(new UserPrefs(),
    //         getTypicalExerciseList(),
    //         getTypicalWorkoutList(),
    //         new ScheduleList(),
    //         new LogList());

    // @Test
    // public void constructor_nullWorkout_throwsNullPointerException() {
    //     assertThrows(NullPointerException.class, () -> new EditCommand(null, null));
    // }
    // @Test
    // public void execute_validIndexUnfilteredList_success() {
    //     Workout workoutToEdit = model.getFilteredWorkoutList().get(INDEX_FIRST_OBJECT.getZeroBased());
    //     Workout editedWorkout = new WorkoutBuilder(workoutToEdit).withWorkoutName("Strength Training").build();
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT, new WorkoutName("Strength Training"));

    //     String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_WORKOUT_SUCCESS,
    //             editedWorkout.getWorkoutName());

    //     ModelManager expectedModel = new ModelManager(new UserPrefs(),
    //             model.getExerciseList(),
    //             model.getWorkoutList(),
    //             model.getScheduleList(),
    //             model.getLogList());

    //     expectedModel.setWorkout(workoutToEdit, editedWorkout);

    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    // @Test
    // public void equals() {
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_OBJECT, LEGS_WORKOUT.getWorkoutName());
    //     EditCommand otherEditCommand = new EditCommand(INDEX_SECOND_OBJECT, ARMS_WORKOUT.getWorkoutName());

    //     // same object -> returns true
    //     assertTrue(editCommand.equals(editCommand));

    //     // same values -> returns true
    //     EditCommand editCommandCopy = new EditCommand(INDEX_FIRST_OBJECT, LEGS_WORKOUT.getWorkoutName());
    //     assertTrue(editCommand.equals(editCommandCopy));

    //     // different types -> returns false
    //     assertFalse(editCommand.equals(1));

    //     // null -> returns false
    //     assertFalse(editCommand.equals(null));

    //     // different workout -> returns false
    //     assertFalse(editCommand.equals(otherEditCommand));
    // }
}
