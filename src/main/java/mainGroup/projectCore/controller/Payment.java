package mainGroup.projectCore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainGroup.projectCore.GameFXApplication;
import mainGroup.projectCore.model.Customer;
import mainGroup.projectCore.model.Game;
import mainGroup.projectCore.model.misc.PaymentInfo;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
public class Payment {

    @FXML
    public TextField billingField, nameField, surnameField, creditField;
    @FXML
    public Label errorLabel;
    @FXML
    public ComboBox<Integer> yearCombo, monthCombo;

    private List<Stage> stages;
    private Cart cartController;
    private OwnedGamesList ownedGamesListController;
    private Customer customer;
    private float sum;
    private Set<Game> cartGames;

    private Confirmation confirmationController;

    public void receiveInfo(List<Stage> stages, Cart cartController,OwnedGamesList ownedGamesListController, Customer customer, float sum, Set<Game> cartGames)
    {
        this.stages = stages;
        this.cartController = cartController;
        this.ownedGamesListController = ownedGamesListController;
        this.customer = customer;
        this.sum = sum;
        this.cartGames = cartGames;
    }

    public void receiveBackInfo(List<Stage> stages)
    {
        this.stages = stages;
    }

    public void initializeUI()
    {
        int currentYear = LocalDate.now().getYear();
        for(int i = 0; i < 12; i ++)
        {
            yearCombo.getItems().add(currentYear + i);
            monthCombo.getItems().add(i + 1);
        }
        yearCombo.getSelectionModel().select(0);
        monthCombo.getSelectionModel().select(0);

        billingField.setText(customer.getBillingAddress());

        creditField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            creditField.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    public void resetInput()
    {
        creditField.clear();
        nameField.clear();
        surnameField.clear();
        billingField.setText(customer.getBillingAddress());

        yearCombo.getSelectionModel().select(0);
        monthCombo.getSelectionModel().select(0);
    }

    public void changeToCart(ActionEvent actionEvent) {

        resetInput();
        stages.get(3).show();
        cartController.receiveBackInfo(stages);
        stages.get(4).hide();
    }

    public void changeToConfirmation(ActionEvent actionEvent) {

        if(checkInput())
        {
            errorLabel.setText("");

            if(stages.size() <= 5)
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/confirmation.fxml"));
                fxmlLoader.setControllerFactory(GameFXApplication.applicationContext::getBean);

                Stage confirmationStage = new Stage();
                Parent root = null;
                try {
                    root = fxmlLoader.load();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                confirmationStage.setTitle("Game store");
                confirmationStage.setScene(new Scene(root));
                stages.add(confirmationStage);

                confirmationController = fxmlLoader.getController();
            }

            PaymentInfo paymentInfo = new PaymentInfo(nameField.getText(),
                    surnameField.getText(),
                    creditField.getText(),
                    billingField.getText(),
                    yearCombo.getValue(),
                    monthCombo.getValue(),
                    cartGames);

            stages.get(5).show();
            confirmationController.receiveInfo(stages, this, ownedGamesListController, customer, sum, paymentInfo);
            confirmationController.initializeUI();
            stages.get(4).hide();
        }
    }

    private boolean checkInput()
    {
        if(nameField.getText().isEmpty() ||
                surnameField.getText().isEmpty() ||
                creditField.getText().isEmpty() ||
                billingField.getText().isEmpty())
        {
            errorLabel.setText("All fields must be filled");
            return false;
        }
        if(creditField.getText().length() < 16)
        {
            errorLabel.setText("Invalid input for credit");
            return false;
        }

        return true;
    }

}
