package io.kawoolutions.bbstats.dto;

import io.kawoolutions.bbstats.util.NamingUtils;

public abstract class MultiGameLogDto extends BaseGameLogDto {
    private static final long serialVersionUID = 1L;

    // games
    protected final long games;

    // field goals
    protected final Double fieldGoalsMadePerGame;
    protected final Double twoPointersMadePerGame;
    protected final Double threePointersMadePerGame;

    // free throws
    protected final Double freeThrowsMadePerGame;
    protected final Double freeThrowsAttemptedPerGame;

    // personal fouls
    protected final Double personalFoulsPerGame;

    protected final long foulOuts;

    // points
    protected final Double pointsPerGame;

    // per player, multiple games
    protected MultiGameLogDto(String lastName, String firstName, boolean incognito, long games, long threePointersMade,
                              long freeThrowsMade, long freeThrowsAttempted, long personalFouls, long foulOuts, long points) {
        this(NamingUtils.getFormalPersonNameFor(lastName, firstName, incognito), games, threePointersMade, freeThrowsMade,
                freeThrowsAttempted, personalFouls, foulOuts, points);
    }

    // per team, multiple games
    public MultiGameLogDto(String name, long games, long threePointersMade, long freeThrowsMade, long freeThrowsAttempted,
                           long personalFouls, long foulOuts, long points) {
        super(name, Long.valueOf(threePointersMade), Long.valueOf(freeThrowsMade), Long.valueOf(freeThrowsAttempted), Long.valueOf(personalFouls), Long.valueOf(points));

        this.games = games;

        this.fieldGoalsMadePerGame = games > 0 ? Double.valueOf((double) fieldGoalsMade.longValue() / games) : null;
        this.twoPointersMadePerGame = games > 0 ? Double.valueOf((double) twoPointersMade.longValue() / games) : null;
        this.threePointersMadePerGame = games > 0 ? Double.valueOf((double) threePointersMade / games) : null;

        this.freeThrowsMadePerGame = games > 0 ? Double.valueOf((double) freeThrowsMade / games) : null;
        this.freeThrowsAttemptedPerGame = games > 0 ? Double.valueOf((double) freeThrowsAttempted / games) : null;

        this.personalFoulsPerGame = games > 0 ? Double.valueOf((double) personalFouls / games) : null;

        this.foulOuts = foulOuts;

        this.pointsPerGame = games > 0 ? Double.valueOf((double) points / games) : null;
    }

    // games
    public long getGames() {
        return games;
    }

    // field goals
    public Double getFieldGoalsMadePerGame() {
        return fieldGoalsMadePerGame;
    }

    public Double getTwoPointersMadePerGame() {
        return twoPointersMadePerGame;
    }

    public Double getThreePointersMadePerGame() {
        return threePointersMadePerGame;
    }

    // free throws
    public Double getFreeThrowsMadePerGame() {
        return freeThrowsMadePerGame;
    }

    public Double getFreeThrowsAttemptedPerGame() {
        return freeThrowsAttemptedPerGame;
    }

    // fouls
    public Double getPersonalFoulsPerGame() {
        return personalFoulsPerGame;
    }

    public long getFoulOuts() {
        return foulOuts;
    }

    // points
    public Double getPointsPerGame() {
        return pointsPerGame;
    }
}
