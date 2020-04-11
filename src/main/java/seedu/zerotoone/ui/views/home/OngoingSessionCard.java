package seedu.zerotoone.ui.views.home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.session.OngoingSet;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code Exercise}.
 */
public class OngoingSessionCard extends UiPart<Region> {
    private static final String FXML = "home/OngoingSessionCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ExerciseList level 4</a>
     */

    @FXML
    private Label sessionSet;
    @FXML
    private Label sessionReps;

    public OngoingSessionCard(OngoingSet ongoingSet) {
        super(FXML);
        String name = ongoingSet.getExerciseName().fullName;
        String set = String.valueOf(ongoingSet.getIndex() + 1);
        String weight = ongoingSet.getWeight().value;
        String reps = ongoingSet.getNumReps().value;
        sessionSet.setText(name + ": " + "Set " + set);
        sessionReps.setText(reps + " reps, " + weight + "kg");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OngoingSessionCard)) {
            return false;
        }

        // state check
        OngoingSessionCard card = (OngoingSessionCard) other;
        return sessionSet.getText().equals(card.sessionSet.getText())
                && sessionReps.getText().equals(card.sessionReps.getText());
    }
}
