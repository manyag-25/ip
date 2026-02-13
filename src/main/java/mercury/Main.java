package mercury;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import mercury.ui.DialogBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

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
        // Setup UI components
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.setTitle("Mercury");
        stage.show();

        // Setup layout
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        // Setup event handlers
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        // Scroll down to the end automatically
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        // Display welcome message
        String welcome = mercury.getWelcomeMessage();
        dialogContainer.getChildren().add(
            DialogBox.getMercuryDialog(welcome, mercuryImage)
        );
    }

    /**
     * Handles user input by processing the command and displaying the response.
     */
    private void handleUserInput() {
        String input = userInput.getText();
        String response = mercury.getResponse(input);

        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getMercuryDialog(response, mercuryImage)
        );

        userInput.clear();

        // Exit if bye command
        if (input.trim().equals("bye")) {
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

        // Draw a simple filled circle
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
