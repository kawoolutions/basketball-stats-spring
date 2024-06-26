package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Clubs")
@DiscriminatorValue("C")
@NamedQuery(name = Club.FIND_ALL, query = "SELECT cl FROM Club cl ORDER BY cl.code, cl.name")
@NamedEntityGraph(name = Club.FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS_TEAM_TYPES_AND_DISTRICT,
    attributeNodes = {
        @NamedAttributeNode("emailAddresses"),
        @NamedAttributeNode("phoneNumbers"),
        @NamedAttributeNode(value = "teams", subgraph = Team.FETCH_TEAM_TYPES_AND_ROSTERS),
        @NamedAttributeNode("district")},
    subgraphs = {
        @NamedSubgraph(name = Roster.FETCH_SEASON, attributeNodes = @NamedAttributeNode("season")),
        @NamedSubgraph(name = Team.FETCH_TEAM_TYPES_AND_ROSTERS, attributeNodes = {@NamedAttributeNode("teamType"), @NamedAttributeNode(value = "rosters", subgraph = Roster.FETCH_SEASON)})}
)
public class Club extends Contact
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Club.findAll";
    public static final String FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS_TEAM_TYPES_AND_DISTRICT = "Club.fetchEmailAddressesPhoneNumbersTeamTypesAndDistrict";

    @Basic(optional = false)
    @Column
    private String name;

    @Basic(optional = false)
    @Column
    private String code;

    @Basic
    @Column(name = "website_url")
    private String websiteUrl;

    @Basic
    @Column
    @Lob
    private byte[] logo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_name")
    @JsonIgnore
    private Color color;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private District district;

    @ManyToMany
    @JoinTable(name = "Tenancies", joinColumns = @JoinColumn(name = "club_id"), inverseJoinColumns = @JoinColumn(name = "arena_id"))
    @OrderColumn(name = "ordinal_nbr", nullable = false)
    @JsonIgnore
    private List<Arena> arenas;

    @ManyToMany
    @JoinTable(name = "Managers", joinColumns = @JoinColumn(name = "club_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    @MapKeyColumn(name = "role")
    @MapKeyEnumerated(EnumType.STRING)
    @JsonIgnore
    private Map<ManagerRole, Person> persons;

    @OneToMany(mappedBy = "ownerClub")
    @JsonIgnore
    private List<Assignment> ownerAssignments;

    @OneToMany(mappedBy = "refClub")
    @JsonIgnore
    private List<Game> refGames;

    @OneToMany(mappedBy = "club")
    @JsonIgnore
    private List<RefpoolMember> refpoolMembers;

    @OneToMany(mappedBy = "club")
    @JsonIgnore
    private Set<Team> teams;

    public Club()
    {
    }

    public Club(Club c)
    {
        this(c.getCountryCode(), c.getZipCode(), c.getCityName(), c.getStreetName(), c.getHouseNbr(), c.getLatitude(), c.getLongitude(), c.getDistrictId(), c.getColorName(), c.getName(), c.getCode(), c.getWebsiteUrl());

        this.logo = c.getLogo();
    }

    public Club(Integer districtId, String colorName)
    {
        this(null, null, null, null, null, null, null, districtId, colorName, null, null, null);
    }

    public Club(String name, String code)
    {
        this(null, name, code);
    }

    public Club(Integer districtId, String name, String code)
    {
        this(null, null, null, null, null, null, null, districtId, null, name, code, null);
    }

    public Club(String countryCode, String zipCode, String cityName, String streetName, String houseNbr, Double latitude, Double longitude, String name, String code, String websiteUrl)
    {
        this(countryCode, zipCode, cityName, streetName, houseNbr, latitude, longitude, null, null, name, code, websiteUrl);
    }

    public Club(String countryCode, String zipCode, String cityName, String streetName, String houseNbr, Double latitude, Double longitude, Integer districtId, String colorName, String name, String code, String websiteUrl)
    {
        super(countryCode, zipCode, cityName, streetName, houseNbr, latitude, longitude);

        this.name = name;
        this.code = code;
        this.websiteUrl = websiteUrl;

        if ( colorName != null )
        {
            this.color = new Color(colorName);
        }

        this.district = new District();
        this.district.setId(districtId);
    }

    public Integer getDistrictId()
    {
        return district.getId();
    }

    public void setDistrictId(Integer districtId)
    {
        district.setId(districtId);
    }

    public String getColorName()
    {
        return color.getName();
    }

    public void setColorName(String colorName)
    {
        color.setName(colorName);
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

    public String getWebsiteUrl()
    {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl)
    {
        this.websiteUrl = websiteUrl;
    }

    public byte[] getLogo()
    {
        return logo;
    }

    public void setLogo(byte[] logo)
    {
        this.logo = logo;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public District getDistrict()
    {
        return district;
    }

    public void setDistrict(District district)
    {
        this.district = district;
    }

    public List<Arena> getArenas()
    {
        return arenas;
    }

    public void setArenas(List<Arena> arenas)
    {
        this.arenas = arenas;
    }

    public Map<ManagerRole, Person> getPersons()
    {
        return persons;
    }

    public void setPersons(Map<ManagerRole, Person> persons)
    {
        this.persons = persons;
    }

    public List<Assignment> getOwnerAssignments()
    {
        return ownerAssignments;
    }

    public void setOwnerAssignments(List<Assignment> ownerAssignments)
    {
        this.ownerAssignments = ownerAssignments;
    }

    public List<Game> getRefGames()
    {
        return refGames;
    }

    public void setRefGames(List<Game> refGames)
    {
        this.refGames = refGames;
    }

    public List<RefpoolMember> getRefpoolMembers()
    {
        return refpoolMembers;
    }

    public void setRefpoolMembers(List<RefpoolMember> refpoolMembers)
    {
        this.refpoolMembers = refpoolMembers;
    }

    public Set<Team> getTeams()
    {
        return teams;
    }

    public void setTeams(Set<Team> teams)
    {
        this.teams = teams;
    }

    @Override
    public String toString()
    {
        return "[toString()=" + super.toString() + ", " + name + ", " + code + ", " + websiteUrl + "]";
    }
}
