package io.kawoolutions.bbstats.dto;

import io.kawoolutions.bbstats.util.NamingUtil;

import java.util.Arrays;
import java.util.List;

public class StandingsDto extends BaseDto {
    private static final long serialVersionUID = 1L;

    private final Integer rosterId;

    private final String clubName;
    private final String clubCode;

    private final String teamName;
    private final String teamCode;

    private final boolean withdrawn;

    private final long games;
    private final long gamesNotPlayed;
    private final long gamesPlayed;

    private final long wins;
    private final long losses;

    private final long winsByRule;
    private final long lossesByRule;
    private final long bothLossesByRule;

    private final long winsPlayed;
    private final long lossesPlayed;

    // sort order in JPQL
    private final long rankingPoints;
    private Double trueWinPercentage;
    private final Double winPercentage;

    private final long pointsFor;
    private final long pointsAgainst;
    private final long pointsDifference;

    private final Double pointsForPerGame;
    private final Double pointsAgainstPerGame;
    private final Double pointsDifferencePerGame;

    private final long pointsForPlayed;
    private final long pointsAgainstPlayed;
    private final long pointsDifferencePlayed;

    private final Double pointsForPerGamePlayed;
    private final Double pointsAgainstPerGamePlayed;
    private final Double pointsDifferencePerGamePlayed;

    private final Long highestScore;
    private final Long highestScoreGameId;
    private final Long lowestScore;
    private final Long lowestScoreGameId;

    // needed internally for fake rows
    public StandingsDto(Integer rosterId, String clubName, String clubCode, int teamOrdinalNumber, boolean withdrawn) {
        this(rosterId, clubName, clubCode, teamOrdinalNumber, withdrawn,
                0, 0, 0, 0, 0, 0,
                0, 0,
                0, Double.valueOf(0.0), Double.valueOf(0.0), 0, 0,
                (Long) null, null, null, null);
    }

    // Hibernate needs the constructor with 4 Integer's at the end, otherwise you will get:
    // Group.findStandingsDtos failed because of: org.hibernate.hql.internal.ast.QuerySyntaxException: Unable to locate appropriate constructor on class [io.kawoolutions.bbstats.dto.StandingsDto]. Expected arguments are: int, java.lang.String, java.lang.String, int, boolean, long, long, long, long, long, long, long, double, double, long, long, int, int, int, int [SELECT NEW io.kawoolutions.bbstats.dto.StandingsDto( ro.id
    public StandingsDto(Integer rosterId, String clubName, String clubCode, int teamOrdinalNumber, boolean withdrawn,
                        long games, long wins, long losses, long winsByRule, long lossesByRule, long bothLossesByRule,
                        long gamesPlayed, long winsPlayed,
                        long rankingPoints, Double trueWinPercentage, Double winPercentage,
                        long pointsFor, long pointsAgainst,
                        Integer highestScore, Integer highestScoreGameId, Integer lowestScore, Integer lowestScoreGameId) {
        this(rosterId, clubName, clubCode, teamOrdinalNumber, withdrawn,
                games, wins, losses, winsByRule, lossesByRule, bothLossesByRule,
                gamesPlayed, winsPlayed,
                rankingPoints, trueWinPercentage, winPercentage,
                pointsFor, pointsAgainst,
                highestScore != null ? Long.valueOf(highestScore.longValue()) : null, highestScoreGameId != null ? Long.valueOf(highestScoreGameId.longValue()) : null,
                lowestScore != null ? Long.valueOf(lowestScore.longValue()) : null, lowestScoreGameId != null ? Long.valueOf(lowestScoreGameId.longValue()) : null);
    }

