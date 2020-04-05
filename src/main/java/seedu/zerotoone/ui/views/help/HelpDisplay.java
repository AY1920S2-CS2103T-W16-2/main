package seedu.zerotoone.ui.views.help;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.zerotoone.logic.commands.util.CommandSection;
import seedu.zerotoone.ui.util.UiPart;

/**
 * Represents a help window to show all ZeroToOne commands.
 */
public class HelpDisplay extends UiPart<Region> {
    private static final String FXML = "help/HelpDisplay.fxml";

    @FXML
    private ListView<CommandSection> allCommands;

    /**
     * Constructs a Help Display to display all commands.
     */
    public HelpDisplay(ObservableList<CommandSection> commandSectionList) {
        super(FXML);
        allCommands.setItems(commandSectionList);
        allCommands.setCellFactory(listView -> new CommandSectionViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the commands.
     */
    class CommandSectionViewCell extends ListCell<CommandSection> {
        @Override
        protected void updateItem(CommandSection commandSection, boolean empty) {
            super.updateItem(commandSection, empty);

            if (empty || commandSection == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CommandSectionCard(commandSection).getRoot());
            }
        }
    }
}
