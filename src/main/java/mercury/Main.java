package mercury;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import mercury.ui.DialogBox;

/**
 * Main JavaFX application class for Mercury chatbot.
 */
public class Main extends Application {
    private Mercury mercury = new Mercury();
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image userImage = createPlaceholderImage(Color.LIGHTBLUE);
    private Image mercuryImage = createPlaceholderImage(Color.LIGHTGREEN);

    @Override
    public void start(Stage stage) {
        dialogContainer = new VBox(12);
        dialogContainer.setPadding(new Insets(8));
        dialogContainer.getStyleClass().add("dialog-container");

        scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefViewportHeight(520);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background: transparent;");

        userInput = new TextField();
        userInput.setPromptText("Try \"help\" for a list of commands.");
        userInput.getStyleClass().add("input-field");

        sendButton = new Button("Send");
        sendButton.getStyleClass().add("send-button");

        HBox inputBox = new HBox(8, userInput, sendButton);
        inputBox.getStyleClass().add("input-container");
        HBox.setHgrow(userInput, Priority.ALWAYS);

        Label hintLabel = new Label("Commands: help, todo, deadline, event, list, mark, unmark, delete, find, cheer.");
        hintLabel.getStyleClass().add("command-hint");
        hintLabel.setWrapText(true);
        hintLabel.setTextAlignment(TextAlignment.LEFT);

        VBox bottomBar = new VBox(6, inputBox, hintLabel);
        bottomBar.setPadding(new Insets(4, 4, 8, 4));
        bottomBar.getStyleClass().add("center-pane");

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setBottom(bottomBar);
        root.getStyleClass().add("root-pane");

        scene = new Scene(root, 420, 620);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Mercury");
        stage.setMinHeight(620.0);
        stage.setMinWidth(420.0);
        stage.show();

        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));

        sendButton.setOnMouseClicked(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        String welcome = mercury.getWelcomeMessage();
        dialogContainer.getChildren().add(DialogBox.getMercuryDialog(welcome, mercuryImage));
    }

    /**
     * Handles user input by processing the command and displaying the response.
     */
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        String response = mercury.getResponse(input);
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getMercuryDialog(response, mercuryImage)
        );

        userInput.clear();

        if ("bye".equals(input.trim())) {
            Platform.exit();
        }
    }

    /**
     * Creates a simple placeholder image with the specified color.
     *
     * @param color The color of the placeholder circle.
     * @return A simple circular image.
     */
    private Image createPlaceholderImage(Color color) {
        int size = 100;
        WritableImage image = new WritableImage(size, size);
        PixelWriter writer = image.getPixelWriter();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                double dx = x - size / 2.0;
                double dy = y - size / 2.0;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < size / 2.0) {
                    writer.setColor(x, y, color);
                } else {
                    writer.setColor(x, y, Color.TRANSPARENT);
                }
            }
        }

        return image;
    }
}
