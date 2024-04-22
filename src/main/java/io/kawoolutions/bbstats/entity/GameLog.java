package io.kawoolutions.bbstats.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.entity.base.BaseEntity;

@Entity
@Table(name = "GameLogs")
@IdClass(GameLogId.class)
public class GameLog extends BaseEntity<GameLogId>
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "game_id")
    private Integer gameId;

    @Id
    @Column(name = "is_home")
    private Boolean home;

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Id
    @Column(name = "roster_id")
    private Integer rosterId;

    @Basic(optional = false)
    @Column(name = "season_start_year", insertable = false, updatable = false)
    private Integer seasonStartYear;

    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    private LocalDateTime tipoff;

    @Basic(optional = false)
    @Column(name = "scheduled_tipoff", insertable = false, updatable = false)
    private LocalDateTime scheduledTipoff;

    @Basic
    @Column(name = "game_matchday_nbr", insertable = false, updatable = false)
    private Integer gameMatchdayNbr;

    @Basic
    @Column(name = "game_official_nbr", insertable = false, updatable = false)
    private String gameOfficialNbr;

    @Basic(optional = false)
    @Column(name = "club_name", insertable = false, updatable = false)
    private String clubName;

    @Basic(optional = false)
    @Column(name = "club_code", insertable = false, updatable = false)
    private String clubCode;

    @Basic(optional = false)
    @Column(name = "team_ordinal_nbr", insertable = false, updatable = false)
    private Integer teamOrdinalNbr;

    @Basic(optional = false)
    @Column(name = "final_score", insertable = false, updatable = false)
    private Integer finalScore;

    @Basic(optional = false)
    @Column(name = "opponent_roster_id", insertable = false, updatable = false)
    private Integer opponentRosterId;

    @Basic(optional = false)
    @Column(name = "opponent_club_name", insertable = false, updatable = false)
    private String opponentClubName;

    @Basic(optional = false)
    @Column(name = "opponent_club_code", insertable = false, updatable = false)
    private String opponentClubCode;

    @Basic(optional = false)
    @Column(name = "opponent_team_ordinal_nbr", insertable = false, updatable = false)
    private Integer opponentTeamOrdinalNbr;

    @Basic(optional = false)
    @Column(name = "opponent_final_score", insertable = false, updatable = false)
    private Integer opponentFinalScore;

    @Basic(optional = false)
    @Column(name = "round_id", insertable = false, updatable = false)
    private Integer roundId;

    @Basic(optional = false)
    @Column(name = "group_code", insertable = false, updatable = false)
    private String groupCode;

    @Basic(optional = false)
    @Column(name = "competition_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private CompetitionType competitionType;

    @Basic(optional = false)
    @Column(name = "is_invalid", insertable = false, updatable = false)
    private Boolean invalid;

    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    private Boolean withdrawn;

    @Basic(optional = false)
    @Column(name = "last_name", insertable = false, updatable = false)
    private String lastName;

    @Basic(optional = false)
    @Column(name = "first_name", insertable = false, updatable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "is_incognito", insertable = false, updatable = false)
    private Boolean incognito;

    @Basic(optional = false)
    @Column(name = "jersey_nbr", insertable = false, updatable = false)
    private Integer jerseyNbr;

    @Basic(optional = false)
    @Column(name = "has_played", insertable = false, updatable = false)
    private Boolean hasPlayed;

    @Basic
    @Column(name = "is_starter", insertable = false, updatable = false)
    private Boolean starter;

    @Basic(optional = false)
    @Column(name = "field_goals_made", insertable = false, updatable = false)
    private Integer fieldGoalsMade;

    @Basic(optional = false)
    @Column(name = "two_pointers_made", insertable = false, updatable = false)
    private Integer twoPointersMade;

    @Basic(optional = false)
    @Column(name = "three_pointers_made", insertable = false, updatable = false)
    private Integer threePointersMade;

    @Basic(optional = false)
    @Column(name = "free_throws_made", insertable = false, updatable = false)
    private Integer freeThrowsMade;

    @Basic(optional = false)
    @Column(name = "free_throws_attempted", insertable = false, updatable = false)
    private Integer freeThrowsAttempted;

    @Basic
    @Column(name = "free_throw_percentage", insertable = false, updatable = false)
    private Double freeThrowPercentage;

    @Basic(optional = false)
    @Column(name = "personal_fouls", insertable = false, updatable = false)
    private Integer personalFouls;

    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    private Integer points;

    public GameLog()
    {
    }

    public GameLog(GameLog g)
    {
        this(g.getGameId(), g.getHome(), g.getPlayerId(), g.getRosterId(), g.getSeasonStartYear(), g.getTipoff(), g.getScheduledTipoff(), g.getGameMatchdayNbr(), g.getGameOfficialNbr(), g.getClubName(), g.getClubCode(), g.getTeamOrdinalNbr(), g.getFinalScore(), g.getOpponentRosterId(), g.getOpponentClubName(), g.getOpponentClubCode(), g.getOpponentTeamOrdinalNbr(), g.getOpponentFinalScore(), g.getRoundId(), g.getGroupCode(), g.getCompetitionType(), g.getInvalid(), g.getWithdrawn(), g.getLastName(), g.getFirstName(), g.getIncognito(), g.getJerseyNbr(), g.getHasPlayed(), g.getStarter(), g.getFieldGoalsMade(), g.getTwoPointersMade(), g.getThreePointersMade(), g.getFreeThrowsMade(), g.getFreeThrowsAttempted(), g.getFreeThrowPercentage(), g.getPersonalFouls(), g.getPoints());
    }

    public GameLog(Integer gameId, Boolean home, Integer playerId, Integer rosterId)
    {
        this(gameId, home, playerId, rosterId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public GameLog(Integer gameId, Boolean home, Integer playerId, Integer rosterId, Integer seasonStartYear, LocalDateTime tipoff, LocalDateTime scheduledTipoff, String clubName, String clubCode, Integer teamOrdinalNbr, Integer finalScore, Integer opponentRosterId, String opponentClubName, String opponentClubCode, Integer opponentTeamOrdinalNbr, Integer opponentFinalScore, Integer roundId, String groupCode, CompetitionType competitionType, Boolean invalid, Boolean withdrawn, String lastName, String firstName, Boolean incognito, Integer jerseyNbr, Boolean hasPlayed, Integer fieldGoalsMade, Integer twoPointersMade, Integer threePointersMade, Integer freeThrowsMade, Integer freeThrowsAttempted, Integer personalFouls, Integer points)
    {
        this(gameId, home, playerId, rosterId, seasonStartYear, tipoff, scheduledTipoff, null, null, clubName, clubCode, teamOrdinalNbr, finalScore, opponentRosterId, opponentClubName, opponentClubCode, opponentTeamOrdinalNbr, opponentFinalScore, roundId, groupCode, competitionType, invalid, withdrawn, lastName, firstName, incognito, jerseyNbr, hasPlayed, null, fieldGoalsMade, twoPointersMade, threePointersMade, freeThrowsMade, freeThrowsAttempted, null, personalFouls, points);
    }

    public GameLog(Integer seasonStartYear, LocalDateTime tipoff, LocalDateTime scheduledTipoff, Integer gameMatchdayNbr, String gameOfficialNbr, String clubName, String clubCode, Integer teamOrdinalNbr, Integer finalScore, Integer opponentRosterId, String opponentClubName, String opponentClubCode, Integer opponentTeamOrdinalNbr, Integer opponentFinalScore, Integer roundId, String groupCode, CompetitionType competitionType, Boolean invalid, Boolean withdrawn, String lastName, String firstName, Boolean incognito, Integer jerseyNbr, Boolean hasPlayed, Boolean starter, Integer fieldGoalsMade, Integer twoPointersMade, Integer threePointersMade, Integer freeThrowsMade, Integer freeThrowsAttempted, Double freeThrowPercentage, Integer personalFouls, Integer points)
    {
        this(null, null, null, null, seasonStartYear, tipoff, scheduledTipoff, gameMatchdayNbr, gameOfficialNbr, clubName, clubCode, teamOrdinalNbr, finalScore, opponentRosterId, opponentClubName, opponentClubCode, opponentTeamOrdinalNbr, opponentFinalScore, roundId, groupCode, competitionType, invalid, withdrawn, lastName, firstName, incognito, jerseyNbr, hasPlayed, starter, fieldGoalsMade, twoPointersMade, threePointersMade, freeThrowsMade, freeThrowsAttempted, freeThrowPercentage, personalFouls, points);
    }

    public GameLog(Integer gameId, Boolean home, Integer playerId, Integer rosterId, Integer seasonStartYear, LocalDateTime tipoff, LocalDateTime scheduledTipoff, Integer gameMatchdayNbr, String gameOfficialNbr, String clubName, String clubCode, Integer teamOrdinalNbr, Integer finalScore, Integer opponentRosterId, String opponentClubName, String opponentClubCode, Integer opponentTeamOrdinalNbr, Integer opponentFinalScore, Integer roundId, String groupCode, CompetitionType competitionType, Boolean invalid, Boolean withdrawn, String lastName, String firstName, Boolean incognito, Integer jerseyNbr, Boolean hasPlayed, Boolean starter, Integer fieldGoalsMade, Integer twoPointersMade, Integer threePointersMade, Integer freeThrowsMade, Integer freeThrowsAttempted, Double freeThrowPercentage, Integer personalFouls, Integer points)
    {
        this.gameId = Objects.requireNonNull(gameId);
        this.home = Objects.requireNonNull(home);
        this.playerId = Objects.requireNonNull(playerId);
        this.rosterId = Objects.requireNonNull(rosterId);
        this.seasonStartYear = seasonStartYear;
        this.tipoff = tipoff;
        this.scheduledTipoff = scheduledTipoff;
        this.gameMatchdayNbr = gameMatchdayNbr;
        this.gameOfficialNbr = gameOfficialNbr;
        this.clubName = clubName;
        this.clubCode = clubCode;
        this.teamOrdinalNbr = teamOrdinalNbr;
        this.finalScore = finalScore;
        this.opponentRosterId = opponentRosterId;
        this.opponentClubName = opponentClubName;
        this.opponentClubCode = opponentClubCode;
        this.opponentTeamOrdinalNbr = opponentTeamOrdinalNbr;
        this.opponentFinalScore = opponentFinalScore;
        this.roundId = roundId;
        this.groupCode = groupCode;
        this.competitionType = competitionType;
        this.invalid = invalid;
        this.withdrawn = withdrawn;
        this.lastName = lastName;
        this.firstName = firstName;
        this.incognito = incognito;
        this.jerseyNbr = jerseyNbr;
        this.hasPlayed = hasPlayed;
        this.starter = starter;
        this.fieldGoalsMade = fieldGoalsMade;
        this.twoPointersMade = twoPointersMade;
        this.threePointersMade = threePointersMade;
        this.freeThrowsMade = freeThrowsMade;
        this.freeThrowsAttempted = freeThrowsAttempted;
        this.freeThrowPercentage = freeThrowPercentage;
        this.personalFouls = personalFouls;
        this.points = points;
    }

    @Override
    public GameLogId getPk()
    {
        return new GameLogId(gameId, home, playerId, rosterId);
    }

    @Override
    public void setPk(GameLogId pk)
    {
        this.gameId = pk.getGameId();
        this.home = pk.getHome();
        this.playerId = pk.getPlayerId();
        this.rosterId = pk.getRosterId();
    }

    public Integer getGameId()
    {
        return gameId;
    }

    public Boolean getHome()
    {
        return home;
    }

    public Integer getPlayerId()
    {
        return playerId;
    }

    public Integer getRosterId()
    {
        return rosterId;
    }

    public Integer getSeasonStartYear()
    {
        return seasonStartYear;
    }

    public LocalDateTime getTipoff()
    {
        return tipoff;
    }

    public LocalDateTime getScheduledTipoff()
    {
        return scheduledTipoff;
    }

    public Integer getGameMatchdayNbr()
    {
        return gameMatchdayNbr;
    }

    public String getGameOfficialNbr()
    {
        return gameOfficialNbr;
    }

    public String getClubName()
    {
        return clubName;
    }

    public String getClubCode()
    {
        return clubCode;
    }

    public Integer getTeamOrdinalNbr()
    {
        return teamOrdinalNbr;
    }

    public Integer getFinalScore()
    {
        return finalScore;
    }

    public Integer getOpponentRosterId()
    {
        return opponentRosterId;
    }

    public String getOpponentClubName()
    {
        return opponentClubName;
    }

    public String getOpponentClubCode()
    {
        return opponentClubCode;
    }

    public Integer getOpponentTeamOrdinalNbr()
    {
        return opponentTeamOrdinalNbr;
    }

    public Integer getOpponentFinalScore()
    {
        return opponentFinalScore;
    }

    public Integer getRoundId()
    {
        return roundId;
    }

    public String getGroupCode()
    {
        return groupCode;
    }

    public CompetitionType getCompetitionType()
    {
        return competitionType;
    }

    public Boolean getInvalid()
    {
        return invalid;
    }

    public Boolean getWithdrawn()
    {
        return withdrawn;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public Boolean getIncognito()
    {
        return incognito;
    }

    public Integer getJerseyNbr()
    {
        return jerseyNbr;
    }

    public Boolean getHasPlayed()
    {
        return hasPlayed;
    }

    public Boolean getStarter()
    {
        return starter;
    }

    public Integer getFieldGoalsMade()
    {
        return fieldGoalsMade;
    }

    public Integer getTwoPointersMade()
    {
        return twoPointersMade;
    }

    public Integer getThreePointersMade()
    {
        return threePointersMade;
    }

    public Integer getFreeThrowsMade()
    {
        return freeThrowsMade;
    }

    public Integer getFreeThrowsAttempted()
    {
        return freeThrowsAttempted;
    }

    public Double getFreeThrowPercentage()
    {
        return freeThrowPercentage;
    }

    public Integer getPersonalFouls()
    {
        return personalFouls;
    }

    public Integer getPoints()
    {
        return points;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (gameId == null) ? 0 : gameId.hashCode() );
        result = prime * result + ( (home == null) ? 0 : home.hashCode() );
        result = prime * result + ( (playerId == null) ? 0 : playerId.hashCode() );
        result = prime * result + ( (rosterId == null) ? 0 : rosterId.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        GameLog other = ( GameLog ) obj;
        if ( gameId == null )
        {
            if ( other.gameId != null )
                return false;
        }
        else if ( !gameId.equals( other.gameId ) )
            return false;
        if ( home == null )
        {
            if ( other.home != null )
                return false;
        }
        else if ( !home.equals( other.home ) )
            return false;
        if ( playerId == null )
        {
            if ( other.playerId != null )
                return false;
        }
        else if ( !playerId.equals( other.playerId ) )
            return false;
        if ( rosterId == null )
        {
            if ( other.rosterId != null )
                return false;
        }
        else if ( !rosterId.equals( other.rosterId ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + gameId + ", " + home + ", " + playerId + ", " + rosterId + ", " + seasonStartYear + ", " + tipoff + ", " + scheduledTipoff + ", " + gameMatchdayNbr + ", " + gameOfficialNbr + ", " + clubName + ", " + clubCode + ", " + teamOrdinalNbr + ", " + finalScore + ", " + opponentRosterId + ", " + opponentClubName + ", " + opponentClubCode + ", " + opponentTeamOrdinalNbr + ", " + opponentFinalScore + ", " + roundId + ", " + groupCode + ", " + competitionType + ", " + invalid + ", " + withdrawn + ", " + lastName + ", " + firstName + ", " + incognito + ", " + jerseyNbr + ", " + hasPlayed + ", " + starter + ", " + fieldGoalsMade + ", " + twoPointersMade + ", " + threePointersMade + ", " + freeThrowsMade + ", " + freeThrowsAttempted + ", " + freeThrowPercentage + ", " + personalFouls + ", " + points + "]";
    }
}
