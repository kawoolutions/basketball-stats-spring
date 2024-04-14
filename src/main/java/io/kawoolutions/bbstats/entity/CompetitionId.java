package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

public class CompetitionId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer geoContextId;

    private String teamTypeCode;

    private CompetitionType type;

    private Integer level = Integer.valueOf(1);

    public CompetitionId()
    {
    }

    public CompetitionId(CompetitionId c)
    {
        this(c.getGeoContextId(), c.getTeamTypeCode(), c.getType(), c.getLevel());
    }

    public CompetitionId(Integer geoContextId, String teamTypeCode, CompetitionType type, Integer level)
    {
        this.geoContextId = Objects.requireNonNull(geoContextId);
        this.teamTypeCode = Objects.requireNonNull(teamTypeCode);
        this.type = Objects.requireNonNull(type);
        this.level = Objects.requireNonNull(level);
    }

    public Integer getGeoContextId()
    {
        return geoContextId;
    }

    public void setGeoContextId(Integer geoContextId)
    {
        this.geoContextId = geoContextId;
    }

    public String getTeamTypeCode()
    {
        return teamTypeCode;
    }

    public void setTeamTypeCode(String teamTypeCode)
    {
        this.teamTypeCode = teamTypeCode;
    }

    public CompetitionType getType()
    {
        return type;
    }

    public void setType(CompetitionType type)
    {
        this.type = type;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (geoContextId == null) ? 0 : geoContextId.hashCode() );
        result = prime * result + ( (level == null) ? 0 : level.hashCode() );
        result = prime * result + ( (teamTypeCode == null) ? 0 : teamTypeCode.hashCode() );
        result = prime * result + ( (type == null) ? 0 : type.hashCode() );
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
        CompetitionId other = ( CompetitionId ) obj;
        if ( geoContextId == null )
        {
            if ( other.geoContextId != null )
                return false;
        }
        else if ( !geoContextId.equals( other.geoContextId ) )
            return false;
        if ( level == null )
        {
            if ( other.level != null )
                return false;
        }
        else if ( !level.equals( other.level ) )
            return false;
        if ( teamTypeCode == null )
        {
            if ( other.teamTypeCode != null )
                return false;
        }
        else if ( !teamTypeCode.equals( other.teamTypeCode ) )
            return false;
        if ( type != other.type )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + geoContextId + ", " + teamTypeCode + ", " + type + ", " + level + "]";
    }
}
