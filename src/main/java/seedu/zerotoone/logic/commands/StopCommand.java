package seedu.zerotoone.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;

/**
 * Deletes a exercise identified using it's displayed index from the exercise list.
 */
public class StopCommand extends Command {
    public static final String COMMAND_WORD = "stop";
    public static final String MESSAGE_USAGE = "Usage: stop";
    public static final String MESSAGE_STOP_SESSION_SUCCESS = "Stopped workout session: %1$s at ";
    public static final String MESSAGE_NOT_STARTED = "There is no workout session in progress!";
    private final FormatStyle formatStyle = FormatStyle.MEDIUM;

    public StopCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isInSession()) {
            throw new CommandException((MESSAGE_NOT_STARTED));
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        String formatted = currentDateTime.format(DateTimeFormatter.ofLocalizedDateTime(this.formatStyle));
        String outputMessage = String.format(MESSAGE_STOP_SESSION_SUCCESS,
                model.getCurrentWorkout().get().getWorkoutName().toString()) + formatted;

        model.stopSession(currentDateTime);

        return new CommandResult(outputMessage);
    }
}
