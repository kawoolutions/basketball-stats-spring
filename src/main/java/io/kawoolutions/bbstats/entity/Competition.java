package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"Competitions\"")
@IdClass(CompetitionId.class)
@NamedEntityGraph(name = Competition.FETCH_GEO_CONTEXT_TEAM_TYPE_ROUNDS_AND_COMPETITION_LABELS, attributeNodes = {
        @NamedAttributeNode("geoContext"),
        @NamedAttributeNode("teamType"),
        @NamedAttributeNode("rounds"),
        @NamedAttributeNode("competitionLabels")})
public class Competition extends BaseEntity<CompetitionId>
{
    private static final long serialVersionUID = 1L;

    public static final String FETCH_GEO_CONTEXT_TEAM_TYPE_ROUNDS_AND_COMPETITION_LABELS = "Competition.fetchGeoContextTeamTypeRoundsAndCompetitionLabels";
    public static final String FETCH_TEAM_TYPE = "Competition.fetchTeamType";
    public static final String FETCH_COMPETITION_LABELS = "Competition.fetchCompetitionLabels";

    @Id
    @Column(name = "geo_context_id")
    private Integer geoContextId;

    @Id
    @Column(name = "team_type_code")
    private String teamTypeCode;

    @Id
    @Column
    @Enumerated(EnumType.STRING)
    private CompetitionType type;

    @Id
    @Column
    private Integer level;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "geo_context_id", insertable = false, updatable = false)
    @JsonbTransient
    private GeoContext geoContext;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_type_code", insertable = false, updatable = false)
    @JsonbTransient
    private TeamType teamType;

    @OneToMany(mappedBy = "competition")
    @JsonbTransient
    private Set<CompetitionLabel> competitionLabels;

    @OneToMany(mappedBy = "competition")
    @JsonbTransient
    private List<Round> rounds;

    public Competition()
    {
    }

    public Competition(Competition c)
    {
        this(c.getGeoContextId(), c.getTeamTypeCode(), c.getType(), c.getLevel());
    }

    public Competition(Integer geoContextId, String teamTypeCode, CompetitionType type, Integer level)
    {
        this.geoContextId = Objects.requireNonNull(geoContextId);
        this.teamTypeCode = Objects.requireNonNull(teamTypeCode);
        this.type = Objects.requireNonNull(type);
        this.level = Objects.requireNonNull(level);
        // this.geoContext = new GeoContext(geoContextId);

        this.teamType = new TeamType(teamTypeCode);
    }

    @Override
    public CompetitionId getPk()
    {
        return new CompetitionId(geoContextId, teamTypeCode, type, level);
    }

    @Override
    public void setPk(CompetitionId pk)
    {
        this.geoContextId = pk.getGeoContextId();
        this.teamTypeCode = pk.getTeamTypeCode();
        this.type = pk.getType();
        this.level = pk.getLevel();
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

    public GeoContext getGeoContext()
    {
        return geoContext;
    }

    public void setGeoContext(GeoContext geoContext)
    {
        this.geoContext = geoContext;
    }

    public TeamType getTeamType()
    {
        return teamType;
    }

    public void setTeamType(TeamType teamType)
    {
        this.teamType = teamType;
    }

    public Set<CompetitionLabel> getCompetitionLabels()
    {
        return competitionLabels;
    }

    public void setCompetitionLabels(Set<CompetitionLabel> competitionLabels)
    {
        this.competitionLabels = competitionLabels;
    }

    public List<Round> getRounds()
    {
        return rounds;
    }

    public void setRounds(List<Round> rounds)
    {
        this.rounds = rounds;
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
        Competition other = ( Competition ) obj;
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
