package client.java.controllers;

import client.java.NetworkConnection;
import client.java.Player;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class InGameController {

    // Temporary street names.
    // To be replaced by NOC-List
    private String[] SquareNames = {
            "Go","Old Kent Road","Community Chest","Whitechapel Road","Income Tax", "King Cross", "The Angel Islington", "Chance", "Euston Road", "Pentonville Road", "Jail",
            "Pall Mall","Electric Co","Whitehall","Northumberland Ave","Marylebone Station", "Bow St", "Community Chest","Marlborough St","Vine St",
            "Free Parking","Strand","Chance", "Fleet St","Trafalgar Sq","Fenchurch St Station", "Leicester Sq", "Coventry St","Water Works", "Piccadilly","Go To Jail",
            "Regent St","Oxford St","Community Chest","Bond St","Liverpool St Station","Chance","Park Lane","Super Tax","Mayfair"
    };

    private ObservableList<String> playerList = FXCollections.observableArrayList("player1","player2","player3", "player 4");
    private List<Player> players;


    // Streets
    public HBox top;
    public HBox bottom;
    public VBox left;
    public VBox right;

    private ArrayList<Pane> squares;

    // Networking.
    private final static String IP = "52.48.249.220";
    private final static int PORT = 8080;
    private NetworkConnection connection = new NetworkConnection(IP,PORT, input -> {
        try {
            onUpdateReceived(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    });

    public void initialize() throws Exception{
        drawProperty();
        showLobbyWindow();
        connection.startConnection();
    }

    public void closeGame() {
        connection.gameEnd();
    }

    public void showLobbyWindow() throws IOException {
        VBox lobbyRoot = new VBox();
        Button startGameButton = new Button("Start Game");
        ListView<String> playerListView = new ListView<>(playerList);

        lobbyRoot.getChildren().add(playerListView);
        lobbyRoot.getChildren().add(startGameButton);

        Scene lobbyScene = new Scene(lobbyRoot, 400,600);
        lobbyScene.getStylesheets().add("/client/resources/css/lobby.css");

        Stage lobbyStage = new Stage();
        lobbyStage.initStyle(StageStyle.UNDECORATED);
        lobbyStage.initModality(Modality.APPLICATION_MODAL);
        lobbyStage.setScene(lobbyScene);

        startGameButton.setOnAction(e -> lobbyStage.close());

        lobbyStage.show();
    }

    // called whenever a message/JSON is received form the server.
    public void onUpdateReceived(JSONObject update) throws JSONException {
        Platform.runLater(() -> {
            try {
                System.out.println("Current GameState: " + update.toString());

                int playerTurn = update.getInt("player_turn");
                //String actionInfo = update.getString("action_info");

                JSONArray playerObjects = update.getJSONArray("players");
                List<Player> plyrs = new ArrayList<>();
                for(int i=0;i<playerObjects.length();i++){
                    int balance = playerObjects.getJSONObject(i).getInt("balance");
                    int id = playerObjects.getJSONObject(i).getInt("id");
                    int position = playerObjects.getJSONObject(i).getInt("position");
                    plyrs.add(new Player(balance,id,position));
                }

            } catch (JSONException e) { e.printStackTrace(); }
        });

        // Extract JSON fields
        // Update player positions
        // Print action information
    }

    // Populates the board with property. Plan to refactor a lot.
    public void drawProperty() {

        for (int i = 0; i < 11; i++) {
            Pane squarePane = new Pane();

            squarePane.setStyle("-fx-background-color: #F2F4F4;" + "-fx-border-color: #34495E;");

            squarePane.setMinSize(50, 75);
            squarePane.setPrefSize(60, 100);

            HBox.setHgrow(squarePane, Priority.ALWAYS);

            Label name = new Label(SquareNames[10 - i]);
            name.setPrefWidth(60);
            name.setWrapText(true);
            name.setStyle("-fx-text-fill: #34495E;");

            name.layoutXProperty().bind(squarePane.widthProperty().subtract(name.widthProperty()).divide(2));

            squarePane.getChildren().add(name);
            bottom.getChildren().add(squarePane);
        }

        for (int i = 0; i < 8; i++) {
            Pane squarePane = new Pane();

            squarePane.setStyle("-fx-background-color: #F2F4F4;" + "-fx-border-color: #34495E;");

            squarePane.setMinSize(75, 50);
            squarePane.setPrefSize(100, 60);

            VBox.setVgrow(squarePane, Priority.ALWAYS);

            Label name = new Label(SquareNames[19 - i]);
            name.setRotate(90.0);
            name.setPrefWidth(60);
            name.setWrapText(true);
            name.setStyle("-fx-text-fill: #34495E;");

            name.layoutYProperty().bind(squarePane.heightProperty().subtract(name.heightProperty()).divide(2));


            squarePane.getChildren().add(name);
            left.getChildren().add(squarePane);
        }

        for (int i = 20; i < 31; i++) {
            Pane squarePane = new Pane();

            squarePane.setStyle("-fx-background-color: #F2F4F4;" + "-fx-border-color: #34495E;");

            squarePane.setMinSize(50, 75);
            squarePane.setPrefSize(60, 100);

            HBox.setHgrow(squarePane, Priority.ALWAYS);

            Label name = new Label(SquareNames[i]);
            name.setPrefWidth(60);
            name.setWrapText(true);
            name.setStyle("-fx-text-fill: #34495E;");

            name.layoutXProperty().bind(squarePane.widthProperty().subtract(name.widthProperty()).divide(2));

            squarePane.getChildren().add(name);
            top.getChildren().add(squarePane);
        }

        for (int i = 31; i < 40; i++) {
            Pane squarePane = new Pane();

            squarePane.setStyle("-fx-background-color: #F2F4F4;" + "-fx-border-color: #34495E;");

            squarePane.setMinSize(75, 50);
            squarePane.setPrefSize(100, 60);

            VBox.setVgrow(squarePane, Priority.ALWAYS);

            Label name = new Label(SquareNames[i]);
            name.setRotate(90.0);
            name.setPrefWidth(60);
            name.setWrapText(true);
            name.setStyle("-fx-text-fill: #34495E;");

            name.layoutYProperty().bind(squarePane.heightProperty().subtract(name.heightProperty()).divide(2));


            squarePane.getChildren().add(name);
            right.getChildren().add(squarePane);
        }
    }




    // Draws players in their current positions.
    public void drawPlayers() {

    }
}


