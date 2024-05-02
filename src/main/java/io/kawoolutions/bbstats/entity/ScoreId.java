package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

public class ScoreId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer gameId;

    private HomeAway homeAway;

    public ScoreId()
    {
    }

    public ScoreId(ScoreId s)
    {
        this(s.getGameId(), s.getHomeAway());
    }

    public ScoreId(Integer gameId, HomeAway homeAway)
    {
        this.gameId = Objects.requireNonNull(gameId);
        this.homeAway = Objects.requireNonNull(homeAway);
    }

    public Integer getGameId()
    {
        return gameId;
    }

    public void setGameId(Integer gameId)
    {
        this.gameId = gameId;
    }

    public HomeAway getHomeAway()
    {
        return homeAway;
    }

    public void setHomeAway(HomeAway homeAway)
    {
        this.homeAway = homeAway;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (gameId == null) ? 0 : gameId.hashCode() );
        result = prime * result + ( (homeAway == null) ? 0 : homeAway.hashCode() );
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
        ScoreId other = ( ScoreId ) obj;
        if ( gameId == null )
        {
            if ( other.gameId != null )
                return false;
        }
        else if ( !gameId.equals( other.gameId ) )
            return false;
        if ( homeAway != other.homeAway )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + gameId + ", " + homeAway + "]";
    }
}
