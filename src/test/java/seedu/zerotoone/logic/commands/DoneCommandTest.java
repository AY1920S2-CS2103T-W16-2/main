package seedu.zerotoone.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.zerotoone.logic.commands.DoneCommand.MESSAGE_NONE_LEFT;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.testutil.ModelStub;

class DoneCommandTest {

    @Test
    public void execute_isInSession_throwsCommandException() throws CommandException {
        DoneCommand doneCommand = new DoneCommand();
        ModelStub modelStub = new ModelStubInSession();
        CompletedSet set = new CompletedSet(new Weight("10"), new NumReps("10"), true);
        assertEquals(new CommandResult(String.format(DoneCommand.MESSAGE_SUCCESS, set.toString())),
                doneCommand.execute(modelStub));
    }

    @Test
    public void execute_isInSessionNoneLeft_throwsCommandException() throws CommandException {
        DoneCommand doneCommand = new DoneCommand();
        ModelStub modelStub = new ModelStubInSessionNoneLeft();
        CompletedSet set = new CompletedSet(new Weight("10"), new NumReps("10"), true);
        assertEquals(new CommandResult(String.format(DoneCommand.MESSAGE_SUCCESS, set.toString())
                + "\n" + MESSAGE_NONE_LEFT), doneCommand.execute(modelStub));
    }

    @Test
    public void execute_isNotInSession_throwsCommandException() {
        DoneCommand doneCommand = new DoneCommand();
        ModelStub modelStub = new ModelStubNotInSession();
        assertThrows(CommandException.class, Command.MESSAGE_SESSION_NOT_STARTED, () ->
                doneCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        DoneCommand doneCommand = new DoneCommand();

        // same object -> returns true
        assertEquals(doneCommand, doneCommand);

        // same values -> returns true
        DoneCommand doneCommandCopy = new DoneCommand();
        assertEquals(doneCommand, doneCommandCopy);

        // different types -> returns false
        assertNotEquals(1, doneCommand);

        // null -> returns false
        assertNotEquals(null, doneCommand);
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
        public Boolean hasExerciseLeft() {
            return true;
        }

        @Override
        public CompletedSet done() {
            return new CompletedSet(new Weight("10"), new NumReps("10"), true);
        }
    }

    /**
     * A Model stub that is in session.
     */
    private class ModelStubInSessionNoneLeft extends ModelStubInSession {
        @Override
        public Boolean hasExerciseLeft() {
            return false;
        }

        @Override
        public void stopSession(LocalDateTime currentDateTime) {
        }
    }
}
