package mainGroup.projectCore;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainGroup.projectCore.GameFXApplication.StageReadyEvent;
import mainGroup.projectCore.controller.LogIn;
import mainGroup.projectCore.model.*;
import mainGroup.projectCore.repository.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private LanguageSpecializationRepo languageSpecializationRepo;

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private PurchaseRepo purchaseRepo;

    @Autowired
    private GameUpdateRepo gameUpdateRepo;

    @Autowired
    private LocalizationRepo localizationRepo;

    @Autowired
    private SaleRepo saleRepo;

    @Autowired
    private GameOnSaleRepo gameOnSaleRepo;

    @Autowired
    private PublisherRepo publisherRepo;

    @Autowired
    private ContractRepo contractRepo;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {

        initializeDb();

        /*Iterable<Employee> a = employeeRepo.findAll();
        System.out.println(a);
        Employee emp = employeeRepo.findById(1l).get();
        System.out.println(emp);

        Customer cust = customerRepo.findById(5l).get();
        System.out.println(cust);*/

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/logIn.fxml"));
        fxmlLoader.setControllerFactory(GameFXApplication.applicationContext::getBean);

        Stage stage = event.getStage();

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Game store");
        stage.setScene(new Scene(root));
        stage.show();

        LogIn logInController = fxmlLoader.getController();
        logInController.passStage(stage);
    }

    private void initializeDb()
    {
        Customer cust1 = null;
        Customer cust2 = null;
        if(!customerRepo.findById(1l).isPresent())
        {
            cust1 = new Customer("Adam","123456", LocalDate.of(1999,1,1),"01-111, address1, Warsaw", "user1","123456789" ,Customer.Region.NA);
            cust2 = new Customer("Smith","54231", LocalDate.of(1980,2,2),"01-222, address2, Poznan","user2","123456789" , Customer.Region.CIS);
            customerRepo.save(cust1);
            customerRepo.save(cust2);

            Employee emp1 = new Employee("Bob","phone1", LocalDate.of(1995,5,5),200, Employee.EmployeeRole.CustomerSupport);
            Employee emp2 = new Employee("Sara","phone2", LocalDate.of(1993,3,3),200, Employee.EmployeeRole.Manager);
            employeeRepo.save(emp1);
            employeeRepo.save(emp2);

            LanguageSpecialization.createLanguageSpecialization(emp1, "English","B2");
            LanguageSpecialization.createLanguageSpecialization(emp1, "German","B1");
            for(LanguageSpecialization ls: emp1.getSpecializations())
                languageSpecializationRepo.save(ls);




            Game game1 = new Game("Team fortress 2","War of 2 mercenary factions for gravel",19.99f,LocalDate.of(2007,10,10),"FPS");
            Game game2 = new Game("Half life", "Story driven game",33.33f, LocalDate.of(1998,1,1),"FPS");
            Game game3 = new Game("Half life 2", "Story driven game",33.33f, LocalDate.of(1998,1,1),"FPS");
            Game game4 = new Game("Boneworks", "Revolutionary VR experience",120f, LocalDate.of(1998,1,1),"FPS");
            Game game5 = new Game("Hearts of iron 3", "WW2 simulator",35.99f, LocalDate.of(1998,1,1),"GS");
            Game game6 = new Game("Cossacks: Back to war", "Grand scale wars set in 17th century",25f, LocalDate.of(1998,1,1),"RTS");
            Game game7 = new Game("Planetside 2", "Massive combined arms multiplayer set in far future",100f, LocalDate.of(1998,1,1),"MMO FPS");
            gameRepo.save(game1);
            gameRepo.save(game2);
            gameRepo.save(game3);
            gameRepo.save(game4);
            gameRepo.save(game5);
            gameRepo.save(game6);
            gameRepo.save(game7);

            Purchase purchase1 = new Purchase(LocalDate.of(2010,1,1),game1,cust1);
            Purchase purchase2 = new Purchase(LocalDate.of(2011,1,1),game2,cust1);
            Purchase purchase3 = new Purchase(LocalDate.of(2015, 1, 1),game3,cust2);
            purchaseRepo.save(purchase1);
            purchaseRepo.save(purchase2);
            purchaseRepo.save(purchase3);

            GameUpdate.createGameUpdate(game1,"0.1.1", LocalDate.of(2008,1,1),"Added something");
            GameUpdate.createGameUpdate(game1,"0.2.1", LocalDate.of(2008,1,1),"Added something more");
            for(GameUpdate gu: game1.getUpdates())
                gameUpdateRepo.save(gu);

            Localization.createLocalization(game1, "English", Localization.TranslationType.Manual, 5);
            Localization.createLocalization(game1, "German", Localization.TranslationType.Manual, 3);
            for(Localization localization: game1.getLocalizations())
                localizationRepo.save(localization);

            Sale sale1 = new Sale(15);
            Sale sale2 = new Sale(30);
            saleRepo.save(sale1);
            saleRepo.save(sale2);

            GameOnSale gs1 = new GameOnSale(LocalDate.of(2019,1,1),LocalDate.of(2019,2,1),game1,sale1);
            GameOnSale gs2 = new GameOnSale(LocalDate.of(2020,3,1),LocalDate.of(2020,4,1),game1,sale2);
            gameOnSaleRepo.save(gs1);
            gameOnSaleRepo.save(gs2);


            Publisher publisher1 = new Publisher("Valve","123456","Address1");
            Publisher publisher2 = new Publisher("EA","123456","Address2");
            publisherRepo.save(publisher1);
            publisherRepo.save(publisher2);

            HashSet<String> releasedPlatforms = new HashSet<>();
            releasedPlatforms.add("Steam");
            Contract contract1 = new Contract(LocalDate.of(2007,10,10),releasedPlatforms,game1,publisher1);
            contractRepo.save(contract1);
        }



    }

}
