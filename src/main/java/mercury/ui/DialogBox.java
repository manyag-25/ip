package mercury.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Represents a dialog box for displaying chat messages.
 * User messages are right-aligned; Mercury messages are left-aligned;
 * error messages are left-aligned with distinct error styling.
 */
public class DialogBox extends HBox {
    private Label text;

    private DialogBox(String s) {
        text = new Label(s);
        text.setWrapText(true);
        text.setMaxWidth(Double.MAX_VALUE);
        text.getStyleClass().add("dialog-text");
        this.getStyleClass().add("dialog-box");
        HBox.setHgrow(text, Priority.ALWAYS);
        this.getChildren().add(text);
    }

    /**
     * Creates a dialog box for user messages (right-aligned).
     *
     * @param s The user's message text.
     * @return A DialogBox styled for user input.
     */
    public static DialogBox getUserDialog(String s) {
        var db = new DialogBox(s);
        db.setAlignment(Pos.CENTER_RIGHT);
        db.getStyleClass().add("user-dialog");
        return db;
    }

    /**
     * Creates a dialog box for Mercury's responses (left-aligned).
     *
     * @param s Mercury's response text.
     * @return A DialogBox styled for Mercury output.
     */
    public static DialogBox getMercuryDialog(String s) {
        var db = new DialogBox(s);
        db.setAlignment(Pos.CENTER_LEFT);
        db.getStyleClass().add("mercury-dialog");
        return db;
    }

    /**
     * Creates a dialog box for error responses (left-aligned, highlighted in red).
     *
     * @param s The error message text.
     * @return A DialogBox styled to highlight errors.
     */
    public static DialogBox getErrorDialog(String s) {
        var db = new DialogBox(s);
        db.setAlignment(Pos.CENTER_LEFT);
        db.getStyleClass().add("error-dialog");
        return db;
    }
}
