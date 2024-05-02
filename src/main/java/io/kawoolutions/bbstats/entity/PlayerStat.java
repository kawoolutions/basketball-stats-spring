package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.entity.base.BaseEntity;

@Entity
@Table(name = "PlayerStats")
@IdClass(PlayerStatId.class)
@NamedQuery(name = PlayerStat.FIND_BY_INTERVAL_AND_TEAM, query = "SELECT ps FROM PlayerStat ps JOIN ps.teamMember tm JOIN tm.player pl JOIN pl.person pe JOIN ps.score sc JOIN sc.game ga JOIN sc.roster ro JOIN ro.team te JOIN te.teamType tt JOIN te.club cl WHERE CASE WHEN ga.actualTipoff IS NOT NULL THEN ga.actualTipoff ELSE ga.scheduledTipoff END >= :begin AND CASE WHEN ga.actualTipoff IS NOT NULL THEN ga.actualTipoff ELSE ga.scheduledTipoff END <= :end AND pe.id = :playerId AND cl.id = :clubId AND tt.code = :teamTypeCode AND te.ordinalNbr = :ordinalNbr ORDER BY CASE WHEN ga.actualTipoff IS NOT NULL THEN ga.actualTipoff ELSE ga.scheduledTipoff END DESC")
public class PlayerStat extends BaseEntity<PlayerStatId>
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_INTERVAL_AND_TEAM = "PlayerStat.findByIntervalAndTeam";
    public static final String FETCH_STATS = "PlayerStat.fetchStats";

    @Id
    @Column(name = "game_id")
    private Integer gameId;

    @Id
    @Column(name = "score_home_away")
    private HomeAway scoreHomeAway;

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Id
    @Column(name = "roster_id")
    private Integer rosterId;

    @Basic(optional = false)
    @Column(name = "jersey_nbr")
    private Integer jerseyNbr;

    @Basic(optional = false)
    @Column(name = "has_played")
    private Boolean hasPlayed = Boolean.TRUE;

    @Basic
    @Column(name = "is_starter")
    private Boolean starter;

    @Basic(optional = false)
    @Column
    private Integer pf;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", insertable = false, updatable = false)
    @JoinColumn(name = "score_home_away", referencedColumnName = "home_away", insertable = false, updatable = false)
    @JsonIgnore
    private Score score;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id", insertable = false, updatable = false)
    @JoinColumn(name = "roster_id", referencedColumnName = "roster_id", insertable = false, updatable = false)
    @JsonIgnore
    private TeamMember teamMember;

    @OneToMany(mappedBy = "playerStat")
    @OrderBy("period")
    @JsonIgnore
    private List<Stat> stats;

    public PlayerStat()
    {
    }

    public PlayerStat(PlayerStat p)
    {
        this(p.getGameId(), p.getScoreHomeAway(), p.getPlayerId(), p.getRosterId(), p.getJerseyNbr(), p.getStarter(), p.getPf());

        this.hasPlayed = p.getHasPlayed();
    }

    public PlayerStat(Integer jerseyNbr, Boolean starter, Integer pf)
    {
        this(null, null, null, null, jerseyNbr, starter, pf);
    }

    public PlayerStat(Integer gameId, HomeAway scoreHomeAway, Integer playerId, Integer rosterId)
    {
        this(gameId, scoreHomeAway, playerId, rosterId, null, null);
    }

    public PlayerStat(Integer gameId, HomeAway scoreHomeAway, Integer playerId, Integer rosterId, Integer jerseyNbr, Integer pf)
    {
        this(gameId, scoreHomeAway, playerId, rosterId, jerseyNbr, null, pf);
    }

    public PlayerStat(Integer gameId, HomeAway scoreHomeAway, Integer playerId, Integer rosterId, Integer jerseyNbr, Boolean starter, Integer pf)
    {
        this.gameId = Objects.requireNonNull(gameId);
        this.scoreHomeAway = Objects.requireNonNull(scoreHomeAway);
        this.playerId = Objects.requireNonNull(playerId);
        this.rosterId = Objects.requireNonNull(rosterId);
        this.jerseyNbr = jerseyNbr;
        this.starter = starter;
        this.pf = pf;

        this.score = new Score(gameId, scoreHomeAway);
        this.teamMember = new TeamMember(playerId, rosterId);
    }

    @Override
    public PlayerStatId getPk()
    {
        return new PlayerStatId(gameId, scoreHomeAway, playerId, rosterId);
    }

    @Override
    public void setPk(PlayerStatId pk)
    {
        this.gameId = pk.getGameId();
        this.scoreHomeAway = pk.getScoreHomeAway();
        this.playerId = pk.getPlayerId();
        this.rosterId = pk.getRosterId();
    }

    public Integer getGameId()
    {
        return gameId;
    }

    public void setGameId(Integer gameId)
    {
        this.gameId = gameId;
    }

    public HomeAway getScoreHomeAway()
    {
        return scoreHomeAway;
    }

    public void setScoreHomeAway(HomeAway scoreHomeAway)
    {
        this.scoreHomeAway = scoreHomeAway;
    }

    public Integer getPlayerId()
    {
        return playerId;
    }

    public void setPlayerId(Integer playerId)
    {
        this.playerId = playerId;
    }

    public Integer getRosterId()
    {
        return rosterId;
    }

    public void setRosterId(Integer rosterId)
    {
        this.rosterId = rosterId;
    }

    public Integer getJerseyNbr()
    {
        return jerseyNbr;
    }

    public void setJerseyNbr(Integer jerseyNbr)
    {
        this.jerseyNbr = jerseyNbr;
    }

    public Boolean getHasPlayed()
    {
        return hasPlayed;
    }

    public void setHasPlayed(Boolean hasPlayed)
    {
        this.hasPlayed = hasPlayed;
    }

    public Boolean getStarter()
    {
        return starter;
    }

    public void setStarter(Boolean starter)
    {
        this.starter = starter;
    }

    public Integer getPf()
    {
        return pf;
    }

    public void setPf(Integer pf)
    {
        this.pf = pf;
    }

    public Score getScore()
    {
        return score;
    }

    public void setScore(Score score)
    {
        this.score = score;
    }

    public TeamMember getTeamMember()
    {
        return teamMember;
    }

    public void setTeamMember(TeamMember teamMember)
    {
        this.teamMember = teamMember;
    }

    public List<Stat> getStats()
    {
        return stats;
    }

    public void setStats(List<Stat> stats)
    {
        this.stats = stats;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (gameId == null) ? 0 : gameId.hashCode() );
        result = prime * result + ( (playerId == null) ? 0 : playerId.hashCode() );
        result = prime * result + ( (rosterId == null) ? 0 : rosterId.hashCode() );
        result = prime * result + ( (scoreHomeAway == null) ? 0 : scoreHomeAway.hashCode() );
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
        PlayerStat other = ( PlayerStat ) obj;
        if ( gameId == null )
        {
            if ( other.gameId != null )
                return false;
        }
        else if ( !gameId.equals( other.gameId ) )
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
        if ( scoreHomeAway != other.scoreHomeAway )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + gameId + ", " + scoreHomeAway + ", " + playerId + ", " + rosterId + ", " + jerseyNbr + ", " + hasPlayed + ", " + starter + ", " + pf + "]";
    }
}
