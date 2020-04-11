package seedu.zerotoone.logic.commands;

import static seedu.zerotoone.logic.commands.SkipCommand.MESSAGE_SKIPPED_LAST;
import static seedu.zerotoone.logic.commands.SkipCommand.MESSAGE_SUCCESS;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.testutil.ModelStub;

class SkipCommandTest {

    @Test
    public void execute_isInSession_throwsCommandException() throws CommandException {
        SkipCommand skipCommand = new SkipCommand();
        ModelStub modelStub = new ModelStubInSession();
        CompletedSet set = new CompletedSet(new Weight("10"), new NumReps("10"), true);
        Assertions.assertEquals(new CommandResult(String.format(MESSAGE_SUCCESS, set.toString())),
                skipCommand.execute(modelStub));
    }

    @Test
    public void execute_isInSessionNoneLeft_throwsCommandException() throws CommandException {
        SkipCommand skipCommand = new SkipCommand();
        ModelStub modelStub = new ModelStubInSessionNoneLeft();
        CompletedSet set = new CompletedSet(new Weight("10"), new NumReps("10"), true);
        Assertions.assertEquals(new CommandResult(String.format(MESSAGE_SUCCESS, set.toString())
                        + "\n" + MESSAGE_SKIPPED_LAST), skipCommand.execute(modelStub));
    }

    @Test
    public void execute_isNotInSession_throwsCommandException() {
        SkipCommand skipCommand = new SkipCommand();
        ModelStub modelStub = new ModelStubNotInSession();
        assertThrows(CommandException.class, Command.MESSAGE_SESSION_NOT_STARTED, () ->
                skipCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        SkipCommand skipCommand = new SkipCommand();

        // same object -> returns true
        Assertions.assertEquals(skipCommand, skipCommand);

        // same values -> returns true
        SkipCommand skipCommandCopy = new SkipCommand();
        Assertions.assertEquals(skipCommand, skipCommandCopy);

        // different types -> returns false
        Assertions.assertNotEquals(1, skipCommand);

        // null -> returns false
        Assertions.assertNotEquals(null, skipCommand);
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
        public CompletedSet skip() {
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
