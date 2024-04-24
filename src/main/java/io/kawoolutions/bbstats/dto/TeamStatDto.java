package io.kawoolutions.bbstats.dto;

import io.kawoolutions.bbstats.util.NamingUtil;

import java.util.Arrays;
import java.util.List;

public class TeamStatDto extends MultiGameLogDto {
    // team info

    protected final Integer rosterId;

    // protected final long high;
    // protected final long low;

    private static final long serialVersionUID = 1L;

    public TeamStatDto(Integer rosterId, String clubName, String clubCode, int teamNumber, long games, long threePointersMade, long freeThrowsMade,
                       long freeThrowsAttempted, long personalFouls, long foulOuts, long points) {
        super(NamingUtil.getShortTeamLabelFor(clubName, teamNumber), games, threePointersMade, freeThrowsMade, freeThrowsAttempted,
                personalFouls, foulOuts, points);

        this.rosterId = rosterId;

//        this.high = high;
//        this.low = low;
    }

    public Integer getRosterId() {
        return rosterId;
    }

    // view line impl
    @Override
    public List<Object> getValues() {
        return Arrays.asList(name, Long.valueOf(games), fieldGoalsMade, twoPointersMade, threePointersMade, fieldGoalsMadePerGame,
                threePointersMadePerGame, freeThrowsMade, freeThrowsAttempted, freeThrowsMadePerGame,
                freeThrowsAttemptedPerGame, freeThrowPercentage, points, pointsPerGame);
    }
}
