package io.kawoolutions.bbstats.dto;

public abstract class BaseGameLogDto extends BaseDto {
    private static final long serialVersionUID = 1L;

    // label (player name, team name, ...)
    protected final String name;

    protected final Long fieldGoalsMade;
    protected final Long twoPointersMade;
    protected final Long threePointersMade;

    protected final Long freeThrowsMade;
    protected final Long freeThrowsAttempted;
    protected final Double freeThrowPercentage;

    protected final Long personalFouls;

    protected final Long points;

    protected BaseGameLogDto() {
        this(null, null, null, null, null, null);
    }

    protected BaseGameLogDto(String name, Long threePointersMade, Long freeThrowsMade, Long freeThrowsAttempted, Long personalFouls,
                             Long points) {
        this.name = name;

        this.fieldGoalsMade = points != null && freeThrowsMade != null && threePointersMade != null ? Long.valueOf((points.longValue() - freeThrowsMade.longValue() - threePointersMade.longValue()) / 2) : null;
        this.twoPointersMade = fieldGoalsMade != null && threePointersMade != null ? Long.valueOf(fieldGoalsMade.longValue() - threePointersMade.longValue()) : null;
        this.threePointersMade = threePointersMade;

        this.freeThrowsMade = freeThrowsMade;
        this.freeThrowsAttempted = freeThrowsAttempted;
        this.freeThrowPercentage = freeThrowsMade != null && freeThrowsAttempted != null && freeThrowsAttempted.longValue() > 0 ? Double.valueOf((double) freeThrowsMade.longValue() / freeThrowsAttempted.longValue()) : null;

        this.personalFouls = personalFouls;

        this.points = points;
    }

    public final String getName() {
        return name;
    }

    // stats per quarter

    public Long getFieldGoalsMade() {
        return fieldGoalsMade;
    }

    public Long getTwoPointersMade() {
        return twoPointersMade;
    }

    public Long getThreePointersMade() {
        return threePointersMade;
    }

    public Long getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public Long getFreeThrowsAttempted() {
        return freeThrowsAttempted;
    }

    public Double getFreeThrowPercentage() {
        return freeThrowPercentage;
    }

    // personal fouls

    public Long getPersonalFouls() {
        return personalFouls;
    }

    // points

    public Long getPoints() {
        return points;
    }
}
