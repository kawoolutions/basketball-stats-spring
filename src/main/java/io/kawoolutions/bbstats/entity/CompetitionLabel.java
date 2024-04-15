package io.kawoolutions.bbstats.entity;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"CompetitionLabels\"")
@IdClass(CompetitionLabelId.class)
@NamedQuery(name = CompetitionLabel.FIND_BY_COMPETITION_AND_SEASON,
            query = "SELECT cml " +
                    "FROM CompetitionLabel cml " +
                    "  JOIN cml.competition cm " +
                    "  JOIN cm.geoContext gc " +
                    "  JOIN cm.teamType tt " +
                    "WHERE gc.id = :geoContextId " +
                    "  AND tt.code = :teamTypeCode " +
                    "  AND cm.type = :competitionType " +
                    "  AND cm.level = :competitionLevel " +
                    "  AND cml.seasonStartYear = (SELECT MAX(cml.seasonStartYear) " +
                    "                            FROM CompetitionLabel cml " +
                    "                            JOIN cml.competition cm " +
                    "                            JOIN cm.geoContext gc " +
                    "                            JOIN cm.teamType tt " +
                    "                            WHERE gc.id = :geoContextId " +
                    "                              AND tt.code = :teamTypeCode " +
                    "                              AND cm.type = :competitionType " +
                    "                              AND cm.level = :competitionLevel " +
                    "                              AND cml.seasonStartYear <= :seasonStartYear)")
public class CompetitionLabel extends BaseEntity<CompetitionLabelId>
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_COMPETITION_AND_SEASON = "CompetitionLabel.findByCompetitionAndSeason";

    @Id
    @Column(name = "geo_context_id")
    private Integer geoContextId;

    @Id
    @Column(name = "team_type_code")
    private String teamTypeCode;

    @Id
    @Column(name = "competition_type")
    @Enumerated(EnumType.STRING)
    private CompetitionType competitionType;

    @Id
    @Column(name = "competition_level")
    private Integer competitionLevel;

    @Id
    @Column(name = "season_start_year")
    private Integer seasonStartYear;

    @Basic(optional = false)
    @Column
    private String name;

    @Basic
    @Column
    private String code;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "geo_context_id", referencedColumnName = "geo_context_id", insertable = false, updatable = false)
    @JoinColumn(name = "team_type_code", referencedColumnName = "team_type_code", insertable = false, updatable = false)
    @JoinColumn(name = "competition_type", referencedColumnName = "type", insertable = false, updatable = false)
    @JoinColumn(name = "competition_level", referencedColumnName = "level", insertable = false, updatable = false)
    @JsonIgnore
    private Competition competition;

    public CompetitionLabel()
    {
    }

    public CompetitionLabel(CompetitionLabel c)
    {
        this(c.getGeoContextId(), c.getTeamTypeCode(), c.getCompetitionType(), c.getCompetitionLevel(), c.getSeasonStartYear(), c.getName(), c.getCode());
    }

    public CompetitionLabel(String name, String code)
    {
        this(null, null, null, null, Integer.valueOf(1966), name, code);
    }

    public CompetitionLabel(Integer geoContextId, String teamTypeCode, CompetitionType competitionType, Integer competitionLevel, Integer seasonStartYear)
    {
        this(geoContextId, teamTypeCode, competitionType, competitionLevel, seasonStartYear, null);
    }

    public CompetitionLabel(Integer geoContextId, String teamTypeCode, CompetitionType competitionType, Integer competitionLevel, Integer seasonStartYear, String name)
    {
        this(geoContextId, teamTypeCode, competitionType, competitionLevel, seasonStartYear, name, null);
    }

    public CompetitionLabel(Integer geoContextId, String teamTypeCode, CompetitionType competitionType, Integer competitionLevel, Integer seasonStartYear, String name, String code)
    {
        this.geoContextId = Objects.requireNonNull(geoContextId);
        this.teamTypeCode = Objects.requireNonNull(teamTypeCode);
        this.competitionType = Objects.requireNonNull(competitionType);
        this.competitionLevel = Objects.requireNonNull(competitionLevel);
        this.seasonStartYear = Objects.requireNonNull(seasonStartYear);
        this.name = name;
        this.code = code;

        this.competition = new Competition(geoContextId, teamTypeCode, competitionType, competitionLevel);
    }

    @Override
    public CompetitionLabelId getPk()
    {
        return new CompetitionLabelId(geoContextId, teamTypeCode, competitionType, competitionLevel, seasonStartYear);
    }

    @Override
    public void setPk(CompetitionLabelId pk)
    {
        this.geoContextId = pk.getGeoContextId();
        this.teamTypeCode = pk.getTeamTypeCode();
        this.competitionType = pk.getCompetitionType();
        this.competitionLevel = pk.getCompetitionLevel();
        this.seasonStartYear = pk.getSeasonStartYear();
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Competition getCompetition()
    {
        return competition;
    }

    public void setCompetition(Competition competition)
    {
        this.competition = competition;
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
        CompetitionLabel other = ( CompetitionLabel ) obj;
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
        return "[" + geoContextId + ", " + teamTypeCode + ", " + competitionType + ", " + competitionLevel + ", " + seasonStartYear + ", " + name + ", " + code + "]";
    }
}
