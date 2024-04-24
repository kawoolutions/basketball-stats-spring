package io.kawoolutions.bbstats.dto;

import io.kawoolutions.bbstats.entity.CompetitionType;
import io.kawoolutions.bbstats.util.GameUtil;
import io.kawoolutions.bbstats.util.NamingUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ScheduleScoreDto extends BaseDto {
    private static final long serialVersionUID = 1L;

    private final Integer gameId;

    private final Integer matchdayNumber;
    private final String officialNumber;
    private final String officialNumberZeroPadded;

    private final LocalDateTime scheduledTipoff;
    private final LocalDateTime actualTipoff;
    private final LocalDateTime tipoff;

    private final Integer arenaId;
    private final String arenaName;

    private final Integer homeRosterId;
    private final String homeTeamName;
    private final String homeTeamCode;
    private final int homeFinalScore;

    private final Integer awayRosterId;
    private final String awayTeamName;
    private final String awayTeamCode;
    private final int awayFinalScore;

    private final boolean hasFinalScore;
    private final int deltaFinalScore;
    private final boolean hasPlayerStats;
    private final boolean withdrawn;

    private CompetitionType competitionType;

    //    public ScheduleScoreDto( Integer gameId, Integer matchdayNumber, String officialNumber, Date scheduledTipoff, Date actualTipoff,
    public ScheduleScoreDto(Integer gameId, Integer matchdayNumber, String officialNumber, LocalDateTime scheduledTipoff, LocalDateTime actualTipoff,
                            Integer arenaId, String arenaName,
                            Integer homeRosterId, String homeClubName, String homeClubCode, int homeTeamOrdinalNumber, Integer homeFinalScore,
                            Integer awayRosterId, String awayClubName, String awayClubCode, int awayTeamOrdinalNumber, Integer awayFinalScore,
                            boolean hasFinalScore, boolean hasPlayerStats, boolean withdrawn, CompetitionType competitionType) {
        this(gameId, matchdayNumber, officialNumber, scheduledTipoff, actualTipoff, arenaId, arenaName, homeRosterId, homeClubName,
                homeClubCode, homeTeamOrdinalNumber, homeFinalScore, awayRosterId, awayClubName, awayClubCode,
                awayTeamOrdinalNumber, awayFinalScore, hasFinalScore, hasPlayerStats, withdrawn);

        this.competitionType = competitionType;
    }

    //    public ScheduleScoreDto( Integer gameId, Integer matchdayNumber, String officialNumber, Date scheduledTipoff, Date actualTipoff,
    public ScheduleScoreDto(Integer gameId, Integer matchdayNumber, String officialNumber, LocalDateTime scheduledTipoff, LocalDateTime actualTipoff,
                            Integer arenaId, String arenaName,
                            Integer homeRosterId, String homeClubName, String homeClubCode, int homeTeamOrdinalNumber, Integer homeFinalScore,
                            Integer awayRosterId, String awayClubName, String awayClubCode, int awayTeamOrdinalNumber, Integer awayFinalScore,
                            boolean hasFinalScore, boolean hasPlayerStats, boolean withdrawn) {
        this.gameId = gameId;
        this.matchdayNumber = matchdayNumber;
        this.officialNumber = officialNumber;

        // only works for German game numbers: store format in Rounds table? CompetitionMetas?
        if (officialNumber != null) {
            String numberOnly = null;
            String paddedNumber = null;

            if (officialNumber.endsWith("R")) {
                numberOnly = officialNumber.substring(0, officialNumber.length() - 1);
            } else {
                numberOnly = officialNumber;
            }

            paddedNumber = StringUtils.leftPad(numberOnly, 3, "0");

            if (officialNumber.endsWith("R")) {
                paddedNumber += "R";
            }

            this.officialNumberZeroPadded = paddedNumber;
        } else {
            this.officialNumberZeroPadded = null;
        }

        this.scheduledTipoff = scheduledTipoff;
        this.actualTipoff = actualTipoff;
        this.tipoff = actualTipoff != null ? actualTipoff : scheduledTipoff;

        this.arenaId = arenaId;
        this.arenaName = arenaName;

        this.homeRosterId = homeRosterId;
        this.homeTeamName = NamingUtil.getTeamLabelFor(homeClubName, homeTeamOrdinalNumber, homeClubCode);
        this.homeTeamCode = homeClubCode + homeTeamOrdinalNumber;
        this.homeFinalScore = homeFinalScore != null ? homeFinalScore.intValue() : -1;

        this.awayRosterId = awayRosterId;
        this.awayTeamName = NamingUtil.getTeamLabelFor(awayClubName, awayTeamOrdinalNumber, awayClubCode);
        this.awayTeamCode = awayClubCode + " " + awayTeamOrdinalNumber;
        this.awayFinalScore = awayFinalScore != null ? awayFinalScore.intValue() : -1;

        this.hasFinalScore = hasFinalScore;
        this.deltaFinalScore = hasFinalScore ? Math.abs(this.homeFinalScore - this.awayFinalScore) : -1;
        this.hasPlayerStats = hasPlayerStats;
        this.withdrawn = withdrawn;
    }

    // game data

    public Integer getGameId() {
        return gameId;
    }

    public Integer getMatchdayNumber() {
        return matchdayNumber;
    }

    public String getOfficialNumber() {
        return officialNumber;
    }

    public String getOfficialNumberZeroPadded() {
        return officialNumberZeroPadded;
    }

    // tipoff times

    //    public Date getScheduledTipoff()
    public LocalDateTime getScheduledTipoff() {
        return scheduledTipoff;
    }

    //    public Date getActualTipoff()
    public LocalDateTime getActualTipoff() {
        return actualTipoff;
    }

    //    public Date getTipoff()
    public LocalDateTime getTipoff() {
        return actualTipoff != null ? actualTipoff : scheduledTipoff;
    }

    // arena

    public Integer getArenaId() {
        return arenaId;
    }

    public String getArenaName() {
        return arenaName;
    }

    // home team

    public Integer getHomeRosterId() {
        return homeRosterId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getHomeTeamCode() {
        return homeTeamCode;
    }

    public int getHomeFinalScore() {
        return homeFinalScore;
    }

    // away team

    public Integer getAwayRosterId() {
        return awayRosterId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public String getAwayTeamCode() {
        return awayTeamCode;
    }

    public int getAwayFinalScore() {
        return awayFinalScore;
    }

    // score status

    public boolean hasFinalScore() {
        return hasFinalScore;
    }

    public boolean getHasFinalScore() {
        return hasFinalScore;
    }

    public int getDeltaFinalScore() {
        return deltaFinalScore;
    }

    public boolean hasPlayerStats() {
        return hasPlayerStats;
    }

    public boolean getHasPlayerStats() {
        return hasPlayerStats;
    }

    public boolean getWithdrawn() {
        return withdrawn;
    }

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    // sorting stuff

    public String getScoreSortProxy() {
        if (!hasFinalScore || withdrawn) {
            return "B000";
        }

        // used for sorting by score (max. difference 999 points)
        return new DecimalFormat("'A'000").format(Math.abs(deltaFinalScore - 1000));
    }

    public String getScoreSortProxyFor(Integer rosterId) {
        if (!hasFinalScore || withdrawn) {
            return "C000";
        }

        // always positive (at first)
        int rosterDependentDelta = deltaFinalScore;

        // now determine comparable proxy value for sorting
        if (homeRosterId == rosterId && homeFinalScore > awayFinalScore || awayRosterId == rosterId && homeFinalScore < awayFinalScore) {
            // WIN depending on rosterId!
            rosterDependentDelta -= 1000;
        }

        // used for sorting by score (max. difference 999 points)
        return new DecimalFormat("'B'000;'A'000").format(rosterDependentDelta);
    }

    public FinalScoreStatus getFinalScoreStatus() {
        return GameUtil.getFinalScoreStatusFor(gameId.intValue(), homeFinalScore, awayFinalScore, withdrawn, tipoff);
    }

    public boolean filterBothTeamNames(Object row, Object filter, @SuppressWarnings("unused") Locale locale) {
        ScheduleScoreDto dto = (ScheduleScoreDto) row;
        String filterValue = (String) filter;

//        System.out.println( getClass().getSimpleName() + ".filterBothTeamNames(): " + filterValue );

        if (filterValue == null) {
            return true;
        }

        return StringUtils.containsIgnoreCase(dto.getHomeTeamName(), filterValue) || StringUtils.containsIgnoreCase(dto.getAwayTeamName(), filterValue);
    }

    // for shell program
    @Override
    public List<Object> getValues() {
        return Arrays.asList(gameId, actualTipoff, arenaName, homeTeamName, awayTeamName, hasFinalScore ? homeFinalScore + ":" + awayFinalScore : "", hasPlayerStats ? "+" : "", withdrawn ? "*" : "");
    }
}
