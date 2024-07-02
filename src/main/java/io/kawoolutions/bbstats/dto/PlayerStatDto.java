package io.kawoolutions.bbstats.dto;

import java.util.Arrays;
import java.util.List;

import io.kawoolutions.bbstats.util.NamingUtil;

public class PlayerStatDto extends MultiGameLogDto {
    private static final long serialVersionUID = 1L;

    // player info
    protected final Integer playerId;
    protected final Integer seasonStartYear;

    protected final String lastName;
    protected final String firstName;
    protected final boolean incognito;
    protected final String formalName;

    protected final long gamesPlayed;
    protected final long gamesNotPlayed;
    protected final long gamesStarted;
    protected final Double gamesStartedPercentage;

    protected final Double foulOutPercentage;

    protected final Integer high;
    protected final Integer low;

    public PlayerStatDto(Integer playerId, Integer seasonStartYear, String lastName, String firstName, boolean incognito, boolean hasPlayed, boolean started,
                         long threePointersMade, long freeThrowsMade, long freeThrowsAttempted, long personalFouls, boolean fouledOut,
                         long points) {
        this(playerId, seasonStartYear, lastName, firstName, incognito, 1L, hasPlayed ? 1L : 0L, started ? 1L : 0L,
                threePointersMade, freeThrowsMade, freeThrowsAttempted, personalFouls, fouledOut ? 1L : 0L,
                points, null, null);
    }

    public PlayerStatDto(Integer playerId, Integer seasonStartYear, String lastName, String firstName, boolean incognito, long games, long gamesNotPlayed, long gamesStarted,
                         long threePointersMade, long freeThrowsMade, long freeThrowsAttempted, long personalFouls, long foulOuts,
                         long points, Integer high, Integer low) {
        super(lastName, firstName, incognito, games, threePointersMade, freeThrowsMade, freeThrowsAttempted, personalFouls, foulOuts,
                points);

        this.playerId = playerId;
        this.seasonStartYear = seasonStartYear;

        this.lastName = lastName;
        this.firstName = firstName;
        this.incognito = incognito;

        this.formalName = NamingUtil.getFormalPersonNameFor(lastName, firstName, incognito);

        // per player only
        this.gamesNotPlayed = gamesNotPlayed;
        this.gamesStarted = gamesStarted;

        this.gamesPlayed = games - gamesNotPlayed;
        this.gamesStartedPercentage = games > 0 ? Double.valueOf((double) gamesStarted / games) : null;

        this.foulOutPercentage = games > 0 ? Double.valueOf((double) foulOuts / games) : null;

        this.high = high;
        this.low = low;
    }

    // player info

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getSeasonStartYear() {
        return seasonStartYear;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isIncognito() {
        return incognito;
    }

    public String getFormalName() {
        return formalName;
    }

    // player stats

    public long getGamesPlayed() {
        return gamesPlayed;
    }

    public long getGamesNotPlayed() {
        return gamesNotPlayed;
    }

    public long getGamesStarted() {
        return gamesStarted;
    }

    public Double getGamesStartedPercentage() {
        return gamesStartedPercentage;
    }

    public Double getFoulOutPercentage() {
        return foulOutPercentage;
    }

    public Integer getHigh() {
        return high;
    }

    public Integer getLow() {
        return low;
    }

    @Override
    public List<Object> getValues() {
        return Arrays.asList(name, Long.valueOf(games), Long.valueOf(gamesNotPlayed), Long.valueOf(gamesStarted),
                fieldGoalsMade, twoPointersMade, threePointersMade,
                fieldGoalsMadePerGame, threePointersMadePerGame, freeThrowsMade,
                freeThrowsAttempted, freeThrowsMadePerGame, freeThrowsAttemptedPerGame, freeThrowPercentage,
                points, pointsPerGame, high, low);
    }
}
