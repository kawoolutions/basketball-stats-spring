package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.framework.entity.BaseIdEntity;

@Entity
@Table(name = "\"Players\"")
@NamedQuery(name = Player.FIND_BY_SEARCH_TERM, query = "SELECT pl FROM Player pl JOIN pl.person pe WHERE pe.lastName LIKE :searchTerm OR pe.firstName LIKE :searchTerm ORDER BY pe.lastName, pe.firstName")
@NamedEntityGraph(name = Player.FETCH_TEAM_MEMBERS, attributeNodes = {@NamedAttributeNode("teamMembers")})
public class Player extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_SEARCH_TERM = "Player.findBySearchTerm";
    public static final String FETCH_TEAM_MEMBERS = "Player.fetchTeamMembers";
    public static final String FETCH_PERSON = "Player.fetchPerson";

    @Id
    @Column
    private Integer id;

    @Basic
    @Column(name = "registration_nbr")
    private String registrationNbr;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Person person;

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    private List<TeamMember> teamMembers;

    public Player()
    {
    }

    public Player(Player p)
    {
        this(p.getId(), p.getRegistrationNbr());
    }

    public Player(Integer id)
    {
        this(id, null);
    }

    public Player(String registrationNbr)
    {
        this(null, registrationNbr);
    }

    public Player(Integer id, String registrationNbr)
    {
        this.id = Objects.requireNonNull(id);
        this.registrationNbr = registrationNbr;

        this.person = new Person();
        this.person.setId(id);
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

    public String getRegistrationNbr()
    {
        return registrationNbr;
    }

    public void setRegistrationNbr(String registrationNbr)
    {
        this.registrationNbr = registrationNbr;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public List<TeamMember> getTeamMembers()
    {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers)
    {
        this.teamMembers = teamMembers;
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
        Player other = ( Player ) obj;
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
        return "[" + id + ", " + registrationNbr + "]";
    }
}
