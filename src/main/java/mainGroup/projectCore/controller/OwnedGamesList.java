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
import mainGroup.projectCore.model.Purchase;
import mainGroup.projectCore.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class OwnedGamesList {

    @FXML
    public VBox gamesList;


    @Autowired
    private CustomerRepo customerRepo;

    private Customer customer;
    private List<Stage> stages;
    private boolean afterSpecialActions;
    private Store storeController;

    public void initialize()
    {
        afterSpecialActions = false;
    }

    public void receiveInfo(List<Stage> stages, Customer customer)
    {
        this.customer = customer;
        this.stages = stages;
        getGamesList();
    }

    public void receiveBackInfo(List<Stage> stages)
    {
        this.stages = stages;
    }

    public void getGamesList()
    {
        gamesList.getChildren().clear();

        Set<Purchase> purchases = new HashSet<>();
        customer.getPurchases().forEach(purchase -> {
            purchases.add(customerRepo.ownedGame(purchase.getPurchaseId()).get());
        });

        purchases.forEach(purchase -> {
            HBox gameLine = new HBox();
            Label gameName = new Label(purchase.getGame().getName());
            Label gameDescription = new Label(purchase.getGame().getDescription());
            Button downloadButton = new Button("Download");

            gameLine.getChildren().addAll(gameName, gameDescription, downloadButton);
            gameLine.setSpacing(15);

            gamesList.getChildren().add(gameLine);
            gamesList.setSpacing(10);
        });
    }

    @FXML
    public void changeToStore()
    {
        if(stages.size() <= 2)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/store.fxml"));
            fxmlLoader.setControllerFactory(GameFXApplication.applicationContext::getBean);

            Stage storeStage = new Stage();
            Parent root = null;
            try {
                root = fxmlLoader.load();

            } catch (IOException e) {
                e.printStackTrace();
            }

            storeStage.setTitle("Game store");
            storeStage.setScene(new Scene(root));
            stages.add(storeStage);

            storeController = fxmlLoader.getController();
            storeController.receiveInfo(stages, this, customer);
        }

        if(afterSpecialActions)
        {
            storeController.resetCart();
            storeController.receiveInfo(stages, this, customer);
            afterSpecialActions = false;
        }
        stages.get(2).show();
        stages.get(1).hide();
    }

    public boolean isAfterSpecialActions() {
        return afterSpecialActions;
    }

    public void setAfterSpecialActions(boolean afterSpecialActions) {
        this.afterSpecialActions = afterSpecialActions;
    }

    public void changeToLog(ActionEvent actionEvent) {
        afterSpecialActions = true;

        stages.get(0).show();
        stages.get(1).hide();
    }
}
