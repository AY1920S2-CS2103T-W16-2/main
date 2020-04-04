package seedu.zerotoone.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.zerotoone.commons.core.GuiSettings;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.logic.parser.ParserManager;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.schedule.ScheduledWorkout;
import seedu.zerotoone.model.session.OngoingSet;
import seedu.zerotoone.model.session.Session;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ParserManager parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        parser = new ParserManager();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = parser.parse(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveExerciseList(model.getExerciseList());
            storage.saveWorkoutList(model.getWorkoutList());
            storage.saveScheduleList(model.getScheduleList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    // -----------------------------------------------------------------------------------------
    // Common
    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    // -----------------------------------------------------------------------------------------
    // Exercise List
    @Override
    public ReadOnlyExerciseList getExerciseList() {
        return model.getExerciseList();
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return model.getFilteredExerciseList();
    }

    @Override
    public Path getExerciseListFilePath() {
        return model.getExerciseListFilePath();
    }

    @Override
    public ObservableList<Session> getSessionList() {
        return model.getSessionList().getSessionList();
    }

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return model.getFilteredSessionList();
    }

    @Override
    public Path getSessionListFilePath() {
        return model.getSessionListFilePath();
    }

    // -----------------------------------------------------------------------------------------
    // Workout List
    @Override
    public ReadOnlyWorkoutList getWorkoutList() {
        return model.getWorkoutList();
    }

    @Override
    public ObservableList<Workout> getFilteredWorkoutList() {
        return model.getFilteredWorkoutList();
    }

    @Override
    public Path getWorkoutListFilePath() {
        return model.getWorkoutListFilePath();
    }

    // -----------------------------------------------------------------------------------------
    // Schedule
    @Override
    public ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList() {
        return model.getSortedScheduledWorkoutList();
    }

    @Override
    public ObservableList<OngoingSet> getOngoingSetList() {
        return model.getOngoingSetList().getOngoingSetList();
    }
}
