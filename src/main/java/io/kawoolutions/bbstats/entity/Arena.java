package io.kawoolutions.bbstats.entity;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "\"Arenas\"")
@DiscriminatorValue("A")
@NamedQuery(name = Arena.FIND_ALL, query = "SELECT ar FROM Arena ar ORDER BY ar.name")
@NamedQuery(name = Arena.FIND_BY_PK, query = "SELECT ar FROM Arena ar WHERE ar.id = :arenaId")
@NamedQuery(name = Arena.FIND_BY_CLUB, query = "SELECT ar FROM Arena ar JOIN ar.clubs cl WHERE cl.id = :clubId")
@NamedNativeQuery(name = Arena.FIND_BY_CLUB_SORTED,
                  query = "SELECT ad.*, co.*, ar.* FROM Arenas ar JOIN Contacts co ON ar.id = co.id JOIN Addresses ad ON co.id = ad.contact_id JOIN Tenancies te ON ar.id = te.arena_id WHERE te.club_id = ?1 ORDER BY te.ordinal_nbr;",
                  resultClass = Arena.class
)
@NamedEntityGraph(name = Arena.FETCH_CLUBS, attributeNodes = @NamedAttributeNode("clubs"))
public class Arena extends Contact
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Arena.findAll";
    public static final String FIND_BY_PK = "Arena.findByPk";
    public static final String FIND_BY_CLUB = "Arena.findByClub";
    public static final String FIND_BY_CLUB_SORTED = "Arena.findByClubSorted";
    public static final String FETCH_CLUBS = "Arena.fetchClubs";

    @Basic(optional = false)
    @Column
    private String name;

    @Basic
    @Column
    private Integer capacity;

    @OneToMany(mappedBy = "arena")
    @JsonIgnore
    private List<Game> games;

    @ManyToMany(mappedBy = "arenas")
    @OrderBy
    @JsonIgnore
    private List<Club> clubs;

    public Arena()
    {
    }

    public Arena(Arena a)
    {
        this(a.getCountryCode(), a.getZipCode(), a.getCityName(), a.getStreetName(), a.getHouseNbr(), a.getLatitude(), a.getLongitude(), a.getName(), a.getCapacity());
    }

    public Arena(String name)
    {
        this(null, null, null, null, null, null, null, name, null);
    }

    public Arena(String countryCode, String zipCode, String cityName, String streetName, String houseNbr, Double latitude, Double longitude, String name, Integer capacity)
    {
        super(countryCode, zipCode, cityName, streetName, houseNbr, latitude, longitude);

        this.name = name;
        this.capacity = capacity;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getCapacity()
    {
        return capacity;
    }

    public void setCapacity(Integer capacity)
    {
        this.capacity = capacity;
    }

    public List<Game> getGames()
    {
        return games;
    }

    public void setGames(List<Game> games)
    {
        this.games = games;
    }

    public List<Club> getClubs()
    {
        return clubs;
    }

    public void setClubs(List<Club> clubs)
    {
        this.clubs = clubs;
    }

    @Override
    public String toString()
    {
        return "[toString()=" + super.toString() + ", " + name + ", " + capacity + "]";
    }
}
