package seedu.zerotoone.logic;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.zerotoone.model.log.StatisticsData;
import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * The type Statistics.
 */
public class Statistics {
    /**
     * Generate statistics data.
     *
     * @param workouts       the workouts
     * @param startDateRange the start date range
     * @param endDateRange   the end date range
     * @return the statistics data
     */
    public static StatisticsData generate(List<CompletedWorkout> workouts, Optional<LocalDateTime> startDateRange,
                                          Optional<LocalDateTime> endDateRange) {

        LocalDateTime startDateTime = startDateRange.orElseGet(() ->
            Statistics.getEarliestWorkoutStartTime(workouts));
        LocalDateTime endDateTime = endDateRange.orElseGet(() ->
            Statistics.getLatestEndDate(workouts));

        workouts.removeIf(workout -> workout.getStartTime().isBefore(startDateTime));
        workouts.removeIf(workout -> workout.getEndTime().isAfter(endDateTime));

        Integer workoutCount = workouts.size();

        if (workoutCount.equals(0)) {
            return new StatisticsData();
        }

        Duration totalWorkoutDuration = workouts.stream().map(
            workout -> Duration.between(workout.getStartTime(), workout.getEndTime()))
            .reduce(Duration.ZERO, Duration::plus);

        long numberOfDays = Duration.between(startDateTime, endDateTime).toDays() + 1;

        Duration averageTimePerDay = totalWorkoutDuration.dividedBy(numberOfDays);

        return new StatisticsData(workouts,
            startDateTime,
            endDateTime,
            workoutCount,
            totalWorkoutDuration,
            averageTimePerDay);
    }

    /**
     * Gets earliest workout start time.
     *
     * @param workouts the workouts
     * @return the earliest workout start time
     */
    public static LocalDateTime getEarliestWorkoutStartTime(List<CompletedWorkout> workouts) {
        LocalDateTime earliest = LocalDateTime.MAX;

        for (CompletedWorkout workout : workouts) {
            if (workout.getStartTime().isBefore(earliest)) {
                earliest = workout.getStartTime();
            }
        }

        return earliest;
    }

    /**
     * Gets latest end date.
     *
     * @param workouts the workouts
     * @return the latest end date
     */
    public static LocalDateTime getLatestEndDate(List<CompletedWorkout> workouts) {
        LocalDateTime latest = LocalDateTime.MIN;

        for (CompletedWorkout workout : workouts) {
            if (workout.getEndTime().isAfter(latest)) {
                latest = workout.getEndTime();
            }
        }

        return latest;
    }
}
