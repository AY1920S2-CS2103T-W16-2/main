package seedu.zerotoone.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.zerotoone.logic.commands.StopCommand.FORMAT_STYLE;
import static seedu.zerotoone.logic.commands.StopCommand.MESSAGE_SUCCESS;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.getTypicalWorkoutList;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.model.session.OngoingWorkout;
import seedu.zerotoone.testutil.ModelStub;

class StopCommandTest {

    @Test
    public void executeHelper_isInSession_throwsCommandException() {
        StopCommand stopCommand = new StopCommand();
        ModelStub modelStub = new ModelStubInSession();
        CompletedSet set = new CompletedSet(new Weight("10"), new NumReps("10"), true);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formatted = currentDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FORMAT_STYLE));
        assertEquals(new CommandResult(String.format(MESSAGE_SUCCESS,
                modelStub.getCurrentWorkout().get().getWorkoutName().toString()) + formatted),
                stopCommand.executeHelper(modelStub, currentDateTime));
    }

    @Test
    public void execute_isNotInSession_throwsCommandException() {
        StopCommand stopCommand = new StopCommand();
        ModelStub modelStub = new ModelStubNotInSession();
        assertThrows(CommandException.class, Command.MESSAGE_SESSION_NOT_STARTED, () ->
                stopCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        StopCommand stopCommand = new StopCommand();

        // same object -> returns true
        assertEquals(stopCommand, stopCommand);

        // same values -> returns true
        StopCommand stopCommandCopy = new StopCommand();
        assertEquals(stopCommand, stopCommandCopy);

        // different types -> returns false
        assertNotEquals(1, stopCommand);

        // null -> returns false
        assertNotEquals(null, stopCommand);
    }

    /**
     * A Model stub that is not in session.
     */
    private class ModelStubNotInSession extends ModelStub {
        @Override
        public boolean isInSession() {
            return false;
        }
    }

    /**
     * A Model stub that is in session.
     */
    private class ModelStubInSession extends ModelStub {
        @Override
        public boolean isInSession() {
            return true;
        }

        @Override
        public Optional<OngoingWorkout> getCurrentWorkout() {
            LocalDateTime testDateTime = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
            return Optional.of(new OngoingWorkout(getTypicalWorkoutList().getWorkoutList().get(0), testDateTime));
        }

        @Override
        public void stopSession(LocalDateTime currentDateTime) {
        }
    }
}
