package io.kawoolutions.bbstats.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Persons")
@DiscriminatorValue("P")
@NamedQuery(name = Person.FIND_ALL, query = "SELECT pe FROM Person pe ORDER BY pe.lastName, pe.firstName")
@NamedQuery(name = Person.FIND_UNLINKED_BY_SEARCH_TERM, query = "SELECT pe FROM Person pe LEFT JOIN FETCH pe.user us WHERE us IS NULL AND (pe.lastName LIKE :searchTerm OR pe.firstName LIKE :searchTerm) ORDER BY pe.lastName, pe.firstName")
@NamedEntityGraph(name = Person.FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS_AND_ROLES, attributeNodes = {
        @NamedAttributeNode("emailAddresses"),
        @NamedAttributeNode("phoneNumbers"),
        @NamedAttributeNode("player"),
        @NamedAttributeNode("coach"),
        @NamedAttributeNode("referee")})
@NamedEntityGraph(name = Person.FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS_TEAM_STAFF_AND_REFPOOL_MEMBERS,
    attributeNodes = {
        @NamedAttributeNode("emailAddresses"),
        @NamedAttributeNode("phoneNumbers"),
        @NamedAttributeNode(value = "player", subgraph = Player.FETCH_TEAM_MEMBERS),
        @NamedAttributeNode(value = "coach", subgraph = Coach.FETCH_STAFF_MEMBERS),
        @NamedAttributeNode(value = "referee", subgraph = Referee.FETCH_REFPOOL_MEMBERS)},
    subgraphs = {
        @NamedSubgraph(name = Player.FETCH_TEAM_MEMBERS, attributeNodes = @NamedAttributeNode("teamMembers")),
        @NamedSubgraph(name = Coach.FETCH_STAFF_MEMBERS, attributeNodes = @NamedAttributeNode("staffMembers")),
        @NamedSubgraph(name = Referee.FETCH_REFPOOL_MEMBERS, attributeNodes = @NamedAttributeNode("refpoolMembers"))}
)
public class Person extends Contact
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Person.findAll";
    public static final String FIND_UNLINKED_BY_SEARCH_TERM = "Person.findUnlinkedBySearchTerm";
    public static final String FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS_AND_ROLES = "Person.fetchEmailAddressesPhoneNumbersAndRoles";
    public static final String FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS_TEAM_STAFF_AND_REFPOOL_MEMBERS = "Person.fetchEmailAddressesPhoneNumbersTeamStaffAndRefpoolMembers";
    public static final String FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS = "Person.fetchEmailAddressesPhoneNumbers";

    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;

    @Basic(optional = false)
    @Column
    @Enumerated(EnumType.STRING)
    private PersonGender gender = PersonGender.MALE;

    @Basic
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Basic(optional = false)
    @Column(name = "is_incognito")
    private Boolean incognito = Boolean.FALSE;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private Coach coach;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private Player player;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private Referee referee;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(mappedBy = "persons")
    @OrderBy("name")
    @JsonIgnore
    private List<Club> clubs;

    public Person()
    {
    }

    public Person(Person p)
    {
        this(p.getCountryCode(), p.getZipCode(), p.getCityName(), p.getStreetName(), p.getHouseNbr(), p.getLatitude(), p.getLongitude(), p.getFirstName(), p.getLastName(), p.getBirthDate());

        this.gender = p.getGender();
        this.incognito = p.getIncognito();
    }

    public Person(String firstName, String lastName)
    {
        this(null, null, null, null, null, null, null, firstName, lastName, null);
    }

    public Person(String countryCode, String zipCode, String cityName, String streetName, String houseNbr, Double latitude, Double longitude, String firstName, String lastName, LocalDate birthDate)
    {
        super(countryCode, zipCode, cityName, streetName, houseNbr, latitude, longitude);

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public PersonGender getGender()
    {
        return gender;
    }

    public void setGender(PersonGender gender)
    {
        this.gender = gender;
    }

    public LocalDate getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate)
    {
        this.birthDate = birthDate;
    }

    public Boolean getIncognito()
    {
        return incognito;
    }

    public void setIncognito(Boolean incognito)
    {
        this.incognito = incognito;
    }

    public Coach getCoach()
    {
        return coach;
    }

    public void setCoach(Coach coach)
    {
        this.coach = coach;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Referee getReferee()
    {
        return referee;
    }

    public void setReferee(Referee referee)
    {
        this.referee = referee;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
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
        return "[toString()=" + super.toString() + ", " + firstName + ", " + lastName + ", " + gender + ", " + birthDate + ", " + incognito + "]";
    }
}