    // EclipseLink needs the constructor with 4 Long's at the end, otherwise you will get:
    // Caused by: java.lang.IllegalArgumentException: argument type mismatch
    public StandingsDto(Integer rosterId, String clubName, String clubCode, int teamOrdinalNumber, boolean withdrawn,
                        long games, long wins, long losses, long winsByRule, long lossesByRule, long bothLossesByRule,
                        long gamesPlayed, long winsPlayed,
                        long rankingPoints, Double trueWinPercentage, Double winPercentage,
                        long pointsFor, long pointsAgainst,
                        Long highestScore, Long highestScoreGameId, Long lowestScore, Long lowestScoreGameId) {

        this.rosterId = rosterId;

        this.clubName = clubName;
        this.clubCode = clubCode;

        this.teamName = NamingUtil.getTeamLabelFor(clubName, teamOrdinalNumber, clubCode);
        this.teamCode = clubCode + teamOrdinalNumber;
        this.withdrawn = withdrawn;

        this.games = games;
        this.gamesNotPlayed = winsByRule + lossesByRule + bothLossesByRule;
//        this.gamesPlayed = games - gamesNotPlayed;
        this.gamesPlayed = gamesPlayed;

        this.wins = wins;
        this.losses = losses;
        this.winsByRule = winsByRule; // 20:0's
        this.lossesByRule = lossesByRule; // 0:20's
        this.bothLossesByRule = bothLossesByRule; // 0:0's

//        this.winsPlayed = wins - winsByRule;
        this.winsPlayed = winsPlayed;
        this.lossesPlayed = losses - lossesByRule - bothLossesByRule;

        this.rankingPoints = rankingPoints;
//        this.trueWinPercentage = gamesPlayed != 0 ? winsPlayed / ( double ) gamesPlayed : null;
        this.trueWinPercentage = gamesPlayed == 0 ? null : trueWinPercentage;

//        System.out.println( clubCode + teamOrdinalNumber +
//                            ": games played (Q) = " + gamesPlayed + ", games played (J) = " + ( games - gamesNotPlayed ) +
//                            ", wins played (Q) = " + winsPlayed + ", wins played (J) = " + ( wins - winsByRule ) +
//                            ", true win % (Q) = " + trueWinPercentage + ", true win % (J) = " + ( gamesPlayed != 0 ? winsPlayed / ( double ) gamesPlayed : null ) );

        this.winPercentage = winPercentage != null ? Double.valueOf(winPercentage.doubleValue()) : null;

        this.pointsFor = pointsFor;
        this.pointsAgainst = pointsAgainst;
        this.pointsDifference = pointsFor - pointsAgainst;

        this.pointsForPerGame = games > 0 ? Double.valueOf((double) pointsFor / games) : null;
        this.pointsAgainstPerGame = games > 0 ? Double.valueOf((double) pointsAgainst / games) : null;
        this.pointsDifferencePerGame = games > 0 ? Double.valueOf((double) pointsDifference / games) : null;

        // cleaned stats without wins and losses by rule!
        this.pointsForPlayed = pointsFor - winsByRule * 20;
        this.pointsAgainstPlayed = pointsAgainst - lossesByRule * 20;
        this.pointsDifferencePlayed = pointsForPlayed - pointsAgainstPlayed;

        this.pointsForPerGamePlayed = gamesPlayed > 0 ? Double.valueOf((double) pointsForPlayed / gamesPlayed) : null;
        this.pointsAgainstPerGamePlayed = gamesPlayed > 0 ? Double.valueOf((double) pointsAgainstPlayed / gamesPlayed) : null;
        this.pointsDifferencePerGamePlayed = gamesPlayed > 0 ? Double.valueOf((double) pointsDifferencePlayed / gamesPlayed) : null;

        this.highestScore = highestScore;
        this.highestScoreGameId = highestScoreGameId;
        this.lowestScore = lowestScore;
        this.lowestScoreGameId = lowestScoreGameId;
    }

    public Integer getRosterId() {
        return rosterId;
    }

    public String getClubName() {
        return clubName;
    }

