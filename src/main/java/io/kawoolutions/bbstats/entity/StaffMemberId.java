package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

public class StaffMemberId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer coachId;

    private Integer rosterId;

    public StaffMemberId()
    {
    }

    public StaffMemberId(StaffMemberId s)
    {
        this(s.getCoachId(), s.getRosterId());
    }

    public StaffMemberId(Integer coachId, Integer rosterId)
    {
        this.coachId = Objects.requireNonNull(coachId);
        this.rosterId = Objects.requireNonNull(rosterId);
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
        StaffMemberId other = ( StaffMemberId ) obj;
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
        return "[" + coachId + ", " + rosterId + "]";
    }
}
