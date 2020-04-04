package seedu.zerotoone.model.session;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the session list level.
 */
public class OngoingSetList implements ReadOnlyOngoingSetList {

    private final ObservableList<OngoingSet> internalList;
    private final ObservableList<OngoingSet> internalUnmodifiableList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        internalList = FXCollections.observableArrayList();
        internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);
    }

    public OngoingSetList() {}

    /**
     * Creates an SessionList using the Sessions in the {@code toBeCopied}
     */
    public OngoingSetList(ReadOnlyOngoingSetList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Session list with {@code Sessions}.
     * {@code Sessions} must not contain duplicate Sessions.
     */
    public void setSessionList(List<OngoingSet> sessions) {
        requireNonNull(sessions);
        internalList.setAll(sessions);
    }

    /**
     * Resets the existing data of this {@code SessionList} with {@code newData}.
     */
    public void resetData(ReadOnlyOngoingSetList newData) {
        requireNonNull(newData);
        setSessionList(newData.getOngoingSetList());
    }

    //// Session-level operations
    /**
     * Adds a Session to the Session list.
     */
    public void addSession(OngoingSet set) {
        internalList.add(set);
    }

    /**
     * Removes {@code key} from this {@code SessionList}.
     * {@code key} must exist in the Session list.
     */
    public void removeSession(int key) {
        internalList.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return getOngoingSetList().size() + " Sets";
    }

    @Override
    public ObservableList<OngoingSet> getOngoingSetList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof OngoingSetList // instanceof handles nulls
            && internalList.equals(((OngoingSetList) other).internalList));
    }
}
