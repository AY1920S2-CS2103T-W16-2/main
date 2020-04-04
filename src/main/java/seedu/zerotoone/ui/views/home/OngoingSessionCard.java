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
    private Label exerciseWeight;
    @FXML
    private Label setId;
    @FXML
    private Label exerciseName;
    @FXML
    private Label exerciseReps;

    public OngoingSessionCard(OngoingSet ongoingSet) {
        super(FXML);
        exerciseName.setText(ongoingSet.getExerciseName().fullName);
        setId.setText(String.valueOf(ongoingSet.getIndex()));
        exerciseWeight.setText(ongoingSet.getWeight().value);
        exerciseReps.setText(ongoingSet.getNumReps().value);
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
        return setId.getText().equals(card.setId.getText())
                && exerciseName.getText().equals(card.exerciseName.getText())
                && exerciseReps.getText().equals(card.exerciseReps.getText())
                && exerciseWeight.getText().equals(card.exerciseWeight.getText());
    }
}
