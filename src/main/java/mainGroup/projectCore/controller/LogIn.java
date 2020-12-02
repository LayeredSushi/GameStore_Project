package mainGroup.projectCore.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainGroup.projectCore.GameFXApplication;
import mainGroup.projectCore.model.Customer;
import mainGroup.projectCore.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class LogIn {


    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField usernameField;

    @FXML
    public Label invalidInputLabel;

    @Autowired
    private CustomerRepo customerRepo;

    private Stage stage;
    private List<Stage> stages = new ArrayList<>();
    private OwnedGamesList ownedGamesListController;

    @FXML
    private void checkLogIn()
    {
        Iterable<Customer> allCustomers = customerRepo.findAll();

        AtomicBoolean customerPresent = new AtomicBoolean(false);
        AtomicReference<Customer> loggedInCustomer = new AtomicReference<>();
        allCustomers.forEach(customer ->{
            if(customer.getUsername().equals(usernameField.getText()) && customer.getPassword().equals(passwordField.getText()))
            {
                customerPresent.set(true);
                loggedInCustomer.set(customer);
            }
        });

        if(customerPresent.get())
        {
            if(stages.isEmpty())
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ownedGamesList.fxml"));
                fxmlLoader.setControllerFactory(GameFXApplication.applicationContext::getBean);

                Stage ownedGamesListStage = new Stage();
                Parent root = null;
                try {
                    root = fxmlLoader.load();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ownedGamesListStage.setTitle("Game store");
                ownedGamesListStage.setScene(new Scene(root));
                ownedGamesListStage.show();

                ownedGamesListController = fxmlLoader.getController();

                stages.add(stage);
                stages.add(ownedGamesListStage);

            }

            ownedGamesListController.receiveInfo(stages, loggedInCustomer.get());
            stages.get(1).show();
            resetInput();
            stages.get(0).hide();
        }
        else
        {
            invalidInputLabel.setText("Either wrong username or password");
        }
    }

    public void passStage(Stage stage){
        this.stage=stage;
    }

    private void resetInput()
    {
        usernameField.clear();
        passwordField.clear();
    }
}
