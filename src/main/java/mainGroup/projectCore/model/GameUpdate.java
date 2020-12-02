package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
public class GameUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long updateId;

    @NotBlank(message = "Update version must be specified")
    private String versionNumber;

    @PastOrPresent
    private LocalDate releaseDate;

    @NotBlank(message = "Update description must be specified")
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "gameId")
    private Game game;

    public GameUpdate() {
    }

    private GameUpdate(@NotBlank(message = "Update version must be specified") String versionNumber, @PastOrPresent LocalDate releaseDate, @NotBlank(message = "Update description must be specified") String description, Game game) {
        setVersionNumber(versionNumber);
        setReleaseDate(releaseDate);
        setDescription(description);
        setGame(game);
    }

    public static void createGameUpdate(Game game, String versionNumber, LocalDate releaseDate, String description)
    {
        if(game == null)
        {
            throw new IllegalArgumentException("Game must not be null");
        }

        GameUpdate gu = new GameUpdate(versionNumber, releaseDate, description, game);
        game.addUpdate(gu);
    }

    public long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(long updateId) {
        this.updateId = updateId;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Game getGame() {
        return game;
    }

    private void setGame(Game game) {
        this.game = game;
    }

    public void removeGame()
    {
        game.removeUpdate(this);
        if(game != null)
        {
            game = null;
        }
    }
}
