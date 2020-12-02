package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long saleId;

    @Min(10)
    private int value;

    @OneToMany(mappedBy = "sale",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    private Set<GameOnSale> gameOnSales = new HashSet<>();

    public Sale() {
    }

    public Sale(@Min(10) int value) {
        setValue(value);
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Set<GameOnSale> getGameOnSales() {
        return new HashSet<>(gameOnSales);
    }

    private void setGameOnSales(Set<GameOnSale> gameOnSales) {
        this.gameOnSales = gameOnSales;
    }

    public void addGameOnSale(GameOnSale gameOnSale)
    {
        if(gameOnSale == null)
        {
            throw new IllegalArgumentException("Product must not be null");
        }
        if(!gameOnSales.contains(gameOnSale))
        {
            gameOnSales.add(gameOnSale);
        }
    }

    public void removeGameOnSale(GameOnSale gameOnSale)
    {
        if(gameOnSale == null)
        {
            throw new IllegalArgumentException("Game on sale must not be null");
        }

        if(gameOnSales.contains(gameOnSale))
        {
            if(gameOnSales.size() == 1)
            {
                throw new IllegalArgumentException("Game on sale list should have at least 1 element");
            }
            gameOnSales.remove(gameOnSale);
            gameOnSale.removeLinks();
        }
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", value=" + value +
                ", gameOnSales=" + gameOnSales +
                '}';
    }
}
