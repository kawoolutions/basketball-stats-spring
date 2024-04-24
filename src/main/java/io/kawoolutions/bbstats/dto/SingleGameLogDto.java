package io.kawoolutions.bbstats.dto;

import io.kawoolutions.bbstats.util.NamingUtil;

public abstract class SingleGameLogDto extends BaseGameLogDto {
    private static final long serialVersionUID = 1L;

    protected SingleGameLogDto() {
        super();
    }

    // per player, single game -> watch out for BoxScoreDto(): must be nullable!
    protected SingleGameLogDto(String lastName, String firstName, boolean incognito, long threePointersMade, long freeThrowsMade,
                               long freeThrowsAttempted, long personalFouls, long points) {
        this(NamingUtil.getFormalPersonNameFor(lastName, firstName, incognito), threePointersMade, freeThrowsMade,
                freeThrowsAttempted, personalFouls, points);
    }

    // per team, single game
    protected SingleGameLogDto(String name, long threePointersMade, long freeThrowsMade, long freeThrowsAttempted, long personalFouls,
                               long points) {
        super(name, Long.valueOf(threePointersMade), Long.valueOf(freeThrowsMade), Long.valueOf(freeThrowsAttempted), Long.valueOf(personalFouls), Long.valueOf(points));
    }
}
