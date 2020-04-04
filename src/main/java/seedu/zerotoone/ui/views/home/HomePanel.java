package seedu.zerotoone.ui.views.home;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.zerotoone.model.session.OngoingSet;
import seedu.zerotoone.ui.util.UiPart;

/**
 * Panel containing the home page.
 */
public class HomePanel extends UiPart<Region> {
    private static final String FXML = "home/HomePanel.fxml";

    @FXML
    private ListView<OngoingSet> ongoingSessionView;

    public HomePanel(ObservableList<OngoingSet> ongoingSetList) {
        super(FXML);
        ongoingSessionView.setItems(ongoingSetList);
        ongoingSessionView.setCellFactory(listView -> new OngoingSetViewCell());
    }
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Exercise} using a {@code ExerciseCard}.
     */
    class OngoingSetViewCell extends ListCell<OngoingSet> {
        @Override
        protected void updateItem(OngoingSet ongoingSet, boolean empty) {
            super.updateItem(ongoingSet, empty);

            if (empty || ongoingSet == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OngoingSessionCard(ongoingSet).getRoot());
            }
        }
    }
}
