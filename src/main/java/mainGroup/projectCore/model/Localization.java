package mainGroup.projectCore.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Localization {

    @Id
    @GeneratedValue
    private long localizationId;

    @NotBlank
    private String language;

    public enum TranslationType {
        Manual,
        Automated
    }

    @Enumerated
    private TranslationType translationType;

    @Min(1)
    @Max(5)
    private int translationAccuracy;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "gameId")
    private Game game;

    public Localization() {
    }

    private Localization(@NotBlank String language, TranslationType translationType, @Min(1) @Max(5) int translationAccuracy, Game game) {
        setLanguage(language);
        setTranslationType(translationType);
        setTranslationAccuracy(translationAccuracy);
        setGame(game);
    }

    public static void createLocalization(Game game, String language, TranslationType translationType, int translationAccuracy)
    {
        if(game == null)
        {
            throw new IllegalArgumentException("Game must not be null");
        }

        Localization localization = new Localization(language, translationType, translationAccuracy, game);
        game.addLocalization(localization);
    }

    public long getLocalizationId() {
        return localizationId;
    }

    public void setLocalizationId(long localizationId) {
        this.localizationId = localizationId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public TranslationType getTranslationType() {
        return translationType;
    }

    public void setTranslationType(TranslationType translationType) {
        this.translationType = translationType;
    }

    public int getTranslationAccuracy() {
        return translationAccuracy;
    }

    public void setTranslationAccuracy(int translationAccuracy) {
        this.translationAccuracy = translationAccuracy;
    }

    public Game getGame() {
        return game;
    }

    private void setGame(Game game) {
        this.game = game;
    }

    public void removeGame()
    {
        if(game != null)
        {
            game.removeLocalization(this);
            game = null;
        }
    }
}