    public String getClubCode() {
        return clubCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean getWithdrawn() {
        return withdrawn;
    }

    // static standings data

    public long getGames() {
        return withdrawn ? 0 : games;
    }

    public long getWins() {
        return withdrawn ? 0 : wins;
    }

    public long getLosses() {
        return withdrawn ? 0 : losses;
    }

    public long getWinsByRule() {
        return withdrawn ? 0 : winsByRule;
    }

    public long getLossesByRule() {
        return withdrawn ? 0 : lossesByRule;
    }

    public long getBothLossesByRule() {
        return withdrawn ? 0 : bothLossesByRule;
    }

    public long getGamesPlayed() {
        return withdrawn ? 0 : gamesPlayed;
    }

    public long getGamesNotPlayed() {
        return withdrawn ? 0 : gamesNotPlayed;
    }

    public long getWinsPlayed() {
        return withdrawn ? 0 : winsPlayed;
    }

    public long getLossesPlayed() {
        return withdrawn ? 0 : lossesPlayed;
    }

    // official data
    public long getRankingPoints() {
        return withdrawn ? 0 : rankingPoints;
    }

    public Double getTrueWinPercentage() {
        return withdrawn ? null : trueWinPercentage;
    }

    public Double getWinPercentage() {
        return withdrawn ? null : winPercentage;
    }

    public long getPointsFor() {
        return withdrawn ? 0 : pointsFor;
    }

    public long getPointsAgainst() {
        return withdrawn ? 0 : pointsAgainst;
    }

    public long getPointsDifference() {
        return withdrawn ? 0 : pointsDifference;
    }

    public Double getPointsForPerGame() {
        return withdrawn ? null : pointsForPerGame;
    }

    public Double getPointsAgainstPerGame() {
        return withdrawn ? null : pointsAgainstPerGame;
    }

    public Double getPointsDifferencePerGame() {
        return withdrawn ? null : pointsDifferencePerGame;
    }

    // played data
    public long getPointsForPlayed() {
        return withdrawn ? 0 : pointsForPlayed;
    }

    public long getPointsAgainstPlayed() {
        return withdrawn ? 0 : pointsAgainstPlayed;
    }

    public long getPointsDifferencePlayed() {
        return withdrawn ? 0 : pointsDifferencePlayed;
    }

    public Double getPointsForPerGamePlayed() {
        return withdrawn ? null : pointsForPerGamePlayed;
    }

    public Double getPointsAgainstPerGamePlayed() {
        return withdrawn ? null : pointsAgainstPerGamePlayed;
    }

    public Double getPointsDifferencePerGamePlayed() {
        return withdrawn ? null : pointsDifferencePerGamePlayed;
    }

    // highest and lowest score data

    public Long getHighestScore() {
        return withdrawn ? null : highestScore;
    }

    public Long getHighestScoreGameId() {
        return withdrawn ? null : highestScoreGameId;
    }

    public Long getLowestScore() {
        return withdrawn ? null : lowestScore;
    }

    public Long getLowestScoreGameId() {
        return withdrawn ? null : lowestScoreGameId;
    }

    @Override
    public List<Object> getValues() {
        return Arrays.asList(rosterId, teamCode, Boolean.valueOf(withdrawn), Long.valueOf(getGames()),
                Long.valueOf(getGamesPlayed()), Long.valueOf(getWins()), Long.valueOf(getLosses()),
                Long.valueOf(getWinsByRule()), Long.valueOf(getLossesByRule()), Long.valueOf(getBothLossesByRule()),
                Long.valueOf(getRankingPoints()), getTrueWinPercentage(), getWinPercentage(),
                Long.valueOf(getPointsFor()), Long.valueOf(getPointsAgainst()), Long.valueOf(getPointsDifference()),
                getPointsForPerGamePlayed(), getPointsAgainstPerGamePlayed(), getPointsDifferencePerGamePlayed(),
                getHighestScore(), getHighestScoreGameId(), getLowestScore(), getLowestScoreGameId());
    }
}
