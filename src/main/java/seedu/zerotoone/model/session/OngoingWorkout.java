//@@author gb3h
package seedu.zerotoone.model.session;

import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Represents a single Session.
 */
public class OngoingWorkout {

    // Identity fields
    private final LocalDateTime startTime;
    private final WorkoutName workoutName;
    private final Queue<OngoingSession> toDo = new LinkedList<>();
    private final Queue<Session> done = new LinkedList<>();

    /**
     * Every field must be present and not null.
     */
    public OngoingWorkout(Workout workout, LocalDateTime startTime) {
        requireAllNonNull(workout);
        this.workoutName = workout.getWorkoutName();
        this.toDo.addAll(workout.getWorkoutExercises().stream().map((x) -> new OngoingSession(x, startTime))
                .collect(Collectors.toList()));
        this.startTime = startTime;
    }

    public WorkoutName getWorkoutName() {
        return this.workoutName;
    }

    public List<OngoingSession> getRemainingExercises() {
        return Collections.unmodifiableList(new LinkedList<>(this.toDo));
    }

    public List<OngoingSet> getRemainingSets() {
        List<OngoingSet> sets = new LinkedList<>();
        for (OngoingSession o: getRemainingExercises()) {
            int i = 1;
            for (ExerciseSet e: o.getRemaining()) {
                sets.add(new OngoingSet(e, o.getExerciseName(), i));
                i++;
            }
        }
        return sets;
    }

    /**
     * Completes the top exercise that is left in the exerciseQueue and puts it into the done list.
     * @return set: the done SessionSet
     */
    public SessionSet done() {
        SessionSet set = toDo.peek().done();
        if (!toDo.peek().hasSetLeft()) {
            done.offer(toDo.poll().finish(startTime));
        }
        return set;
    }

    /**
     * Skips the top exercise that is left in the exerciseQueue and puts it into the done list.
     * @return set: the skipped SessionSet
     */
    public SessionSet skip() {
        SessionSet set = toDo.peek().skip();
        if (!toDo.peek().hasSetLeft()) {
            done.offer(toDo.poll().finish(startTime));
        }
        return set;
    }

    /**
     *  Returns true if there are still sets remaining in the queue.
     */
    public boolean hasExerciseLeft() {
        return !toDo.isEmpty();
    }

    public Optional<OngoingSession> peek() {
        return Optional.ofNullable(toDo.peek());
    }

    public Optional<Session> last() {
        return Optional.ofNullable(done.peek());
    }

    /**
     * Ends a Session (prematurely if queue is still filled) with a endTime, and labelling
     * incomplete sets as unfinished.
     * @param endTime the time of completion
     * @return returns a new immutable CompletedSession.
     */
    public CompletedWorkout finish(LocalDateTime endTime) {
        while (this.hasExerciseLeft()) {
            done.offer(toDo.poll().finish(startTime));
        }
        return new CompletedWorkout(this.workoutName, new LinkedList<>(done),
                startTime, endTime);
    }
}
