package io.kawoolutions.bbstats.entity;

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
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.entity.base.BaseEntity;

@Entity
@Table(name = "\"StaffMembers\"")
@IdClass(StaffMemberId.class)
public class StaffMember extends BaseEntity<StaffMemberId>
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "coach_id")
    private Integer coachId;

    @Id
    @Column(name = "roster_id")
    private Integer rosterId;

    @Basic
    @Column
    @Enumerated(EnumType.STRING)
    private StaffMemberRole role;

    @Basic
    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_id", insertable = false, updatable = false)
    @JsonIgnore
    private Coach coach;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "roster_id", insertable = false, updatable = false)
    @JsonIgnore
    private Roster roster;

    public StaffMember()
    {
    }

    public StaffMember(StaffMember s)
    {
        this(s.getCoachId(), s.getRosterId(), s.getRole(), s.getImagePath());
    }

    public StaffMember(Integer coachId, Integer rosterId)
    {
        this(coachId, rosterId, null, null);
    }

    public StaffMember(StaffMemberRole role, String imagePath)
    {
        this(null, null, role, imagePath);
    }

    public StaffMember(Integer coachId, Integer rosterId, StaffMemberRole role, String imagePath)
    {
        this.coachId = Objects.requireNonNull(coachId);
        this.rosterId = Objects.requireNonNull(rosterId);
        this.role = role;
        this.imagePath = imagePath;

        this.coach = new Coach(coachId);

        this.roster = new Roster();
        this.roster.setId(rosterId);
    }

    @Override
    public StaffMemberId getPk()
    {
        return new StaffMemberId(coachId, rosterId);
    }

    @Override
    public void setPk(StaffMemberId pk)
    {
        this.coachId = pk.getCoachId();
        this.rosterId = pk.getRosterId();
    }

    public Integer getCoachId()
    {
        return coachId;
    }

    public void setCoachId(Integer coachId)
    {
        this.coachId = coachId;
    }

    public Integer getRosterId()
    {
        return rosterId;
    }

    public void setRosterId(Integer rosterId)
    {
        this.rosterId = rosterId;
    }

    public StaffMemberRole getRole()
    {
        return role;
    }

    public void setRole(StaffMemberRole role)
    {
        this.role = role;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public Coach getCoach()
    {
        return coach;
    }

    public void setCoach(Coach coach)
    {
        this.coach = coach;
    }

    public Roster getRoster()
    {
        return roster;
    }

    public void setRoster(Roster roster)
    {
        this.roster = roster;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (coachId == null) ? 0 : coachId.hashCode() );
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
        StaffMember other = ( StaffMember ) obj;
        if ( coachId == null )
        {
            if ( other.coachId != null )
                return false;
        }
        else if ( !coachId.equals( other.coachId ) )
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
        return "[" + coachId + ", " + rosterId + ", " + role + ", " + imagePath + "]";
    }
}
