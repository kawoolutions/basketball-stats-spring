package io.kawoolutions.bbstats.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.entity.base.BaseIdEntity;

@Entity
@Table(name = "Games")
@NamedQuery(name = Game.FIND_BY_PK, query = "SELECT ga FROM Game ga WHERE ga.id = :gameId")
@NamedQuery(name = Game.FIND_BY_GROUP,
            query = "SELECT DISTINCT ga " +
                    "FROM Game ga " +
                    "  JOIN ga.group gr " +
                    "  JOIN gr.groupLabel gl " +
                    "  JOIN gr.round rd " +
                    "WHERE rd.id = :roundId AND gl.code = :groupCode " +
                    "ORDER BY ga.scheduledTipoff, ga.matchdayNbr, ga.officialNbr, ga.id")
@NamedQuery(name = Game.FIND_BY_GROUP_ORDER_BY_CASE_WHEN,
            query = "SELECT DISTINCT ga " +
                    "FROM Game ga " +
                    "  JOIN ga.group gr " +
                    "  JOIN gr.groupLabel gl " +
                    "  JOIN gr.round rd " +
                    "WHERE rd.id = :roundId AND gl.code = :groupCode " +
                    "ORDER BY CASE WHEN ga.actualTipoff IS NOT NULL THEN ga.actualTipoff ELSE ga.scheduledTipoff END DESC, ga.matchdayNbr, ga.officialNbr, ga.id")
