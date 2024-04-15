package io.kawoolutions.bbstats.entity;

import java.time.LocalDate;
import java.util.List;
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
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"TeamMembers\"")
@IdClass(TeamMemberId.class)
@NamedQuery(name = TeamMember.FIND_BY_ROSTER, query = "SELECT tm FROM TeamMember tm JOIN tm.player pl JOIN pl.person pe JOIN tm.roster ro WHERE ro.id = :rosterId ORDER BY pe.lastName, pe.firstName")
@NamedEntityGraph(name = TeamMember.FETCH_PERSON_PLAYER_STATS,
    attributeNodes = {
        @NamedAttributeNode(value = "player", subgraph = Player.FETCH_PERSON),
        @NamedAttributeNode("playerStats")},
    subgraphs = {
        @NamedSubgraph(name = Player.FETCH_PERSON, attributeNodes = @NamedAttributeNode(value = "person", subgraph = Person.FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS)),
        @NamedSubgraph(name = Person.FETCH_EMAIL_ADDRESSES_PHONE_NUMBERS, attributeNodes = {@NamedAttributeNode("emailAddresses"), @NamedAttributeNode("phoneNumbers")})}
)
@NamedEntityGraph(name = TeamMember.FETCH_PERSON,
                  attributeNodes = @NamedAttributeNode(value = "player", subgraph = Player.FETCH_PERSON),
                  subgraphs = @NamedSubgraph(name = Player.FETCH_PERSON, attributeNodes = @NamedAttributeNode("person"))
)
public class TeamMember extends BaseEntity<TeamMemberId>
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ROSTER = "TeamMember.findByRoster";
    public static final String FETCH_PERSON_PLAYER_STATS = "TeamMember.fetchPersonPlayerStats";
    public static final String FETCH_PERSON = "TeamMember.fetchPerson";

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Id
    @Column(name = "roster_id")
    private Integer rosterId;

    @Basic
    @Column
    @Enumerated(EnumType.STRING)
    private Position position;

    @Basic
    @Column(name = "eligible_to_play_since")
    private LocalDate eligibleToPlaySince;

    @Basic
    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", insertable = false, updatable = false)
    @JsonIgnore
    private Player player;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "roster_id", insertable = false, updatable = false)
    @JsonIgnore
    private Roster roster;

    @OneToMany(mappedBy = "teamMember")
    @JsonIgnore
    private List<PlayerStat> playerStats;

    public TeamMember()
    {
    }

    public TeamMember(TeamMember t)
    {
        this(t.getPlayerId(), t.getRosterId(), t.getPosition(), t.getEligibleToPlaySince(), t.getImagePath());
    }

    public TeamMember(Integer playerId, Integer rosterId)
    {
        this(playerId, rosterId, null, null, null);
    }

    public TeamMember(Position position, LocalDate eligibleToPlaySince, String imagePath)
    {
        this(null, null, position, eligibleToPlaySince, imagePath);
    }

    public TeamMember(Integer playerId, Integer rosterId, Position position, LocalDate eligibleToPlaySince, String imagePath)
    {
        this.playerId = Objects.requireNonNull(playerId);
        this.rosterId = Objects.requireNonNull(rosterId);
        this.position = position;
        this.eligibleToPlaySince = eligibleToPlaySince;
        this.imagePath = imagePath;

        this.player = new Player(playerId);

        this.roster = new Roster();
        this.roster.setId(rosterId);
    }

    @Override
    public TeamMemberId getPk()
    {
        return new TeamMemberId(playerId, rosterId);
    }

    @Override
    public void setPk(TeamMemberId pk)
    {
        this.playerId = pk.getPlayerId();
        this.rosterId = pk.getRosterId();
    }

    public Integer getPlayerId()
    {
        return playerId;
    }

    public void setPlayerId(Integer playerId)
    {
        this.playerId = playerId;
    }

    public Integer getRosterId()
    {
        return rosterId;
    }

    public void setRosterId(Integer rosterId)
    {
        this.rosterId = rosterId;
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public LocalDate getEligibleToPlaySince()
    {
        return eligibleToPlaySince;
    }

    public void setEligibleToPlaySince(LocalDate eligibleToPlaySince)
    {
        this.eligibleToPlaySince = eligibleToPlaySince;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Roster getRoster()
    {
        return roster;
    }

    public void setRoster(Roster roster)
    {
        this.roster = roster;
    }

    public List<PlayerStat> getPlayerStats()
    {
        return playerStats;
    }

    public void setPlayerStats(List<PlayerStat> playerStats)
    {
        this.playerStats = playerStats;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (playerId == null) ? 0 : playerId.hashCode() );
        result = prime * result + ( (rosterId == null) ? 0 : rosterId.hashCode() );
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
        TeamMember other = ( TeamMember ) obj;
        if ( playerId == null )
        {
            if ( other.playerId != null )
                return false;
        }
        else if ( !playerId.equals( other.playerId ) )
            return false;
        if ( rosterId == null )
        {
            if ( other.rosterId != null )
                return false;
        }
        else if ( !rosterId.equals( other.rosterId ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + playerId + ", " + rosterId + ", " + position + ", " + eligibleToPlaySince + ", " + imagePath + "]";
    }
}
