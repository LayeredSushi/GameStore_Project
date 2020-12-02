package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long purchaseId;

    @PastOrPresent
    private LocalDate date;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "gameId")
    private Game game;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "customerId")
    private Customer customer;

    public Purchase() {
    }


    public Purchase(@PastOrPresent LocalDate date, Game game, Customer customer) {
        this.date = date;
        setGame(game);
        setCustomer(customer);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Game getGame() {
        return game;
    }

    private void setGame(Game game) {
        if(game == null)
        {
            throw new IllegalArgumentException("Game must not be null");
        }

        this.game = game;
        game.addPurchase(this);
    }

    private void removeGame()
    {
        if(game != null)
        {
            game.removePurchase(this);
            game = null;
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    private void setCustomer(Customer customer) {
        if(customer == null)
        {
            throw new IllegalArgumentException("Customer must not be null");
        }

        this.customer = customer;
        customer.addPurchase(this);
    }

    private void removeSale()
    {
        if(customer != null)
        {
            customer.removePurchase(this);
            customer = null;
        }
    }

    public void removeLinks()
    {
        removeSale();
        removeGame();
    }

}
