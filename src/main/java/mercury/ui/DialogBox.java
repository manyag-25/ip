package mercury.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    private Label text;
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the given text and image.
     *
     * @param s The text to display.
     * @param i The image to display.
     */
    public DialogBox(String s, Image i) {
        text = new Label(s);
        displayPicture = new ImageView(i);

        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);

        text.getStyleClass().add("dialog-text");
        displayPicture.getStyleClass().add("display-picture");
        this.getStyleClass().add("dialog-box");

        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Creates a dialog box for user messages.
     *
     * @param s The user's message.
     * @param i The user's image.
     * @return A DialogBox representing the user's message.
     */
    public static DialogBox getUserDialog(String s, Image i) {
        var db = new DialogBox(s, i);
        db.getStyleClass().add("user-dialog");
        return db;
}

    /**
     * Creates a dialog box for Mercury's responses.
     *
     * @param s Mercury's response.
     * @param i Mercury's image.
     * @return A DialogBox representing Mercury's response.
     */
    public static DialogBox getMercuryDialog(String s, Image i) {
        var db = new DialogBox(s, i);
        db.flip();
        db.getStyleClass().add("mercury-dialog");
        return db;
    }
}
