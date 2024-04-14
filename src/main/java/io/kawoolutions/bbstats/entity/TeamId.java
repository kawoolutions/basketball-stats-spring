package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

public class TeamId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer clubId;

    private String teamTypeCode;

    private Integer ordinalNbr;

    public TeamId()
    {
    }

    public TeamId(TeamId t)
    {
        this(t.getClubId(), t.getTeamTypeCode(), t.getOrdinalNbr());
    }

    public TeamId(Integer clubId, String teamTypeCode, Integer ordinalNbr)
    {
        this.clubId = Objects.requireNonNull(clubId);
        this.teamTypeCode = Objects.requireNonNull(teamTypeCode);
        this.ordinalNbr = Objects.requireNonNull(ordinalNbr);
    }

    public Integer getClubId()
    {
        return clubId;
    }

    public void setClubId(Integer clubId)
    {
        this.clubId = clubId;
    }

    public String getTeamTypeCode()
    {
        return teamTypeCode;
    }

    public void setTeamTypeCode(String teamTypeCode)
    {
        this.teamTypeCode = teamTypeCode;
    }

    public Integer getOrdinalNbr()
    {
        return ordinalNbr;
    }

    public void setOrdinalNbr(Integer ordinalNbr)
    {
        this.ordinalNbr = ordinalNbr;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (clubId == null) ? 0 : clubId.hashCode() );
        result = prime * result + ( (ordinalNbr == null) ? 0 : ordinalNbr.hashCode() );
        result = prime * result + ( (teamTypeCode == null) ? 0 : teamTypeCode.hashCode() );
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
        TeamId other = ( TeamId ) obj;
        if ( clubId == null )
        {
            if ( other.clubId != null )
                return false;
        }
        else if ( !clubId.equals( other.clubId ) )
            return false;
        if ( ordinalNbr == null )
        {
            if ( other.ordinalNbr != null )
                return false;
        }
        else if ( !ordinalNbr.equals( other.ordinalNbr ) )
            return false;
        if ( teamTypeCode == null )
        {
            if ( other.teamTypeCode != null )
                return false;
        }
        else if ( !teamTypeCode.equals( other.teamTypeCode ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + clubId + ", " + teamTypeCode + ", " + ordinalNbr + "]";
    }
}
