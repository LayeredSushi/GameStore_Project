package mainGroup.projectCore.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainGroup.projectCore.model.Customer;
import mainGroup.projectCore.model.Game;
import mainGroup.projectCore.model.Purchase;
import mainGroup.projectCore.model.misc.PaymentInfo;
import mainGroup.projectCore.repository.CustomerRepo;
import mainGroup.projectCore.repository.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class Confirmation {

    @FXML
    TextField nameField, creditField, monthYearField, billingField, sumField;

    @Autowired
    private PurchaseRepo purchaseRepo;

    @Autowired
    private CustomerRepo customerRepo;

    private List<Stage> stages;
    private Payment paymentController;
    private Customer customer;
    private float sum;
    private  PaymentInfo paymentInfo;
    private OwnedGamesList ownedGamesListController;

    public void receiveInfo(List<Stage> stages, Payment paymentController,OwnedGamesList ownedGamesListController, Customer customer, float sum, PaymentInfo paymentInfo) {
        this.stages = stages;
        this.paymentController = paymentController;
        this.ownedGamesListController = ownedGamesListController;
        this.customer = customer;
        this.sum = sum;
        this.paymentInfo = paymentInfo;
    }

    public void initializeUI() {
        nameField.setText(paymentInfo.getName() + " " + paymentInfo.getSurname());
        creditField.setText("**** " + paymentInfo.getCredit().substring(paymentInfo.getCredit().length() - 4));
        monthYearField.setText(paymentInfo.getMonth() + " " + paymentInfo.getYear());
        billingField.setText(paymentInfo.getBillingAddress());
        sumField.setText(String.valueOf(sum) + "zl");
    }

    @FXML
    public void changeToPayment(ActionEvent actionEvent) {
        stages.get(4).show();
        paymentController.receiveBackInfo(stages);
        stages.get(5).hide();
    }

    @FXML
    public void changeToOwnedGames()
    {
        modifyBillingAddress();
        makePurchase();

        stages.get(1).show();
        stages.get(5).hide();
        List<Stage> newStages = stages.subList(0,2);
        ownedGamesListController.setAfterSpecialActions(true);
        ownedGamesListController.receiveBackInfo(newStages);
        ownedGamesListController.getGamesList();
        paymentController.resetInput();
    }

    private void makePurchase()
    {
        Set<Purchase> purchases = new HashSet<>();
        for(Game game: paymentInfo.getCartGames())
        {
            purchases.add(new Purchase(LocalDate.now(), game, customer));
        }

        //purchases.forEach(purchase -> purchaseRepo.save(purchase));
        purchaseRepo.saveAll(purchases);
    }

    private void modifyBillingAddress()
    {
        customerRepo.updateBillingField(paymentInfo.getBillingAddress(), customer.getPersonId());
    }
}
