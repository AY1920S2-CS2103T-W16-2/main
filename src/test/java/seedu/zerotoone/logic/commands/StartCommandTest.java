package seedu.zerotoone.logic.commands;

import static seedu.zerotoone.logic.commands.StartCommand.FORMAT_STYLE;
import static seedu.zerotoone.logic.commands.StartCommand.MESSAGE_SUCCESS;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.getTypicalExerciseList;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.getTypicalWorkoutList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.testutil.ModelStub;

class StartCommandTest {
    private Model model = new ModelManager(new UserPrefs(),
            getTypicalExerciseList(),
            getTypicalWorkoutList(),
            new ScheduleList(),
            new LogList());

    @Test
    public void execute_isInSession_throwsCommandException() {
        StartCommand startCommand = new StartCommand(INDEX_FIRST_OBJECT);
        ModelStub modelStub = new ModelStubInSession();
        assertThrows(CommandException.class, Command.MESSAGE_SESSION_STARTED, () ->
                startCommand.execute(modelStub));
    }

    @Test
    public void executeHelper_isNotInSession_throwsCommandException() throws CommandException {
        StartCommand startCommand = new StartCommand(INDEX_FIRST_OBJECT);

        LocalDateTime currentDateTime = LocalDateTime.now();
        String formatted = currentDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FORMAT_STYLE));
        List<Workout> lastShownList = model.getFilteredWorkoutList();
        Workout workoutToStart = lastShownList.get(INDEX_FIRST_OBJECT.getZeroBased());

        String outputMessage = String.format(MESSAGE_SUCCESS,
                workoutToStart.getWorkoutName().toString()) + formatted;

        Assertions.assertEquals(new CommandResult(outputMessage),
                startCommand.executeHelper(model, currentDateTime));
    }

    @Test
    public void equals() {
        StartCommand startCommand = new StartCommand(INDEX_FIRST_OBJECT);

        // same object -> returns true
        Assertions.assertEquals(startCommand, startCommand);

        // same values -> returns true
        StartCommand startCommandCopy = new StartCommand(INDEX_FIRST_OBJECT);
        Assertions.assertEquals(startCommand, startCommandCopy);

        // different types -> returns false
        Assertions.assertNotEquals(1, startCommand);

        // null -> returns false
        Assertions.assertNotEquals(null, startCommand);
    }

    @Test
    public void getWorkoutId() {
        StartCommand startCommand = new StartCommand(INDEX_FIRST_OBJECT);
        Assertions.assertEquals(startCommand.getWorkoutId(), INDEX_FIRST_OBJECT);
    }

    /**
     * A Model stub that is in session.
     */
    private class ModelStubInSession extends ModelStub {
        @Override
        public boolean isInSession() {
            return true;
        }
    }
}
