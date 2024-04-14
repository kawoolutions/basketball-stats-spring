package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

public class GroupId implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer roundId;

    private String code;

    public GroupId()
    {
    }

    public GroupId(GroupId g)
    {
        this(g.getRoundId(), g.getCode());
    }

    public GroupId(Integer roundId, String code)
    {
        this.roundId = Objects.requireNonNull(roundId);
        this.code = Objects.requireNonNull(code);
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
        GroupId other = ( GroupId ) obj;
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
        return "[" + roundId + ", " + code + "]";
    }
}
