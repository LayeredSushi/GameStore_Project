package mainGroup.projectCore.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long gameId;

    @NotBlank(message = "Game name must not be empty or null")
    private String name;

    @NotBlank(message = "Game description must not be null")
    private String description;

    private float price;

    @PastOrPresent(message = "Invalid release date")
    private LocalDate releaseDate;

    @NotBlank(message = "Game genre must be specified")
    private String genre;

    @OneToMany(mappedBy = "game",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    private Set<GameUpdate> updates = new HashSet<>();

    private static Set<GameUpdate> allUpdates = new HashSet<>();

    @OneToMany(mappedBy = "game",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    private Set<Localization> localizations = new HashSet<>();

    private static Set<Localization> allLocalizations = new HashSet<>();

    @OneToMany(mappedBy = "game",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    private Set<Contract> contracts = new HashSet<>();

    @OneToMany(mappedBy = "game",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    private Set<GameOnSale> gameOnSales = new HashSet<>();

    @OneToMany(mappedBy = "game",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    private Set<Purchase> purchases = new HashSet<>();



    public Game() {
    }

    public Game(@NotBlank(message = "Game name must not be empty or null") String name, @NotBlank(message = "Game description must not be null") String description, float price, @PastOrPresent(message = "Invalid release date") LocalDate releaseDate, @NotBlank(message = "Game genre must be specified") String genre) {
        setName(name);
        setDescription(description);
        setPrice(price);
        setReleaseDate(releaseDate);
        setGenre(genre);
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<GameUpdate> getUpdates() {
        return new HashSet<>(updates);
    }

    private void setUpdates(Set<GameUpdate> updates) {
        this.updates = updates;
    }

    public static Set<GameUpdate> getAllUpdates() {
        return new HashSet<>(allUpdates);
    }

    private static void setAllUpdates(Set<GameUpdate> allUpdates) {
        Game.allUpdates = allUpdates;
    }

    public Set<Localization> getLocalizations() {
        return new HashSet<>(localizations);
    }

    private void setLocalizations(Set<Localization> localizations) {
        this.localizations = localizations;
    }

    public static Set<Localization> getAllLocalizations() {
        return new HashSet<>(allLocalizations);
    }

    private static void setAllLocalizations(Set<Localization> allLocalizations) {
        Game.allLocalizations = allLocalizations;
    }

    public Set<Contract> getContracts() {
        return new HashSet<>(contracts);
    }

    private void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public Set<GameOnSale> getGameOnSales() {
        return new HashSet<>(gameOnSales);
    }

    private void setGameOnSales(Set<GameOnSale> gameOnSales) {
        this.gameOnSales = gameOnSales;
    }

    public Set<Purchase> getPurchases() {
        return new HashSet<>(purchases);
    }

    private void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addUpdate(GameUpdate update)
    {
        if(update == null)
        {
            throw new IllegalArgumentException("Update must not be null");
        }
        if(!updates.contains(update))
        {
            if(updates.contains(update))
            {
                throw new IllegalArgumentException("Update is already part of something");
            }

            updates.add(update);
            allUpdates.add(update);
        }
    }

    public void removeUpdate(GameUpdate update)
    {
        if(update == null)
        {
            throw new IllegalArgumentException("Update must not be null");
        }
        if(updates.contains(update))
        {
            updates.remove(update);
            allUpdates.remove(update);
            update.removeGame();
        }
    }

    public void addLocalization(Localization localization)
    {
        if(localization == null)
        {
            throw new IllegalArgumentException("Localization must not be null");
        }
        if(!localizations.contains(localization))
        {
            if(localizations.contains(localization))
            {
                throw new IllegalArgumentException("Localization is already part of something");
            }

            localizations.add(localization);
            allLocalizations.add(localization);
        }
    }

    public void removeLocalization(Localization localization)
    {
        if(localizations.contains(localization))
        {
            localizations.remove(localization);
            localization.removeGame();
        }

        if(localization == null)
        {
            throw new IllegalArgumentException("Localization must not be null");
        }
        if(localizations.contains(localization))
        {
            localizations.remove(localization);
            allLocalizations.remove(localization);
            localization.removeGame();
        }
    }

    public void addContract(Contract contract)
    {
        if(contract == null)
        {
            throw new IllegalArgumentException("Product must not be null");
        }
        if(!contracts.contains(contract))
        {
            contracts.add(contract);
        }
    }

    public void removeContract(Contract contract)
    {
        if(contract == null)
        {
            throw new IllegalArgumentException("Contract must not be null");
        }

        if(contracts.contains(contract))
        {
            if(contracts.size() == 1)
            {
                throw new IllegalArgumentException("Contract list should have at least 1 element");
            }
            contracts.remove(contract);
            contract.removeLinks();
        }
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

    public void addPurchase(Purchase purchase)
    {
        if(purchase == null)
        {
            throw new IllegalArgumentException("Purchase must not be null");
        }
        if(!purchases.contains(purchase))
        {
            purchases.add(purchase);
        }
    }

    public void removePurchase(Purchase purchase)
    {
        if(purchase == null)
        {
            throw new IllegalArgumentException("Purchase must not be null");
        }

        if(purchases.contains(purchase))
        {
            if(purchases.size() == 1)
            {
                throw new IllegalArgumentException("Purchase list should have at least 1 element");
            }
            purchases.remove(purchase);
            purchase.removeLinks();
        }
    }
}
