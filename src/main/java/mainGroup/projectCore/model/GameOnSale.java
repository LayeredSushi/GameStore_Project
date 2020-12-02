package mainGroup.projectCore.model;


import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
public class GameOnSale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long gameOnSaleId;

    @PastOrPresent
    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "gameId")
    private Game game;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "saleId")
    private Sale sale;

    public GameOnSale() {
    }


    public GameOnSale(@PastOrPresent LocalDate startDate, LocalDate endDate, Game game, Sale sale) {
        setStartDate(startDate);
        setEndDate(endDate);
        setGame(game);
        setSale(sale);
    }

    public long getGameOnSaleId() {
        return gameOnSaleId;
    }

    public void setGameOnSaleId(long gameOnSaleId) {
        this.gameOnSaleId = gameOnSaleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
        game.addGameOnSale(this);
    }

    private void removeGame()
    {
        if(game != null)
        {
            game.removeGameOnSale(this);
            game = null;
        }
    }

    public Sale getSale() {
        return sale;
    }

    private void setSale(Sale sale) {
        if(sale == null)
        {
            throw new IllegalArgumentException("Shop must not be null");
        }

        this.sale = sale;
        sale.addGameOnSale(this);
    }

    private void removeSale()
    {
        if(sale != null)
        {
            sale.removeGameOnSale(this);
            sale = null;
        }
    }

    public void removeLinks()
    {
        removeSale();
        removeGame();
    }
}
