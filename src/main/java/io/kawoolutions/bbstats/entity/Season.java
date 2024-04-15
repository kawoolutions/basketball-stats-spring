package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.framework.entity.BaseIdEntity;

@Entity
@Table(name = "\"Seasons\"")
@NamedQuery(name = Season.FIND_ALL, query = "SELECT se FROM Season se ORDER BY se.startYear DESC")
@NamedEntityGraph(name = Season.FETCH_ROUNDS_ROSTERS, attributeNodes = {@NamedAttributeNode("rounds"), @NamedAttributeNode("rosters")})
public class Season extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Season.findAll";
    public static final String FETCH_ROUNDS_ROSTERS = "Season.fetchRoundsRosters";

    @Id
    @Column(name = "start_year")
    private Integer startYear;

    @OneToMany(mappedBy = "season")
    @JsonIgnore
    private List<RefpoolMember> refpoolMembers;

    @OneToMany(mappedBy = "season")
    @JsonIgnore
    private Set<Roster> rosters;

    @OneToMany(mappedBy = "season")
    @JsonIgnore
    private Set<Round> rounds;

    public Season()
    {
    }

    public Season(Season s)
    {
        this(s.getStartYear());
    }

    public Season(Integer startYear)
    {
        this.startYear = Objects.requireNonNull(startYear);
    }

    @Override
    public Integer getPk()
    {
        return startYear;
    }

    @Override
    public void setPk(Integer pk)
    {
        this.startYear = pk;
    }

    @Override
    public Integer getId()
    {
        return startYear;
    }

    @Override
    public void setId(Integer id)
    {
        this.startYear = id;
    }

    public Integer getStartYear()
    {
        return startYear;
    }

    public void setStartYear(Integer startYear)
    {
        this.startYear = startYear;
    }

    public List<RefpoolMember> getRefpoolMembers()
    {
        return refpoolMembers;
    }

    public void setRefpoolMembers(List<RefpoolMember> refpoolMembers)
    {
        this.refpoolMembers = refpoolMembers;
    }

    public Set<Roster> getRosters()
    {
        return rosters;
    }

    public void setRosters(Set<Roster> rosters)
    {
        this.rosters = rosters;
    }

    public Set<Round> getRounds()
    {
        return rounds;
    }

    public void setRounds(Set<Round> rounds)
    {
        this.rounds = rounds;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (startYear == null) ? 0 : startYear.hashCode() );
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
        Season other = ( Season ) obj;
        if ( startYear == null )
        {
            if ( other.startYear != null )
                return false;
        }
        else if ( !startYear.equals( other.startYear ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + startYear + "]";
    }
}
