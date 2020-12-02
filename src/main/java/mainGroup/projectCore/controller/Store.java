package mainGroup.projectCore.controller;

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
import mainGroup.projectCore.model.Purchase;
import mainGroup.projectCore.repository.CustomerRepo;
import mainGroup.projectCore.repository.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class Store {

    private Customer customer;

    @FXML
    private VBox gamesList;

    @FXML
    private Label cartCountLabel;

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private CustomerRepo customerRepo;

    private Set<Game> cartGames = new HashSet<>();
    private Set<Game> games = new HashSet<>();

    private List<Stage> stages;
    private OwnedGamesList ownedGamesListController;
    private Cart cartController;

    public void receiveInfo(List<Stage> stages, OwnedGamesList ownedGamesListController , Customer customer)
    {
        this.customer = customer;
        this.stages = stages;
        this.ownedGamesListController = ownedGamesListController;
        showGames();
    }

    public void passInfoBack()
    {
        ownedGamesListController.receiveBackInfo(stages);
    }

    public void receiveBackInfo(List<Stage> stages)
    {
        this.stages = stages;
    }

    public void resetCart()
    {
        cartGames.clear();
        games.clear();
    }

    public void showGames()
    {
        gamesList.getChildren().clear();

        if(games.isEmpty())
        {
            getGames();
        }

        games.forEach(game -> {
            HBox gameLine = new HBox();
            Label gameName = new Label(game.getName());
            Label gameDescription = new Label(game.getDescription());
            Label gamePrice = new Label(String.valueOf(game.getPrice()) + "zl");
            Button addButton = new Button("Add");

            if(cartGames.contains(game))
            {
                addButton.setText("Remove");
            }

            addButton.setOnAction(actionEvent ->  {
                if(!cartGames.contains(game))
                {
                    cartGames.add(game);
                    addButton.setText("Remove");
                }
                else
                {
                    cartGames.remove(game);
                    addButton.setText("Add");
                }
                cartCountLabel.setText("Games in cart: "+ cartGames.size());
            });

            gameLine.getChildren().addAll(gameName, gameDescription, gamePrice, addButton);
            gameLine.setSpacing(15);

            gamesList.getChildren().add(gameLine);
            gamesList.setSpacing(10);
        });
    }

    public void showGames(Set<Game> cartGames)
    {
        this.cartGames = cartGames;
        cartCountLabel.setText("Games in cart: "+ cartGames.size());
        showGames();
    }

    public void getGames()
    {
        Set<Purchase> purchases = new HashSet<>();
        customer.getPurchases().forEach(purchase -> {
            purchases.add(customerRepo.ownedGame(purchase.getPurchaseId()).get());
        });

        games = gameRepo.findAll();

        Set<Game> filteredGames;
        for(Purchase purchase: purchases)
        {
            filteredGames = games.stream().filter(game -> game.getGameId() != purchase.getGame().getGameId()).collect(Collectors.toSet());
            games = filteredGames;
        }
    }


    @FXML
    public void changeToLibrary() {

        passInfoBack();
        stages.get(2).hide();
        stages.get(1).show();
    }

    @FXML
    public void changeToCart()
    {
        if(stages.size() <= 3)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/cart.fxml"));
            fxmlLoader.setControllerFactory(GameFXApplication.applicationContext::getBean);

            Stage cartStage = new Stage();
            Parent root = null;
            try {
                root = fxmlLoader.load();

            } catch (IOException e) {
                e.printStackTrace();
            }
            cartStage.setTitle("Game store");
            cartStage.setScene(new Scene(root));
            stages.add(cartStage);

            cartController = fxmlLoader.getController();
        }

        stages.get(3).show();
        cartController.receiveInfo(stages, this, ownedGamesListController,customer, cartGames);
        stages.get(2).hide();

    }
}
