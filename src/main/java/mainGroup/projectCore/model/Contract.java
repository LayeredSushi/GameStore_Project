package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long contractId;

    @PastOrPresent
    private LocalDate signedDate;

    @NotEmpty(message = "Released platforms must be specified")
    private HashSet<String> releasedPlatforms = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "gameId")
    private Game game;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "publisherId")
    private Publisher publisher;

    public Contract() {
    }

    public Contract(@PastOrPresent LocalDate signedDate, @NotEmpty(message = "Released platforms must be specified") HashSet<String> releasedPlatforms, Game game, Publisher publisher) {
        setSignedDate(signedDate);
        setReleasedPlatforms(releasedPlatforms);
        setGame(game);
        setPublisher(publisher);
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public LocalDate getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(LocalDate signedDate) {
        this.signedDate = signedDate;
    }

    public HashSet<String> getReleasedPlatforms() {
        return new HashSet<>(releasedPlatforms);
    }

    private void setReleasedPlatforms(HashSet<String> releasedPlatforms) {
        for(String platform: releasedPlatforms)
            addReleasedPlatform(platform);
    }

    public void addReleasedPlatform(String platform)
    {
        if(platform == null) {
            throw new IllegalArgumentException("Platform must not be null");
        }
        releasedPlatforms.add(platform);
    }

    public void removeProduct(String platform)
    {
        releasedPlatforms.remove(platform);
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
        game.addContract(this);
    }

    private void removeGame()
    {
        if(game != null)
        {
            game.removeContract(this);
            game = null;
        }
    }

    public Publisher getPublisher() {
        return publisher;
    }


    private void setPublisher(Publisher publisher) {
        if(publisher == null)
        {
            throw new IllegalArgumentException("Supplier must not be null");
        }

        this.publisher = publisher;
        publisher.addContract(this);
    }

    private void removePublisher()
    {
        if(publisher != null)
        {
            publisher.removeContract(this);
            publisher = null;
        }
    }


    public void removeLinks()
    {
        removePublisher();
        removeGame();
    }
}
