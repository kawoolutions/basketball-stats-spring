package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Objects;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"Teams\"")
@IdClass(TeamId.class)
@NamedQuery(name = Team.FIND_BY_SEARCH_TERM, query = "SELECT te FROM Team te JOIN te.club cl JOIN te.teamType tt WHERE tt.code IN :teamTypeCodes AND ( cl.name LIKE :searchTerm OR cl.code LIKE :searchTerm ) ORDER BY cl.code, cl.name")
public class Team extends BaseEntity<TeamId> implements Comparable<Team>
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_SEARCH_TERM = "Team.findBySearchTerm";
    public static final String FETCH_TEAM_TYPES_AND_ROSTERS = "Team.fetchTeamTypesAndRosters";
    public static final String FETCH_CLUB = "Team.fetchClub";

    @Id
    @Column(name = "club_id")
    private Integer clubId;

    @Id
    @Column(name = "team_type_code")
    private String teamTypeCode;

    @Id
    @Column(name = "ordinal_nbr")
    private Integer ordinalNbr;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "club_id", insertable = false, updatable = false)
    @JsonbTransient
    private Club club;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_type_code", insertable = false, updatable = false)
    @JsonbTransient
    private TeamType teamType;

    @OneToMany(mappedBy = "team")
    @OrderBy("season")
    @JsonbTransient
    private List<Roster> rosters;

    public Team()
    {
    }

    public Team(Team t)
    {
        this(t.getClubId(), t.getTeamTypeCode(), t.getOrdinalNbr());
    }

    public Team(Integer clubId, String teamTypeCode, Integer ordinalNbr)
    {
        this.clubId = Objects.requireNonNull(clubId);
        this.teamTypeCode = Objects.requireNonNull(teamTypeCode);
        this.ordinalNbr = Objects.requireNonNull(ordinalNbr);

        this.club = new Club();
        this.club.setId(clubId);

        this.teamType = new TeamType(teamTypeCode);
    }

    @Override
    public TeamId getPk()
    {
        return new TeamId(clubId, teamTypeCode, ordinalNbr);
    }

    @Override
    public void setPk(TeamId pk)
    {
        this.clubId = pk.getClubId();
        this.teamTypeCode = pk.getTeamTypeCode();
        this.ordinalNbr = pk.getOrdinalNbr();
    }

    public Integer getClubId()
    {
        return clubId;
    }

    public void setClubId(Integer clubId)
    {
        this.clubId = clubId;
    }

    public String getTeamTypeCode()
    {
        return teamTypeCode;
    }

    public void setTeamTypeCode(String teamTypeCode)
    {
        this.teamTypeCode = teamTypeCode;
    }

    public Integer getOrdinalNbr()
    {
        return ordinalNbr;
    }

    public void setOrdinalNbr(Integer ordinalNbr)
    {
        this.ordinalNbr = ordinalNbr;
    }

    public Club getClub()
    {
        return club;
    }

    public void setClub(Club club)
    {
        this.club = club;
    }

    public TeamType getTeamType()
    {
        return teamType;
    }

    public void setTeamType(TeamType teamType)
    {
        this.teamType = teamType;
    }

    public List<Roster> getRosters()
    {
        return rosters;
    }

    public void setRosters(List<Roster> rosters)
    {
        this.rosters = rosters;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (clubId == null) ? 0 : clubId.hashCode() );
        result = prime * result + ( (ordinalNbr == null) ? 0 : ordinalNbr.hashCode() );
        result = prime * result + ( (teamTypeCode == null) ? 0 : teamTypeCode.hashCode() );
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
        Team other = ( Team ) obj;
        if ( clubId == null )
        {
            if ( other.clubId != null )
                return false;
        }
        else if ( !clubId.equals( other.clubId ) )
            return false;
        if ( ordinalNbr == null )
        {
            if ( other.ordinalNbr != null )
                return false;
        }
        else if ( !ordinalNbr.equals( other.ordinalNbr ) )
            return false;
        if ( teamTypeCode == null )
        {
            if ( other.teamTypeCode != null )
                return false;
        }
        else if ( !teamTypeCode.equals( other.teamTypeCode ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + clubId + ", " + teamTypeCode + ", " + ordinalNbr + "]";
    }

    @Override
    public int compareTo(Team other)
    {
        TeamType otherTeamType = other.getTeamType();
        AgeGroup otherAgeGroup = otherTeamType.getAgeGroup();
        String otherRelation = otherAgeGroup.name().charAt( 0 ) + "";
        Integer otherAge = Integer.valueOf( otherAgeGroup.name().substring( 1 ) );
        TeamTypeGender otherGender = otherTeamType.getGender();
        Integer otherOrdinalNbr = other.getOrdinalNbr();
        
        TeamType thisTeamType = getTeamType();
        AgeGroup thisAgeGroup = thisTeamType.getAgeGroup();
        String thisRelation = thisAgeGroup.name().charAt( 0 ) + "";
        Integer thisAge = Integer.valueOf( thisAgeGroup.name().substring( 1 ) );
        TeamTypeGender thisGender = thisTeamType.getGender();
        Integer thisOrdinalNbr = this.getOrdinalNbr();
        
        if ( thisRelation.equals( otherRelation ) )
        {
            if ( thisAge.equals( otherAge ) )
            {
                if ( thisGender.equals( otherGender ) )
                {
                    if ( thisOrdinalNbr.equals( otherOrdinalNbr ) )
                    {
                        // this must never happen!
                        return 0;
                    }
                    else
                    {
                        return thisOrdinalNbr.compareTo( otherOrdinalNbr );
                    }
                }
                else
                {
                    return thisGender.compareTo( otherGender );
                }
            }
            else
            {
                // order by age depending on relation
                if ( "U".equals( thisRelation ) )
                {
                    return otherAge.compareTo( thisAge );
                }
                
                return thisAge.compareTo( otherAge );
            }
        }
        else
        {
            return thisRelation.compareTo( otherRelation );
        }
    }
}
