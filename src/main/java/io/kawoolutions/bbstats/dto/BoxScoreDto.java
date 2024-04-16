package io.kawoolutions.bbstats.dto;

import java.util.Arrays;
import java.util.List;

public class BoxScoreDto extends SingleGameLogDto {
    private static final long serialVersionUID = 1L;

    protected final Integer playerId;
    protected final Integer seasonStartYear;

    protected final Integer jerseyNumber;
    protected final Boolean hasPlayed;
    protected final Boolean starter;

    // constructor for padding dummy
    public BoxScoreDto() {
        super();

        this.playerId = null;
        this.seasonStartYear = null;

        this.jerseyNumber = null;
        this.hasPlayed = null;
        this.starter = null;
    }

    // individual (per player) -> directly from GameLogs view, which has Integers
    public BoxScoreDto(Integer playerId, Integer seasonStartYear,
                       String lastName, String firstName, boolean incognito,
                       Integer jerseyNumber, Boolean hasPlayed, Boolean starter,
                       int threePointersMade, int freeThrowsMade, int freeThrowsAttempted,
                       int personalFouls, int points) {
        this(playerId, seasonStartYear, lastName, firstName, incognito, jerseyNumber, hasPlayed, starter, threePointersMade, freeThrowsMade,
                freeThrowsAttempted, personalFouls, (long) points);
    }

    // total (per team) -> SUM() results in Long
    public BoxScoreDto(Integer playerId, Integer seasonStartYear,
                       String lastName, String firstName, boolean incognito,
                       Integer jerseyNumber, Boolean hasPlayed, Boolean starter,
                       long threePointersMade, long freeThrowsMade, long freeThrowsAttempted,
                       long personalFouls, long points) {
        super(lastName, firstName, incognito, threePointersMade, freeThrowsMade, freeThrowsAttempted, personalFouls, points);

        this.playerId = playerId;
        this.seasonStartYear = seasonStartYear;

        this.jerseyNumber = jerseyNumber;
        this.hasPlayed = hasPlayed;
        this.starter = starter;
    }

    // total (team)
    public BoxScoreDto(long threePointersMade, long freeThrowsMade, long freeThrowsAttempted, long personalFouls, long points) {
        super(null, threePointersMade, freeThrowsMade, freeThrowsAttempted, personalFouls, points);

        this.playerId = null;
        this.seasonStartYear = null;

        this.jerseyNumber = null;
        this.hasPlayed = Boolean.FALSE;
        this.starter = Boolean.FALSE;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getSeasonStartYear() {
        return seasonStartYear;
    }

    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public Boolean getHasPlayed() {
        return hasPlayed;
    }

    public Boolean getStarter() {
        return starter;
    }

    @Override
    public List<Object> getValues() {
        return Arrays.asList(name, hasPlayed.booleanValue() ? "X" : "", jerseyNumber, starter, fieldGoalsMade, twoPointersMade,
                threePointersMade, freeThrowsMade, freeThrowsAttempted, freeThrowPercentage, personalFouls, points);
    }
}
