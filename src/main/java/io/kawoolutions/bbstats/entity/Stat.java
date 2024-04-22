package io.kawoolutions.bbstats.entity;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.entity.base.BaseEntity;

@Entity
@Table(name = "Stats")
@IdClass(StatId.class)
public class Stat extends BaseEntity<StatId>
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

    @Id
    @Column
    private Integer period;

    @Basic(optional = false)
    @Column
    private Integer tpm;

    @Basic(optional = false)
    @Column
    private Integer ftm;

    @Basic(optional = false)
    @Column
    private Integer fta;

    @Basic(optional = false)
    @Column
    private Integer pts;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", insertable = false, updatable = false)
    @JoinColumn(name = "is_home", referencedColumnName = "is_home", insertable = false, updatable = false)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id", insertable = false, updatable = false)
    @JoinColumn(name = "roster_id", referencedColumnName = "roster_id", insertable = false, updatable = false)
    @JsonIgnore
    private PlayerStat playerStat;

    public Stat()
    {
    }

    public Stat(Stat s)
    {
        this(s.getGameId(), s.getHome(), s.getPlayerId(), s.getRosterId(), s.getPeriod(), s.getTpm(), s.getFtm(), s.getFta(), s.getPts());
    }

    public Stat(Integer tpm, Integer ftm, Integer fta, Integer pts)
    {
        this(null, null, null, null, null, tpm, ftm, fta, pts);
    }

    public Stat(Integer gameId, Boolean home, Integer playerId, Integer rosterId, Integer period)
    {
        this(gameId, home, playerId, rosterId, period, null, null, null, null);
    }

    public Stat(Integer gameId, Boolean home, Integer playerId, Integer rosterId, Integer period, Integer tpm, Integer ftm, Integer fta, Integer pts)
    {
        this.gameId = Objects.requireNonNull(gameId);
        this.home = Objects.requireNonNull(home);
        this.playerId = Objects.requireNonNull(playerId);
        this.rosterId = Objects.requireNonNull(rosterId);
        this.period = Objects.requireNonNull(period);
        this.tpm = tpm;
        this.ftm = ftm;
        this.fta = fta;
        this.pts = pts;

        this.playerStat = new PlayerStat(gameId, home, playerId, rosterId);
    }

    @Override
    public StatId getPk()
    {
        return new StatId(gameId, home, playerId, rosterId, period);
    }

    @Override
    public void setPk(StatId pk)
    {
        this.gameId = pk.getGameId();
        this.home = pk.getHome();
        this.playerId = pk.getPlayerId();
        this.rosterId = pk.getRosterId();
        this.period = pk.getPeriod();
    }

    public Integer getGameId()
    {
        return gameId;
    }

    public void setGameId(Integer gameId)
    {
        this.gameId = gameId;
    }

    public Boolean getHome()
    {
        return home;
    }

    public void setHome(Boolean home)
    {
        this.home = home;
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

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public Integer getTpm()
    {
        return tpm;
    }

    public void setTpm(Integer tpm)
    {
        this.tpm = tpm;
    }

    public Integer getFtm()
    {
        return ftm;
    }

    public void setFtm(Integer ftm)
    {
        this.ftm = ftm;
    }

    public Integer getFta()
    {
        return fta;
    }

    public void setFta(Integer fta)
    {
        this.fta = fta;
    }

    public Integer getPts()
    {
        return pts;
    }

    public void setPts(Integer pts)
    {
        this.pts = pts;
    }

    public PlayerStat getPlayerStat()
    {
        return playerStat;
    }

    public void setPlayerStat(PlayerStat playerStat)
    {
        this.playerStat = playerStat;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (gameId == null) ? 0 : gameId.hashCode() );
        result = prime * result + ( (home == null) ? 0 : home.hashCode() );
        result = prime * result + ( (period == null) ? 0 : period.hashCode() );
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
        Stat other = ( Stat ) obj;
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
        if ( period == null )
        {
            if ( other.period != null )
                return false;
        }
        else if ( !period.equals( other.period ) )
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
        return "[" + gameId + ", " + home + ", " + playerId + ", " + rosterId + ", " + period + ", " + tpm + ", " + ftm + ", " + fta + ", " + pts + "]";
    }
}
