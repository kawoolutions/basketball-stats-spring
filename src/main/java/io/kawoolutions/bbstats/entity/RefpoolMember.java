package io.kawoolutions.bbstats.entity;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.entity.base.BaseEntity;

@Entity
@Table(name = "\"RefpoolMembers\"")
@IdClass(RefpoolMemberId.class)
public class RefpoolMember extends BaseEntity<RefpoolMemberId>
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "referee_id")
    private Integer refereeId;

    @Id
    @Column(name = "club_id")
    private Integer clubId;

    @Id
    @Column(name = "season_start_year")
    private Integer seasonStartYear;

    @Basic
    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "referee_id", insertable = false, updatable = false)
    @JsonIgnore
    private Referee referee;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "season_start_year", insertable = false, updatable = false)
    @JsonIgnore
    private Season season;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "club_id", insertable = false, updatable = false)
    @JsonIgnore
    private Club club;

    @OneToMany(mappedBy = "refpoolMember")
    @JsonIgnore
    private Set<Assignment> assignments;

    public RefpoolMember()
    {
    }

    public RefpoolMember(RefpoolMember r)
    {
        this(r.getRefereeId(), r.getClubId(), r.getSeasonStartYear(), r.getImagePath());
    }

    public RefpoolMember(String imagePath)
    {
        this(null, null, null, imagePath);
    }

    public RefpoolMember(Integer refereeId, Integer clubId, Integer seasonStartYear)
    {
        this(refereeId, clubId, seasonStartYear, null);
    }

    public RefpoolMember(Integer refereeId, Integer clubId, Integer seasonStartYear, String imagePath)
    {
        this.refereeId = Objects.requireNonNull(refereeId);
        this.clubId = Objects.requireNonNull(clubId);
        this.seasonStartYear = Objects.requireNonNull(seasonStartYear);
        this.imagePath = imagePath;

        this.referee = new Referee(refereeId);
        this.season = new Season(seasonStartYear);

        this.club = new Club();
        this.club.setId(clubId);
    }

    @Override
    public RefpoolMemberId getPk()
    {
        return new RefpoolMemberId(refereeId, clubId, seasonStartYear);
    }

    @Override
    public void setPk(RefpoolMemberId pk)
    {
        this.refereeId = pk.getRefereeId();
        this.clubId = pk.getClubId();
        this.seasonStartYear = pk.getSeasonStartYear();
    }

    public Integer getRefereeId()
    {
        return refereeId;
    }

    public void setRefereeId(Integer refereeId)
    {
        this.refereeId = refereeId;
    }

    public Integer getClubId()
    {
        return clubId;
    }

    public void setClubId(Integer clubId)
    {
        this.clubId = clubId;
    }

    public Integer getSeasonStartYear()
    {
        return seasonStartYear;
    }

    public void setSeasonStartYear(Integer seasonStartYear)
    {
        this.seasonStartYear = seasonStartYear;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public Referee getReferee()
    {
        return referee;
    }

    public void setReferee(Referee referee)
    {
        this.referee = referee;
    }

    public Season getSeason()
    {
        return season;
    }

    public void setSeason(Season season)
    {
        this.season = season;
    }

    public Club getClub()
    {
        return club;
    }

    public void setClub(Club club)
    {
        this.club = club;
    }

    public Set<Assignment> getAssignments()
    {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments)
    {
        this.assignments = assignments;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (clubId == null) ? 0 : clubId.hashCode() );
        result = prime * result + ( (refereeId == null) ? 0 : refereeId.hashCode() );
        result = prime * result + ( (seasonStartYear == null) ? 0 : seasonStartYear.hashCode() );
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
        RefpoolMember other = ( RefpoolMember ) obj;
        if ( clubId == null )
        {
            if ( other.clubId != null )
                return false;
        }
        else if ( !clubId.equals( other.clubId ) )
            return false;
        if ( refereeId == null )
        {
            if ( other.refereeId != null )
                return false;
        }
        else if ( !refereeId.equals( other.refereeId ) )
            return false;
        if ( seasonStartYear == null )
        {
            if ( other.seasonStartYear != null )
                return false;
        }
        else if ( !seasonStartYear.equals( other.seasonStartYear ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + refereeId + ", " + clubId + ", " + seasonStartYear + ", " + imagePath + "]";
    }
}
