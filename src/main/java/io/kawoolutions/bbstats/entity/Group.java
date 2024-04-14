package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Objects;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"Groups\"")
@IdClass(GroupId.class)
@NamedQuery(name = Group.FIND_BY_COMPETITION,
            query = "SELECT gr " +
                    "FROM Group gr " +
                    "  LEFT JOIN gr.groupLabel gl " +
                    "  JOIN gr.round rd " +
                    "  JOIN rd.season se " +
                    "  JOIN rd.competition cm " +
                    "  JOIN cm.teamType tt " +
                    "  JOIN cm.geoContext gc " +
                    "WHERE gc.id = :geoContextId " +
                    "  AND tt.code = :teamTypeCode " +
                    "  AND cm.type = :competitionType " +
                    "  AND cm.level = :competitionLevel " +
                    "ORDER BY gl.code, se.startYear")
@NamedEntityGraph(name = Group.FETCH_COMPETITION_LABELS,
    attributeNodes = @NamedAttributeNode(value = "round", subgraph = Round.FETCH_COMPETITION_LABELS),
    subgraphs = {
        @NamedSubgraph(name = Round.FETCH_COMPETITION_LABELS, attributeNodes = @NamedAttributeNode(value = "competition", subgraph = Competition.FETCH_COMPETITION_LABELS)),
        @NamedSubgraph(name = Competition.FETCH_COMPETITION_LABELS, attributeNodes = @NamedAttributeNode("competitionLabels"))}
)
public class Group extends BaseEntity<GroupId>
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_COMPETITION = "Group.findByCompetition";
    public static final String FETCH_COMPETITION_LABELS = "Group.fetchCompetitionLabels";
    public static final String FETCH_ROUND = "Group.fetchRound";

    @Id
    @Column(name = "round_id")
    private Integer roundId;

    @Id
    @Column
    private String code;

    @Basic
    @Column(name = "official_code")
    private String officialCode;

    @Basic
    @Column(name = "max_members")
    private Integer maxMembers;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "round_id", insertable = false, updatable = false)
    @JsonbTransient
    private Round round;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "code", insertable = false, updatable = false)
    @JsonbTransient
    private GroupLabel groupLabel;

    @ManyToMany
    @JoinTable(name = "GroupLinks", joinColumns = {@JoinColumn(name = "parent_round_id", referencedColumnName = "round_id"), @JoinColumn(name = "parent_group_code", referencedColumnName = "code")}, inverseJoinColumns = {@JoinColumn(name = "child_round_id", referencedColumnName = "round_id"), @JoinColumn(name = "child_group_code", referencedColumnName = "code")})
    @JsonbTransient
    private List<Group> children;

    @OneToMany(mappedBy = "group")
    @JsonbTransient
    private List<Game> games;

    @OneToMany(mappedBy = "group")
    @JsonbTransient
    private List<GroupMember> groupMembers;

    @ManyToMany(mappedBy = "children")
    @JsonbTransient
    private List<Group> parents;

    public Group()
    {
    }

    public Group(Group g)
    {
        this(g.getRoundId(), g.getCode(), g.getOfficialCode(), g.getMaxMembers());
    }

    public Group(Integer roundId, String code)
    {
        this(roundId, code, null, null);
    }

    public Group(String officialCode, Integer maxMembers)
    {
        this(null, null, officialCode, maxMembers);
    }

    public Group(Integer roundId, String code, String officialCode, Integer maxMembers)
    {
        this.roundId = Objects.requireNonNull(roundId);
        this.code = Objects.requireNonNull(code);
        this.officialCode = officialCode;
        this.maxMembers = maxMembers;
        // this.round = new Round();
        // this.round.setId(roundId);

        this.groupLabel = new GroupLabel(code);
    }

    @Override
    public GroupId getPk()
    {
        return new GroupId(roundId, code);
    }

    @Override
    public void setPk(GroupId pk)
    {
        this.roundId = pk.getRoundId();
        this.code = pk.getCode();
    }

    public Integer getRoundId()
    {
        return roundId;
    }

    public void setRoundId(Integer roundId)
    {
        this.roundId = roundId;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getOfficialCode()
    {
        return officialCode;
    }

    public void setOfficialCode(String officialCode)
    {
        this.officialCode = officialCode;
    }

    public Integer getMaxMembers()
    {
        return maxMembers;
    }

    public void setMaxMembers(Integer maxMembers)
    {
        this.maxMembers = maxMembers;
    }

    public Round getRound()
    {
        return round;
    }

    public void setRound(Round round)
    {
        this.round = round;
    }

    public GroupLabel getGroupLabel()
    {
        return groupLabel;
    }

    public void setGroupLabel(GroupLabel groupLabel)
    {
        this.groupLabel = groupLabel;
    }

    public List<Group> getChildren()
    {
        return children;
    }

    public void setChildren(List<Group> children)
    {
        this.children = children;
    }

    public List<Game> getGames()
    {
        return games;
    }

    public void setGames(List<Game> games)
    {
        this.games = games;
    }

    public List<GroupMember> getGroupMembers()
    {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers)
    {
        this.groupMembers = groupMembers;
    }

    public List<Group> getParents()
    {
        return parents;
    }

    public void setParents(List<Group> parents)
    {
        this.parents = parents;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (code == null) ? 0 : code.hashCode() );
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
        Group other = ( Group ) obj;
        if ( code == null )
        {
            if ( other.code != null )
                return false;
        }
        else if ( !code.equals( other.code ) )
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
        return "[" + roundId + ", " + code + ", " + officialCode + ", " + maxMembers + "]";
    }
}
