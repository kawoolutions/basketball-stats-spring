package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

public class PlayerStatId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer gameId;

    private HomeAway scoreHomeAway;

    private Integer playerId;

    private Integer rosterId;

    public PlayerStatId()
    {
    }

    public PlayerStatId(PlayerStatId p)
    {
        this(p.getGameId(), p.getScoreHomeAway(), p.getPlayerId(), p.getRosterId());
    }

    public PlayerStatId(Integer gameId, HomeAway scoreHomeAway, Integer playerId, Integer rosterId)
    {
        this.gameId = Objects.requireNonNull(gameId);
        this.scoreHomeAway = Objects.requireNonNull(scoreHomeAway);
        this.playerId = Objects.requireNonNull(playerId);
        this.rosterId = Objects.requireNonNull(rosterId);
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
        PlayerStatId other = ( PlayerStatId ) obj;
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
        return "[" + gameId + ", " + scoreHomeAway + ", " + playerId + ", " + rosterId + "]";
    }
}
