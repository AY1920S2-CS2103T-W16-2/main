package seedu.zerotoone.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.zerotoone.commons.core.GuiSettings;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.logic.Logic;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.model.session.OngoingSet;
import seedu.zerotoone.model.session.OngoingSetList;
import seedu.zerotoone.ui.util.UiPart;
import seedu.zerotoone.ui.views.exercise.ExerciseListPanel;
import seedu.zerotoone.ui.views.home.HomePanel;
import seedu.zerotoone.ui.views.log.LogListPanel;
import seedu.zerotoone.ui.views.schedule.ScheduledWorkoutListPanel;
import seedu.zerotoone.ui.views.workout.WorkoutListPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private HomePanel homePanel;
    private ExerciseListPanel exerciseListPanel;
    private WorkoutListPanel workoutListPanel;
    private ScheduledWorkoutListPanel scheduledWorkoutListPanel;
    private LogListPanel logListPanel;

    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ReportWindow reportWindow;

    @FXML
    private VBox tabsVBox;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private TabPane tabPanePlaceHolder;

    @FXML
    private StackPane homeContentPlaceholder;

    @FXML
    private StackPane exerciseContentPlaceholder;

    @FXML
    private StackPane workoutContentPlaceholder;

    @FXML
    private StackPane scheduleContentPlaceholder;

    @FXML
    private StackPane logContentPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        reportWindow = new ReportWindow();

        tabPanePlaceHolder.widthProperty().addListener((observable, oldValue, newValue) -> {
            tabPanePlaceHolder.setTabMinWidth(newValue.doubleValue() / 5 - 24);
            tabPanePlaceHolder.setTabMinWidth(newValue.doubleValue() / 5 - 24);
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        homePanel = new HomePanel(logic.getOngoingSetList());
        homeContentPlaceholder.getChildren().add(homePanel.getRoot());

        exerciseListPanel = new ExerciseListPanel(logic.getFilteredExerciseList());
        exerciseContentPlaceholder.getChildren().add(exerciseListPanel.getRoot());

        workoutListPanel = new WorkoutListPanel(logic.getFilteredWorkoutList());
        workoutContentPlaceholder.getChildren().add(workoutListPanel.getRoot());

        scheduledWorkoutListPanel = new ScheduledWorkoutListPanel(logic.getSortedScheduledWorkoutList());
        scheduleContentPlaceholder.getChildren().add(scheduledWorkoutListPanel.getRoot());


        logListPanel = new LogListPanel(logic.getFilteredSessionList());
        logContentPlaceholder.getChildren().add(logListPanel.getRoot());

        tabPanePlaceHolder.setMinWidth(530);
        tabPanePlaceHolder.setMinHeight(200);


        VBox.setVgrow(tabPanePlaceHolder, Priority.ALWAYS);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the report window or focuses on it if it's already opened.
     */
    @FXML
    public void handleReport() {
        if (!reportWindow.isShowing()) {
            reportWindow.show();
        } else {
            reportWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        reportWindow.hide();
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.zerotoone.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowReport()) {
                handleReport();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