@NamedQuery(name = Game.FIND_BY_ROSTER, query = "SELECT ga FROM Game ga JOIN ga.scores sc JOIN sc.roster ro WHERE ro.id = :rosterId OR ro.id = :rosterId ORDER BY CASE WHEN ga.actualTipoff IS NOT NULL THEN ga.actualTipoff ELSE ga.scheduledTipoff END DESC, ga.matchdayNbr, ga.officialNbr, ga.id")
@NamedEntityGraph(name = Game.FETCH_ARENA_AND_COMPETITION_TEAM_TYPE,
    attributeNodes = {
        @NamedAttributeNode("arena"),
        @NamedAttributeNode(value = "group", subgraph = Group.FETCH_ROUND)},
    subgraphs = {
        @NamedSubgraph(name = Group.FETCH_ROUND, attributeNodes = {@NamedAttributeNode(value = "round", subgraph = Round.FETCH_COMPETITION)}),
        @NamedSubgraph(name = Round.FETCH_COMPETITION, attributeNodes = {@NamedAttributeNode(value = "competition", subgraph = Competition.FETCH_TEAM_TYPE)}),
        @NamedSubgraph(name = Competition.FETCH_TEAM_TYPE, attributeNodes = {@NamedAttributeNode("teamType")})}
)
@NamedEntityGraph(name = Game.FETCH_ARENA_CLUBS_AND_STATS,
    attributeNodes = {
        @NamedAttributeNode("arena"),
        @NamedAttributeNode(value = "scores", subgraph = Score.FETCH_CLUB_AND_STATS)},
    subgraphs = {
        @NamedSubgraph(name = Score.FETCH_CLUB_AND_STATS, attributeNodes = {@NamedAttributeNode(value = "roster", subgraph = Roster.FETCH_CLUB), @NamedAttributeNode(value = "playerStats", subgraph = PlayerStat.FETCH_STATS)}),
        @NamedSubgraph(name = Roster.FETCH_CLUB, attributeNodes = {@NamedAttributeNode(value = "team", subgraph = Team.FETCH_CLUB)}),
        @NamedSubgraph(name = Team.FETCH_CLUB, attributeNodes = @NamedAttributeNode("club")),
        @NamedSubgraph(name = PlayerStat.FETCH_STATS, attributeNodes = @NamedAttributeNode("stats"))}
)
@NamedEntityGraph(name = Game.FETCH_ARENA_AND_SCORES, attributeNodes = {@NamedAttributeNode("arena"), @NamedAttributeNode("scores")})
@NamedEntityGraph(name = Game.FETCH_PLAYER_STATS,
                  attributeNodes = {@NamedAttributeNode(value = "scores", subgraph = Score.FETCH_PLAYER_STATS)},
                  subgraphs = {@NamedSubgraph(name = Score.FETCH_PLAYER_STATS, attributeNodes = @NamedAttributeNode("playerStats"))}
)
public class Game extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_PK = "Game.findByPk";
    public static final String FIND_BY_GROUP = "Game.findByGroup";
    public static final String FIND_BY_GROUP_ORDER_BY_CASE_WHEN = "Game.findByGroupOrderByCaseWhen";
    public static final String FIND_BY_ROSTER = "Game.findByRoster";
    public static final String FETCH_ARENA_AND_COMPETITION_TEAM_TYPE = "Game.fetchArenaAndCompetitionTeamType";
    public static final String FETCH_ARENA_CLUBS_AND_STATS = "Game.fetchArenaClubsAndStats";
    public static final String FETCH_ARENA_AND_SCORES = "Game.fetchArenaAndScores";
    public static final String FETCH_PLAYER_STATS = "Game.fetchPlayerStats";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Basic(optional = false)
    @Column(name = "scheduled_tipoff")
    private LocalDateTime scheduledTipoff;

    @Basic
    @Column(name = "actual_tipoff")
    private LocalDateTime actualTipoff;

    @Basic
    @Column(name = "matchday_nbr")
    private Integer matchdayNbr;

    @Basic
    @Column(name = "official_nbr")
    private String officialNbr;

    @Basic
    @Column
    private Integer attendance;

    @Basic
    @Column
    private String recap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_club_id")
    @JsonIgnore
    private Club refClub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arena_id")
    @JsonIgnore
    private Arena arena;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "round_id", referencedColumnName = "round_id")
    @JoinColumn(name = "group_code", referencedColumnName = "code")
    @JsonIgnore
    private Group group;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "game")
    @MapKey(name = "homeAway")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<HomeAway, Score> scores;

    public Game()
    {
    }

    public Game(Game g)
    {
        this(g.getRoundId(), g.getGroupCode(), g.getArenaId(), g.getRefClubId(), g.getScheduledTipoff(), g.getActualTipoff(), g.getMatchdayNbr(), g.getOfficialNbr(), g.getAttendance(), g.getRecap());

        this.id = Objects.requireNonNull(g.getId());
    }

    public Game(LocalDateTime scheduledTipoff)
    {
        this(null, null, scheduledTipoff);
    }

    public Game(Integer roundId, String groupCode, LocalDateTime scheduledTipoff)
    {
        this(roundId, groupCode, null, null, scheduledTipoff, null, null, null, null, null);
    }

    public Game(Integer roundId, String groupCode, Integer arenaId, Integer refClubId)
    {
        this(roundId, groupCode, arenaId, refClubId, null, null, null, null, null, null);
    }

    public Game(LocalDateTime scheduledTipoff, LocalDateTime actualTipoff, Integer matchdayNbr, String officialNbr, Integer attendance, String recap)
    {
        this(null, null, null, null, scheduledTipoff, actualTipoff, matchdayNbr, officialNbr, attendance, recap);
    }

    public Game(Integer roundId, String groupCode, Integer arenaId, Integer refClubId, LocalDateTime scheduledTipoff, LocalDateTime actualTipoff, Integer matchdayNbr, String officialNbr, Integer attendance, String recap)
    {
        this.scheduledTipoff = scheduledTipoff;
        this.actualTipoff = actualTipoff;
        this.matchdayNbr = matchdayNbr;
        this.officialNbr = officialNbr;
        this.attendance = attendance;
        this.recap = recap;

        if ( refClubId != null )
        {
            this.refClub = new Club();
            this.refClub.setId(refClubId);
        }

        if ( arenaId != null )
        {
            this.arena = new Arena();
            this.arena.setId(arenaId);
        }

        this.group = new Group(roundId, groupCode);
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

    public Integer getRoundId()
    {
        return group.getRoundId();
    }

    public void setRoundId(Integer roundId)
    {
        group.setRoundId(roundId);
    }

    public String getGroupCode()
    {
        return group.getCode();
    }

    public void setGroupCode(String groupCode)
    {
        group.setCode(groupCode);
    }

    public Integer getArenaId()
    {
        return arena.getId();
    }

    public void setArenaId(Integer arenaId)
    {
        arena.setId(arenaId);
    }

    public Integer getRefClubId()
    {
        return refClub.getId();
    }

    public void setRefClubId(Integer refClubId)
    {
        refClub.setId(refClubId);
    }

    public LocalDateTime getScheduledTipoff()
    {
        return scheduledTipoff;
    }

    public void setScheduledTipoff(LocalDateTime scheduledTipoff)
    {
        this.scheduledTipoff = scheduledTipoff;
    }

    public LocalDateTime getActualTipoff()
    {
        return actualTipoff;
    }

    public void setActualTipoff(LocalDateTime actualTipoff)
    {
        this.actualTipoff = actualTipoff;
    }

    public Integer getMatchdayNbr()
    {
        return matchdayNbr;
    }

    public void setMatchdayNbr(Integer matchdayNbr)
    {
        this.matchdayNbr = matchdayNbr;
    }

    public String getOfficialNbr()
    {
        return officialNbr;
    }

    public void setOfficialNbr(String officialNbr)
    {
        this.officialNbr = officialNbr;
    }

    public Integer getAttendance()
    {
        return attendance;
    }

    public void setAttendance(Integer attendance)
    {
        this.attendance = attendance;
    }

    public String getRecap()
    {
        return recap;
    }

    public void setRecap(String recap)
    {
        this.recap = recap;
    }

    public Club getRefClub()
    {
        return refClub;
    }

    public void setRefClub(Club refClub)
    {
        this.refClub = refClub;
    }

    public Arena getArena()
    {
        return arena;
    }

    public void setArena(Arena arena)
    {
        this.arena = arena;
    }

    public Group getGroup()
    {
        return group;
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    public List<Assignment> getAssignments()
    {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments)
    {
        this.assignments = assignments;
    }

    public Map<HomeAway, Score> getScores()
    {
        return scores;
    }

    public void setScores(Map<HomeAway, Score> scores)
    {
        this.scores = scores;
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
        Game other = ( Game ) obj;
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
        return "[" + id + ", " + scheduledTipoff + ", " + actualTipoff + ", " + matchdayNbr + ", " + officialNbr + ", " + attendance + ", " + recap + "]";
    }
}
