package io.kawoolutions.bbstats.entity;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.entity.base.BaseEntity;

@Entity
@Table(name = "\"GroupLabels\"")
public class GroupLabel extends BaseEntity<String>
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String code;

    @Basic
    @Column
    private String name;

    public GroupLabel()
    {
    }

    public GroupLabel(GroupLabel g)
    {
        this(g.getCode(), g.getName());
    }

    public GroupLabel(String code)
    {
        this(code, null);
    }

    public GroupLabel(String code, String name)
    {
        this.code = Objects.requireNonNull(code);
        this.name = name;
    }

    @Override
    public String getPk()
    {
        return code;
    }

    @Override
    public void setPk(String pk)
    {
        this.code = pk;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (code == null) ? 0 : code.hashCode() );
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
        GroupLabel other = ( GroupLabel ) obj;
        if ( code == null )
        {
            if ( other.code != null )
                return false;
        }
        else if ( !code.equals( other.code ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + code + ", " + name + "]";
    }
}
