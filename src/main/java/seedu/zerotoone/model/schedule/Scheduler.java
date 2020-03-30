package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 * STEPH_TODO_JAVADOC
 */
public class Scheduler {

    private final ScheduleList scheduleList;
    private ScheduledWorkoutList scheduledWorkoutList;
    private SortedList<ScheduledWorkout> sortedScheduledWorkoutList;

    public Scheduler(ScheduleList scheduleList) {
        this.scheduleList = new ScheduleList(scheduleList);
        this.scheduledWorkoutList = new ScheduledWorkoutList();
        this.sortedScheduledWorkoutList = new SortedList<>(
                scheduledWorkoutList.getScheduledWorkoutList(),
                ScheduledWorkout.getComparator());
        populateSortedScheduledWorkoutList();
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList() {
        return sortedScheduledWorkoutList;
    }

    /**
     *
     * @param schedule
     * @return
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return scheduleList.hasSchedule(schedule);
    }

    /**
     *
     * @param schedule
     */
    public void addSchedule(Schedule schedule) {
        requireNonNull(schedule);
        scheduleList.addSchedule(schedule);

        populateSortedScheduledWorkoutList();
    }

    public void setSchedule(Schedule scheduleToEdit, Schedule editedSchedule) {
        scheduleList.setSchedule(scheduleToEdit, editedSchedule);

        populateSortedScheduledWorkoutList();
    }

    /**
     *
     * @param scheduledWorkoutToDelete
     */
    public void deleteScheduledWorkout(ScheduledWorkout scheduledWorkoutToDelete) {
        requireNonNull(scheduledWorkoutToDelete);
        Schedule scheduleToDelete = scheduledWorkoutToDelete.getSchedule();
        scheduleList.removeSchedule(scheduleToDelete);

        populateSortedScheduledWorkoutList();
    }

    /**
     *
     */
    private void populateSortedScheduledWorkoutList() {
        List<ScheduledWorkout> newScheduledWorkouts = scheduleList.getScheduleList().stream()
                .map(Schedule::getScheduledWorkout)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        setScheduledWorkouts(newScheduledWorkouts);
    }

    public void setScheduledWorkouts(List<ScheduledWorkout> scheduledWorkouts) {
        this.scheduledWorkoutList.setScheduledWorkouts(scheduledWorkouts);
    }
}
