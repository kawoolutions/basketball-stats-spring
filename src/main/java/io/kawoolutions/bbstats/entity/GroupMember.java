package io.kawoolutions.bbstats.entity;

import java.util.Objects;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"GroupMembers\"")
@IdClass(GroupMemberId.class)
@NamedQuery(name = GroupMember.FIND_BY_ROSTER, query = "SELECT gm FROM GroupMember gm JOIN gm.group gr JOIN gr.round rd JOIN rd.competition cm JOIN cm.geoContext gc JOIN gm.roster ro WHERE ro.id = :rosterId ORDER BY cm.type, gc.type, cm.level")
@NamedQuery(name = GroupMember.FIND_BY_GROUP, query = "SELECT DISTINCT gm FROM GroupMember gm JOIN gm.roster ro JOIN ro.team te JOIN te.club cl JOIN gm.group gr JOIN gr.groupLabel gl JOIN gr.round rd WHERE rd.id = :roundId AND gl.code = :groupCode")
@NamedEntityGraph(name = GroupMember.FETCH_ROSTER_TEAM_MEMBERS,
    attributeNodes = @NamedAttributeNode(value = "roster", subgraph = Roster.FETCH_TEAM_MEMBERS_CLUB_COLORS),
    subgraphs = {
        @NamedSubgraph(name = Roster.FETCH_TEAM_MEMBERS_CLUB_COLORS, attributeNodes = {@NamedAttributeNode("teamMembers"), @NamedAttributeNode(value = "team", subgraph = Team.FETCH_CLUB), @NamedAttributeNode("primaryJerseyColor"), @NamedAttributeNode("secondaryJerseyColor")}),
        @NamedSubgraph(name = Team.FETCH_CLUB, attributeNodes = {@NamedAttributeNode("club")})}
)
public class GroupMember extends BaseEntity<GroupMemberId>
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_ROSTER = "GroupMember.findByRoster";
    public static final String FIND_BY_GROUP = "GroupMember.findByGroup";
    public static final String FETCH_ROSTER_TEAM_MEMBERS = "GroupMember.fetchRosterTeamMembers";

    @Id
    @Column(name = "roster_id")
    private Integer rosterId;

    @Id
    @Column(name = "round_id")
    private Integer roundId;

    @Id
    @Column(name = "group_code")
    private String groupCode;

    @Basic(optional = false)
    @Column
    private Boolean withdrawn;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "roster_id", insertable = false, updatable = false)
    @JsonbTransient
    private Roster roster;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "round_id", referencedColumnName = "round_id", insertable = false, updatable = false)
    @JoinColumn(name = "group_code", referencedColumnName = "code", insertable = false, updatable = false)
    @JsonbTransient
    private Group group;

    public GroupMember()
    {
    }

    public GroupMember(GroupMember g)
    {
        this(g.getRosterId(), g.getRoundId(), g.getGroupCode(), g.getWithdrawn());
    }

    public GroupMember(Boolean withdrawn)
    {
        this(null, null, null, withdrawn);
    }

    public GroupMember(Integer rosterId, Integer roundId, String groupCode)
    {
        this(rosterId, roundId, groupCode, null);
    }

    public GroupMember(Integer rosterId, Integer roundId, String groupCode, Boolean withdrawn)
    {
        this.rosterId = Objects.requireNonNull(rosterId);
        this.roundId = Objects.requireNonNull(roundId);
        this.groupCode = Objects.requireNonNull(groupCode);
        this.withdrawn = withdrawn;

        this.roster = new Roster();
        this.roster.setId(rosterId);

        this.group = new Group(roundId, groupCode);
    }

    @Override
    public GroupMemberId getPk()
    {
        return new GroupMemberId(rosterId, roundId, groupCode);
    }

    @Override
    public void setPk(GroupMemberId pk)
    {
        this.rosterId = pk.getRosterId();
        this.roundId = pk.getRoundId();
        this.groupCode = pk.getGroupCode();
    }

    public Integer getRosterId()
    {
        return rosterId;
    }

    public void setRosterId(Integer rosterId)
    {
        this.rosterId = rosterId;
    }

    public Integer getRoundId()
    {
        return roundId;
    }

    public void setRoundId(Integer roundId)
    {
        this.roundId = roundId;
    }

    public String getGroupCode()
    {
        return groupCode;
    }

    public void setGroupCode(String groupCode)
    {
        this.groupCode = groupCode;
    }

    public Boolean getWithdrawn()
    {
        return withdrawn;
    }

    public void setWithdrawn(Boolean withdrawn)
    {
        this.withdrawn = withdrawn;
    }

    public Roster getRoster()
    {
        return roster;
    }

    public void setRoster(Roster roster)
    {
        this.roster = roster;
    }

    public Group getGroup()
    {
        return group;
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (groupCode == null) ? 0 : groupCode.hashCode() );
        result = prime * result + ( (rosterId == null) ? 0 : rosterId.hashCode() );
        result = prime * result + ( (roundId == null) ? 0 : roundId.hashCode() );
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
        GroupMember other = ( GroupMember ) obj;
        if ( groupCode == null )
        {
            if ( other.groupCode != null )
                return false;
        }
        else if ( !groupCode.equals( other.groupCode ) )
            return false;
        if ( rosterId == null )
        {
            if ( other.rosterId != null )
                return false;
        }
        else if ( !rosterId.equals( other.rosterId ) )
            return false;
        if ( roundId == null )
        {
            if ( other.roundId != null )
                return false;
        }
        else if ( !roundId.equals( other.roundId ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + rosterId + ", " + roundId + ", " + groupCode + ", " + withdrawn + "]";
    }
}
