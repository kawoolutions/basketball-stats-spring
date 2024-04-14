package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Objects;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.framework.entity.BaseIdEntity;

@Entity
@Table(name = "\"Rosters\"")
@NamedQuery(name = Roster.FIND_BY_TEAM_AND_SEASON, query = "SELECT ro FROM Roster ro JOIN ro.team te JOIN te.club cl JOIN te.teamType tt JOIN ro.season se WHERE cl.id = :clubId AND tt.code = :teamTypeCode AND te.ordinalNbr = :ordinalNbr AND se.startYear = :seasonStartYear")
@NamedQuery(name = Roster.FIND_BY_PLAYER_AND_SEASON, query = "SELECT ro FROM Roster ro JOIN ro.team te JOIN te.club cl JOIN te.teamType tt JOIN ro.season se JOIN ro.teamMembers tm JOIN tm.player pl WHERE pl.id = :playerId AND se.startYear = :seasonStartYear")
@NamedEntityGraph(name = Roster.FETCH_TEAM_MEMBERS, attributeNodes = @NamedAttributeNode("teamMembers"))
public class Roster extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_TEAM_AND_SEASON = "Roster.findByTeamAndSeason";
    public static final String FIND_BY_PLAYER_AND_SEASON = "Roster.findByPlayerAndSeason";
    public static final String FETCH_TEAM_MEMBERS = "Roster.fetchTeamMembers";
    public static final String FETCH_SEASON = "Roster.fetchSeason";
    public static final String FETCH_CLUB = "Roster.fetchClub";
    public static final String FETCH_TEAM_MEMBERS_CLUB_COLORS = "Roster.fetchTeamMembersClubColors";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Basic
    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "season_start_year")
    @JsonbTransient
    private Season season;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "club_id", referencedColumnName = "club_id")
    @JoinColumn(name = "team_type_code", referencedColumnName = "team_type_code")
    @JoinColumn(name = "team_ordinal_nbr", referencedColumnName = "ordinal_nbr")
    @JsonbTransient
    private Team team;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "primary_jersey_color_name")
    @JsonbTransient
    private Color primaryJerseyColor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondary_jersey_color_name")
    @JsonbTransient
    private Color secondaryJerseyColor;

    @OneToMany(mappedBy = "roster")
    @JsonbTransient
    private List<GroupMember> groupMembers;

    @OneToMany(mappedBy = "roster")
    @JsonbTransient
    private List<Score> scores;

    @OneToMany(mappedBy = "roster")
    @JsonbTransient
    private List<StaffMember> staffMembers;

    @OneToMany(mappedBy = "roster")
    @JsonbTransient
    private List<TeamMember> teamMembers;

    public Roster()
    {
    }

    public Roster(Roster r)
    {
        this(r.getClubId(), r.getTeamTypeCode(), r.getTeamOrdinalNbr(), r.getSeasonStartYear(), r.getPrimaryJerseyColorName(), r.getSecondaryJerseyColorName(), r.getImagePath());

        this.id = Objects.requireNonNull(r.getId());
    }

    public Roster(String imagePath)
    {
        this(null, null, null, null, null, null, imagePath);
    }

    public Roster(Integer clubId, String teamTypeCode, Integer teamOrdinalNbr, Integer seasonStartYear)
    {
        this(clubId, teamTypeCode, teamOrdinalNbr, seasonStartYear, null, null);
    }

    public Roster(Integer clubId, String teamTypeCode, Integer teamOrdinalNbr, Integer seasonStartYear, String primaryJerseyColorName, String secondaryJerseyColorName)
    {
        this(clubId, teamTypeCode, teamOrdinalNbr, seasonStartYear, primaryJerseyColorName, secondaryJerseyColorName, null);
    }

    public Roster(Integer clubId, String teamTypeCode, Integer teamOrdinalNbr, Integer seasonStartYear, String primaryJerseyColorName, String secondaryJerseyColorName, String imagePath)
    {
        this.imagePath = imagePath;

        this.season = new Season(seasonStartYear);
        this.team = new Team(clubId, teamTypeCode, teamOrdinalNbr);
        this.primaryJerseyColor = new Color(primaryJerseyColorName);
        this.secondaryJerseyColor = new Color(secondaryJerseyColorName);
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

    public Integer getClubId()
    {
        return team.getClubId();
    }

    public void setClubId(Integer clubId)
    {
        team.setClubId(clubId);
    }

    public String getTeamTypeCode()
    {
        return team.getTeamTypeCode();
    }

    public void setTeamTypeCode(String teamTypeCode)
    {
        team.setTeamTypeCode(teamTypeCode);
    }

    public Integer getTeamOrdinalNbr()
    {
        return team.getOrdinalNbr();
    }

    public void setTeamOrdinalNbr(Integer teamOrdinalNbr)
    {
        team.setOrdinalNbr(teamOrdinalNbr);
    }

    public Integer getSeasonStartYear()
    {
        return season.getStartYear();
    }

    public void setSeasonStartYear(Integer seasonStartYear)
    {
        season.setStartYear(seasonStartYear);
    }

    public String getPrimaryJerseyColorName()
    {
        return primaryJerseyColor.getName();
    }

    public void setPrimaryJerseyColorName(String primaryJerseyColorName)
    {
        primaryJerseyColor.setName(primaryJerseyColorName);
    }

    public String getSecondaryJerseyColorName()
    {
        return secondaryJerseyColor.getName();
    }

    public void setSecondaryJerseyColorName(String secondaryJerseyColorName)
    {
        secondaryJerseyColor.setName(secondaryJerseyColorName);
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public Season getSeason()
    {
        return season;
    }

    public void setSeason(Season season)
    {
        this.season = season;
    }

    public Team getTeam()
    {
        return team;
    }

    public void setTeam(Team team)
    {
        this.team = team;
    }

    public Color getPrimaryJerseyColor()
    {
        return primaryJerseyColor;
    }

    public void setPrimaryJerseyColor(Color primaryJerseyColor)
    {
        this.primaryJerseyColor = primaryJerseyColor;
    }

    public Color getSecondaryJerseyColor()
    {
        return secondaryJerseyColor;
    }

    public void setSecondaryJerseyColor(Color secondaryJerseyColor)
    {
        this.secondaryJerseyColor = secondaryJerseyColor;
    }

    public List<GroupMember> getGroupMembers()
    {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers)
    {
        this.groupMembers = groupMembers;
    }

    public List<Score> getScores()
    {
        return scores;
    }

    public void setScores(List<Score> scores)
    {
        this.scores = scores;
    }

    public List<StaffMember> getStaffMembers()
    {
        return staffMembers;
    }

    public void setStaffMembers(List<StaffMember> staffMembers)
    {
        this.staffMembers = staffMembers;
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
        Roster other = ( Roster ) obj;
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
        return "[" + id + ", " + imagePath + "]";
    }
}
