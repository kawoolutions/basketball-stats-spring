package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

public class CompetitionLabelId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer geoContextId;

    private String teamTypeCode;

    private CompetitionType competitionType;

    private Integer competitionLevel;

    private Integer seasonStartYear = Integer.valueOf(1966);

    public CompetitionLabelId()
    {
    }

    public CompetitionLabelId(CompetitionLabelId c)
    {
        this(c.getGeoContextId(), c.getTeamTypeCode(), c.getCompetitionType(), c.getCompetitionLevel(), c.getSeasonStartYear());
    }

    public CompetitionLabelId(Integer geoContextId, String teamTypeCode, CompetitionType competitionType, Integer competitionLevel, Integer seasonStartYear)
    {
        this.geoContextId = Objects.requireNonNull(geoContextId);
        this.teamTypeCode = Objects.requireNonNull(teamTypeCode);
        this.competitionType = Objects.requireNonNull(competitionType);
        this.competitionLevel = Objects.requireNonNull(competitionLevel);
        this.seasonStartYear = Objects.requireNonNull(seasonStartYear);
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

    public CompetitionType getCompetitionType()
    {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType)
    {
        this.competitionType = competitionType;
    }

    public Integer getCompetitionLevel()
    {
        return competitionLevel;
    }

    public void setCompetitionLevel(Integer competitionLevel)
    {
        this.competitionLevel = competitionLevel;
    }

    public Integer getSeasonStartYear()
    {
        return seasonStartYear;
    }

    public void setSeasonStartYear(Integer seasonStartYear)
    {
        this.seasonStartYear = seasonStartYear;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (competitionLevel == null) ? 0 : competitionLevel.hashCode() );
        result = prime * result + ( (competitionType == null) ? 0 : competitionType.hashCode() );
        result = prime * result + ( (geoContextId == null) ? 0 : geoContextId.hashCode() );
        result = prime * result + ( (seasonStartYear == null) ? 0 : seasonStartYear.hashCode() );
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
        CompetitionLabelId other = ( CompetitionLabelId ) obj;
        if ( competitionLevel == null )
        {
            if ( other.competitionLevel != null )
                return false;
        }
        else if ( !competitionLevel.equals( other.competitionLevel ) )
            return false;
        if ( competitionType != other.competitionType )
            return false;
        if ( geoContextId == null )
        {
            if ( other.geoContextId != null )
                return false;
        }
        else if ( !geoContextId.equals( other.geoContextId ) )
            return false;
        if ( seasonStartYear == null )
        {
            if ( other.seasonStartYear != null )
                return false;
        }
        else if ( !seasonStartYear.equals( other.seasonStartYear ) )
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
        return "[" + geoContextId + ", " + teamTypeCode + ", " + competitionType + ", " + competitionLevel + ", " + seasonStartYear + "]";
    }
}
