package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

public class AssignmentId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer refereeId;

    private Integer clubId;

    private Integer seasonStartYear;

    private Integer gameId;

    public AssignmentId()
    {
    }

    public AssignmentId(AssignmentId a)
    {
        this(a.getRefereeId(), a.getClubId(), a.getSeasonStartYear(), a.getGameId());
    }

    public AssignmentId(Integer refereeId, Integer clubId, Integer seasonStartYear, Integer gameId)
    {
        this.refereeId = Objects.requireNonNull(refereeId);
        this.clubId = Objects.requireNonNull(clubId);
        this.seasonStartYear = Objects.requireNonNull(seasonStartYear);
        this.gameId = Objects.requireNonNull(gameId);
    }

    public Integer getRefereeId()
    {
        return refereeId;
    }

    public void setRefereeId(Integer refereeId)
    {
        this.refereeId = refereeId;
    }

    public Integer getClubId()
    {
        return clubId;
    }

    public void setClubId(Integer clubId)
    {
        this.clubId = clubId;
    }

    public Integer getSeasonStartYear()
    {
        return seasonStartYear;
    }

    public void setSeasonStartYear(Integer seasonStartYear)
    {
        this.seasonStartYear = seasonStartYear;
    }

    public Integer getGameId()
    {
        return gameId;
    }

    public void setGameId(Integer gameId)
    {
        this.gameId = gameId;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (clubId == null) ? 0 : clubId.hashCode() );
        result = prime * result + ( (gameId == null) ? 0 : gameId.hashCode() );
        result = prime * result + ( (refereeId == null) ? 0 : refereeId.hashCode() );
        result = prime * result + ( (seasonStartYear == null) ? 0 : seasonStartYear.hashCode() );
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
        AssignmentId other = ( AssignmentId ) obj;
        if ( clubId == null )
        {
            if ( other.clubId != null )
                return false;
        }
        else if ( !clubId.equals( other.clubId ) )
            return false;
        if ( gameId == null )
        {
            if ( other.gameId != null )
                return false;
        }
        else if ( !gameId.equals( other.gameId ) )
            return false;
        if ( refereeId == null )
        {
            if ( other.refereeId != null )
                return false;
        }
        else if ( !refereeId.equals( other.refereeId ) )
            return false;
        if ( seasonStartYear == null )
        {
            if ( other.seasonStartYear != null )
                return false;
        }
        else if ( !seasonStartYear.equals( other.seasonStartYear ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + refereeId + ", " + clubId + ", " + seasonStartYear + ", " + gameId + "]";
    }
}
