package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

public class GroupMemberId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer rosterId;

    private Integer roundId;

    private String groupCode;

    public GroupMemberId()
    {
    }

    public GroupMemberId(GroupMemberId g)
    {
        this(g.getRosterId(), g.getRoundId(), g.getGroupCode());
    }

    public GroupMemberId(Integer rosterId, Integer roundId, String groupCode)
    {
        this.rosterId = Objects.requireNonNull(rosterId);
        this.roundId = Objects.requireNonNull(roundId);
        this.groupCode = Objects.requireNonNull(groupCode);
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
        GroupMemberId other = ( GroupMemberId ) obj;
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
        return "[" + rosterId + ", " + roundId + ", " + groupCode + "]";
    }
}
