import barry.Barry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI of the application. This class manages the
 * graphical user interface (GUI)
 * interactions, handling user input and Barry's responses. It links the UI
 * elements defined in the FXML file
 * to this controller class and contains logic for creating dialog boxes based
 * on user and system input.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane; // Scrollable pane that contains the dialog boxes.
    @FXML
    private VBox dialogContainer; // VBox that holds all the dialog boxes (both user and Barry).
    @FXML
    private TextField userInput; // Input field where the user types their commands.
    @FXML
    private Button sendButton; // Button to send the user's command.

    private Barry barry; // Instance of the Barry class, which handles logic related to task management.

    // Image representing the user and Barry in the dialog box.
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/pfp.png"));
    private Image barryImage = new Image(this.getClass().getResourceAsStream("/images/cat.png"));

    /**
     * Initializes the main window.
     * Sets up the scrollPane to automatically scroll down as more dialog boxes are
     * added to the dialogContainer.
     */
    @FXML
    public void initialize() {
        // Bind the scrollPane's vertical value to the height of the dialogContainer,
        // ensuring it automatically scrolls as more messages are added.
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Barry instance that will be used by this controller to handle
     * user input.
     * This allows the controller to delegate logic handling to Barry.
     *
     * @param b The Barry instance to be used for processing user input and
     *          generating responses.
     */
    public void setBarry(Barry b) {
        this.barry = b;
    }

    /**
     * Handles the user's input when the send button is clicked or the Enter key is
     * pressed.
     * This method creates two dialog boxes: one showing the user's input and
     * another showing Barry's response.
     * Both dialog boxes are appended to the dialogContainer.
     * After processing, the user input field is cleared to allow for new input.
     */
    @FXML
    private void handleUserInput() {
        // Get the user's input from the text field.
        String input = userInput.getText();

        // Generate a response from Barry based on the user's input.
        String response = this.barry.getResponse(input);

        // Add the user's dialog and Barry's response as dialog boxes to the
        // dialogContainer.
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage), // Create a dialog box for the user's input.
                DialogBox.getDialogBox(response, barryImage) // Create a dialog box for Barry's response.
        );

        // Clear the input field after processing the user's command.
        userInput.clear();
    }
}
