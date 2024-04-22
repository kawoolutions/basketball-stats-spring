package io.kawoolutions.bbstats.entity;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.entity.base.BaseIdEntity;

@Entity
@Table(name = "Rounds")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class Round extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    public static final String FETCH_COMPETITION = "Round.fetchCompetition";
    public static final String FETCH_COMPETITION_LABELS = "Round.fetchCompetitionLabels";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Integer id;

    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    protected RoundType type = RoundType.RANKING_ROUND;

    @Basic
    @Column
    protected String nbr;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "geo_context_id", referencedColumnName = "geo_context_id")
    @JoinColumn(name = "team_type_code", referencedColumnName = "team_type_code")
    @JoinColumn(name = "competition_type", referencedColumnName = "type")
    @JoinColumn(name = "competition_level", referencedColumnName = "level")
    @JsonIgnore
    protected Competition competition;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "season_start_year")
    @JsonIgnore
    protected Season season;

    @OneToMany(mappedBy = "round")
    @JsonIgnore
    protected List<Group> groups;

    protected Round()
    {
    }

    protected Round(String nbr)
    {
        this(null, null, null, null, null, nbr);
    }

    protected Round(Integer geoContextId, String teamTypeCode, CompetitionType competitionType, Integer competitionLevel, Integer seasonStartYear)
    {
        this(geoContextId, teamTypeCode, competitionType, competitionLevel, seasonStartYear, null);
    }

    protected Round(Integer geoContextId, String teamTypeCode, CompetitionType competitionType, Integer competitionLevel, Integer seasonStartYear, String nbr)
    {
        this.nbr = nbr;

        this.competition = new Competition(geoContextId, teamTypeCode, competitionType, competitionLevel);
        this.season = new Season(seasonStartYear);
    }

    @Override
    public Integer getPk()
    {
        return id;
    }

    @Override
    public void setPk(Integer pk)
    {
        this.id = pk;
    }

    @Override
    public Integer getId()
    {
        return id;
    }

    @Override
    public void setId(Integer id)
    {
        this.id = id;
    }

    public RoundType getType()
    {
        return type;
    }

    public void setType(RoundType type)
    {
        this.type = type;
    }

    public Integer getGeoContextId()
    {
        return competition.getGeoContextId();
    }

    public void setGeoContextId(Integer geoContextId)
    {
        competition.setGeoContextId(geoContextId);
    }

    public String getTeamTypeCode()
    {
        return competition.getTeamTypeCode();
    }

    public void setTeamTypeCode(String teamTypeCode)
    {
        competition.setTeamTypeCode(teamTypeCode);
    }

    public CompetitionType getCompetitionType()
    {
        return competition.getType();
    }

    public void setCompetitionType(CompetitionType competitionType)
    {
        competition.setType(competitionType);
    }

    public Integer getCompetitionLevel()
    {
        return competition.getLevel();
    }

    public void setCompetitionLevel(Integer competitionLevel)
    {
        competition.setLevel(competitionLevel);
    }

    public Integer getSeasonStartYear()
    {
        return season.getStartYear();
    }

    public void setSeasonStartYear(Integer seasonStartYear)
    {
        season.setStartYear(seasonStartYear);
    }

    public String getNbr()
    {
        return nbr;
    }

    public void setNbr(String nbr)
    {
        this.nbr = nbr;
    }

    public Competition getCompetition()
    {
        return competition;
    }

    public void setCompetition(Competition competition)
    {
        this.competition = competition;
    }

    public Season getSeason()
    {
        return season;
    }

    public void setSeason(Season season)
    {
        this.season = season;
    }

    public List<Group> getGroups()
    {
        return groups;
    }

    public void setGroups(List<Group> groups)
    {
        this.groups = groups;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (id == null) ? 0 : id.hashCode() );
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
        Round other = ( Round ) obj;
        if ( id == null )
        {
            if ( other.id != null )
                return false;
        }
        else if ( !id.equals( other.id ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + id + ", " + type + ", " + nbr + "]";
    }
}
