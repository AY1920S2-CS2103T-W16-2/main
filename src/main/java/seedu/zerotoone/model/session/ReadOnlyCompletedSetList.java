package seedu.zerotoone.model.session;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an exercise list
 */
public interface ReadOnlyCompletedSetList {

    /**
     * Returns an unmodifiable view of the exercises list.
     * This list will not contain any duplicate exercises.
     */
    ObservableList<CompletedSet> getCompletedSetList();

}
