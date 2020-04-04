package seedu.zerotoone.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.session.OngoingWorkout;
import seedu.zerotoone.model.session.SessionSet;

/**
 * Completes the next up exerciseQueue in the session.
 */
public class SkipCommand extends Command {
    public static final String COMMAND_WORD = "skip";
    public static final String MESSAGE_USAGE = "Usage: skip";
    public static final String MESSAGE_SKIP_SET = "Skipped set: %1$s";
    public static final String MESSAGE_SKIPPED_LAST = "You have skipped the last set, "
            + "your workout session is done and saved!";
    public static final String MESSAGE_NOT_STARTED = "There is no session in progress!";
    private final FormatStyle formatStyle = FormatStyle.MEDIUM;

    public SkipCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isInSession()) {
            throw new CommandException((MESSAGE_NOT_STARTED));
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        OngoingWorkout current = model.getCurrentWorkout().get();

        SessionSet set = current.skip();

        String outputMessage = String.format(MESSAGE_SKIP_SET, set.toString());

        if (!current.hasExerciseLeft()) {
            model.stopSession(currentDateTime);
            outputMessage = outputMessage + "\n" + MESSAGE_SKIPPED_LAST;
        }

        String formatted = currentDateTime.format(DateTimeFormatter.ofLocalizedDateTime(this.formatStyle));

        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
