package mainGroup.projectCore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainGroup.projectCore.GameFXApplication;
import mainGroup.projectCore.model.Customer;
import mainGroup.projectCore.model.Game;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
public class Cart {

    @FXML
    public VBox gamesList;
    @FXML
    public Label sumLabel;
    @FXML
    public Button buyButton;

    private List<Stage> stages;
    private Customer customer;
    private Set<Game> cartGames;
    private float sum;
    private Store storeController;
    private OwnedGamesList ownedGamesListController;

    private Payment paymentController;

    public void receiveInfo(List<Stage> stages, Store storeController, OwnedGamesList ownedGamesListController,Customer customer, Set<Game> cartGames)
    {
        this.stages = stages;
        this.customer = customer;
        this.cartGames = cartGames;
        this.storeController = storeController;
        this.ownedGamesListController = ownedGamesListController;
        showGamesList();
    }

    public void receiveBackInfo(List<Stage> stages)
    {
        this.stages = stages;
    }

    private void showGamesList() {

        gamesList.getChildren().clear();

        cartGames.forEach(game -> {
            HBox gameLine = new HBox();
            Label gameName = new Label(game.getName());
            Label gamePrice = new Label(String.valueOf(game.getPrice()) + "zl");
            Button removeButton = new Button("Remove");
            removeButton.setOnAction(actionEvent ->  {
                sum -= game.getPrice();
                cartGames.remove(game);
                showGamesList();
            });
            gameLine.getChildren().addAll(gameName, gamePrice, removeButton);
            gameLine.setSpacing(15);

            gamesList.getChildren().add(gameLine);
            gamesList.setSpacing(10);
        });

        sum = 0;
        cartGames.forEach(game -> {
            sum += game.getPrice();
        }
        );
        sumLabel.setText(String.valueOf(sum) + "zl");

        if(cartGames.isEmpty())
        {
            buyButton.setDisable(true);
        }
        else
        {
            buyButton.setDisable(false);
        }
    }

    @FXML
    public void changeToStore(ActionEvent actionEvent) {
        stages.get(2).show();
        storeController.showGames(cartGames);
        storeController.receiveBackInfo(stages);
        stages.get(3).hide();
    }

    @FXML
    public void changeToPayment(ActionEvent actionEvent) {

        if(stages.size() <= 4)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/payment.fxml"));
            fxmlLoader.setControllerFactory(GameFXApplication.applicationContext::getBean);

            Stage paymentStage = new Stage();
            Parent root = null;
            try {
                root = fxmlLoader.load();

            } catch (IOException e) {
                e.printStackTrace();
            }
            paymentStage.setTitle("Game store");
            paymentStage.setScene(new Scene(root));
            stages.add(paymentStage);

            paymentController = fxmlLoader.getController();
        }

        stages.get(4).show();
        paymentController.receiveInfo(stages, this, ownedGamesListController, customer, sum, cartGames);
        paymentController.initializeUI();
        stages.get(3).hide();
    }
}
