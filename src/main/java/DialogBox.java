import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The DialogBox class represents a dialog box component in the GUI, consisting
 * of a label for
 * text (dialog) and an ImageView to show the speaker's profile picture.
 * It can display the user's dialog or flip the position to represent the
 * system's or other speakers' dialog.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog; // Label to display the dialog text
    @FXML
    private ImageView displayPicture; // ImageView to display the speaker's image

    /**
     * Private constructor for DialogBox.
     * Loads the associated FXML file to initialize the component and sets the
     * dialog text and image.
     * 
     * @param text The text to display in the dialog box.
     * @param img  The image to display in the dialog box.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this); // Set the controller for the FXML to this instance
            fxmlLoader.setRoot(this); // Set the root for the FXML loader
            fxmlLoader.load(); // Load the FXML file
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if FXML loading fails
        }

        // Set the dialog text and speaker's image
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView (speaker's image) appears on the
     * left
     * and the dialog text appears on the right. Used to differentiate between the
     * user's
     * dialog and the system's or other speakers' dialog.
     */
    private void flip() {
        // Reverses the order of nodes (image and text) in the HBox
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT); // Align the components to the left
    }

    /**
     * Factory method to create a DialogBox for the user's dialog.
     * The dialog text appears on the left and the user's image on the right.
     *
     * @param text The dialog text.
     * @param img  The user's image.
     * @return A new DialogBox instance representing the user's dialog.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Factory method to create a DialogBox for another speaker's dialog.
     * The dialog text appears on the right and the speaker's image on the left.
     * This method also calls the flip function to reverse the order of the text and
     * image.
     *
     * @param text The dialog text.
     * @param img  The speaker's image.
     * @return A new DialogBox instance representing the other speaker's dialog.
     */
    public static DialogBox getDialogBox(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip(); // Flip the dialog box to place the image on the left
        return db;
    }
}
