package seedu.zerotoone.ui.views.home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.ui.util.UiPart;

/**
 * An UI component that displays information of a {@code Exercise}.
 */
public class LastSessionCard extends UiPart<Region> {
    private static final String FXML = "home/LastSessionCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ExerciseList level 4</a>
     */

    @FXML
    private Label sessionName;
    @FXML
    private Label sessionPastReps;

    public LastSessionCard(CompletedSet completedSet) {
        super(FXML);
        String name = completedSet.getExerciseName().fullName;
        String set = String.valueOf(completedSet.getIndex() + 1);
        String weight = completedSet.getWeight().value;
        String reps = completedSet.getNumReps().value;
        sessionName.setText(name + ": " + "Set " + set);
        sessionPastReps.setText(reps + " reps, " + weight + "kg");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LastSessionCard)) {
            return false;
        }

        // state check
        LastSessionCard card = (LastSessionCard) other;
        return sessionName.getText().equals(card.sessionName.getText())
                && sessionPastReps.getText().equals(card.sessionPastReps.getText());
    }
}
