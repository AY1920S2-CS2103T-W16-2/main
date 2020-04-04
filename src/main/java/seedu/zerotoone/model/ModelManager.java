package seedu.zerotoone.model;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.StopWatch;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.zerotoone.commons.core.GuiSettings;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.schedule.Schedule;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.schedule.ScheduledWorkout;
import seedu.zerotoone.model.schedule.Scheduler;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.session.OngoingSetList;
import seedu.zerotoone.model.session.OngoingWorkout;
import seedu.zerotoone.model.session.ReadOnlyOngoingSetList;
import seedu.zerotoone.model.session.ReadOnlySessionList;
import seedu.zerotoone.model.session.Session;
import seedu.zerotoone.model.session.SessionList;
import seedu.zerotoone.model.userprefs.ReadOnlyUserPrefs;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;


/**
 * Represents the in-memory model of the exercise list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;

    // Exercise
    private final ExerciseList exerciseList;
    private final FilteredList<Exercise> filteredExercises;

    // Workout
    private final WorkoutList workoutList;
    private final FilteredList<Workout> filteredWorkouts;

    // Session
    private Optional<OngoingWorkout> currentWorkout;
    private final StopWatch stopwatch;
    private final OngoingSetList ongoingSetList;

    // Schedule
    private final Scheduler scheduler;

    // Log
    private final SessionList sessionList;
    private final FilteredList<Session> filteredSessions;

    /**
     * Initializes a ModelManager with the given exerciseList and userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs,
                        ReadOnlyExerciseList exerciseList,
                        ReadOnlyWorkoutList workoutList,
                        ScheduleList scheduleList,
                        ReadOnlySessionList sessionList) {
        super();
        requireAllNonNull(userPrefs,
                exerciseList,
                workoutList,
                scheduleList,
                sessionList);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);

        this.exerciseList = new ExerciseList(exerciseList);
        filteredExercises = new FilteredList<>(this.exerciseList.getExerciseList());

        this.workoutList = new WorkoutList(workoutList);
        filteredWorkouts = new FilteredList<>(this.workoutList.getWorkoutList());

        this.scheduler = new Scheduler(scheduleList);

        this.currentWorkout = Optional.empty();
        this.stopwatch = new StopWatch();
        this.ongoingSetList = new OngoingSetList();

        this.sessionList = new SessionList(sessionList);
        filteredSessions = new FilteredList<>(this.sessionList.getSessionList());
    }

    public ModelManager() {
        this(new UserPrefs(), new ExerciseList(), new WorkoutList(), new ScheduleList(), new SessionList());
    }

    // -----------------------------------------------------------------------------------------
    // Common - User Preferences
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    // -----------------------------------------------------------------------------------------
    // Exercise List
    @Override
    public Path getExerciseListFilePath() {
        return userPrefs.getExerciseListFilePath();
    }

    @Override
    public void setExerciseListFilePath(Path exerciseListFilePath) {
        requireNonNull(exerciseListFilePath);
        userPrefs.setExerciseListFilePath(exerciseListFilePath);
    }

    @Override
    public void setExerciseList(ReadOnlyExerciseList exerciseList) {
        this.exerciseList.resetData(exerciseList);
    }

    @Override
    public ReadOnlyExerciseList getExerciseList() {
        return exerciseList;
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return exerciseList.hasExercise(exercise);
    }

    @Override
    public void deleteExercise(Exercise target) {
        exerciseList.removeExercise(target);
    }

    @Override
    public void addExercise(Exercise exercise) {
        exerciseList.addExercise(exercise);
        updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
    }

    @Override
    public void setExercise(Exercise target, Exercise editedExercise) {
        requireAllNonNull(target, editedExercise);
        exerciseList.setExercise(target, editedExercise);
    }

    @Override
    public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
        requireNonNull(predicate);
        filteredExercises.setPredicate(predicate);
    }

    // -----------------------------------------------------------------------------------------
    // Session List

    @Override
    public Path getSessionListFilePath() {
        return userPrefs.getLogListFilePath();
    }

    @Override
    public void deleteSession(int targetId) {
        sessionList.removeSession(targetId);
    }

    @Override
    public void setSessionListFilePath(Path sessionListFilePath) {
        requireNonNull(sessionListFilePath);
        userPrefs.setLogListFilePath(sessionListFilePath);
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return filteredExercises;
    }

    @Override
    public void updateFilteredSessionList(Predicate<Session> predicate) {
        requireNonNull(predicate);
        filteredSessions.setPredicate(predicate);
    }

    @Override
    public boolean isInSession() {
        return this.currentWorkout.isPresent();
    }

    @Override
    public OngoingWorkout startSession(Workout workoutToStart, LocalDateTime currentDateTime) {
        OngoingWorkout ongoingWorkout = new OngoingWorkout(workoutToStart, currentDateTime);
        this.currentWorkout = Optional.of(ongoingWorkout);
        ongoingSetList.setSessionList(ongoingWorkout.getRemainingSets());
        return ongoingWorkout;
    }

    @Override
    public void stopSession(LocalDateTime currentDateTime) {
        OngoingWorkout ongoingWorkout = this.currentWorkout.get();
        CompletedWorkout workout = ongoingWorkout.finish(currentDateTime);
        ongoingSetList.resetData(new OngoingSetList());
        // Jiachen u need to fix this
        // this.sessionList.addSession(session);
        this.currentWorkout = Optional.empty();
    }

    @Override
    public Optional<OngoingWorkout> getCurrentWorkout() {
        return Optional.ofNullable(this.currentWorkout.orElse(null));
    }

    @Override
    public ReadOnlyOngoingSetList getOngoingSetList() {
        return this.ongoingSetList;
    }

    // -----------------------------------------------------------------------------------------
    // Schedule
    @Override
    public ScheduleList getScheduleList() {
        return scheduler.getScheduleList();
    }

    @Override
    public ReadOnlySessionList getSessionList() {
        return sessionList;
    }

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return filteredSessions;
    }

    @Override
    public boolean hasSchedule(Schedule schedule) {
        return scheduler.hasSchedule(schedule);
    }

    @Override
    public void addSchedule(Schedule schedule) {
        scheduler.addSchedule(schedule);
    }

    @Override
    public void setSchedule(Schedule scheduleToEdit, Schedule editedSchedule) {
        scheduler.setSchedule(scheduleToEdit, editedSchedule);
    }

    @Override
    public void deleteScheduledWorkout(ScheduledWorkout scheduledWorkoutToDelete) {
        scheduler.deleteScheduledWorkout(scheduledWorkoutToDelete);
    }

    @Override
    public ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList() {
        return scheduler.getSortedScheduledWorkoutList();
    }

    // -----------------------------------------------------------------------------------------
    // Workout List
    @Override
    public Path getWorkoutListFilePath() {
        return userPrefs.getWorkoutListFilePath();
    }

    @Override
    public void setWorkoutListFilePath(Path workoutListFilePath) {
        requireNonNull(workoutListFilePath);
        userPrefs.setWorkoutListFilePath(workoutListFilePath);
    }

    @Override
    public void setWorkoutList(ReadOnlyWorkoutList workoutList) {
        this.workoutList.resetData(workoutList);
    }

    @Override
    public ReadOnlyWorkoutList getWorkoutList() {
        return workoutList;
    }

    @Override
    public boolean hasWorkout(Workout workout) {
        requireNonNull(workout);
        return workoutList.hasWorkout(workout);
    }

    @Override
    public void deleteWorkout(Workout target) {
        workoutList.removeWorkout(target);
    }

    @Override
    public void addWorkout(Workout workout) {
        workoutList.addWorkout(workout);
        updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
    }

    @Override
    public void setWorkout(Workout target, Workout editedWorkout) {
        requireAllNonNull(target, editedWorkout);
        workoutList.setWorkout(target, editedWorkout);
    }

    @Override
    public ObservableList<Workout> getFilteredWorkoutList() {
        return filteredWorkouts;
    }

    @Override
    public void updateFilteredWorkoutList(Predicate<Workout> predicate) {
        requireNonNull(predicate);
        filteredWorkouts.setPredicate(predicate);
    }

    // -----------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return exerciseList.equals(other.exerciseList)
                && userPrefs.equals(other.userPrefs)
                && filteredExercises.equals(other.filteredExercises)
                && sessionList.equals(other.sessionList);
        // && scheduler.equals(other.scheduler);   // STEPH_TODO: implement later
    }
}
