package io.kawoolutions.bbstats.entity;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"Colors\"")
@NamedQuery(name = Color.FIND_ALL, query = "SELECT cl FROM Color cl ORDER BY cl.name")
@NamedQuery(name = Color.FIND_BY_NAME, query = "SELECT cl FROM Color cl WHERE cl.name = :name")
public class Color extends BaseEntity<String>
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Color.findAll";
    public static final String FIND_BY_NAME = "Color.findByName";

    @Id
    @Column
    private String name;

    @Basic(optional = false)
    @Column
    private byte[] rgb;

    public Color()
    {
    }

    public Color(Color c)
    {
        this(c.getName(), c.getRgb());
    }

    public Color(String name)
    {
        this(name, null);
    }

    public Color(byte[] rgb)
    {
        this(null, rgb);
    }

    public Color(String name, byte[] rgb)
    {
        this.name = Objects.requireNonNull(name);
        this.rgb = rgb;
    }

    @Override
    public String getPk()
    {
        return name;
    }

    @Override
    public void setPk(String pk)
    {
        this.name = pk;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public byte[] getRgb()
    {
        return rgb;
    }

    public void setRgb(byte[] rgb)
    {
        this.rgb = rgb;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (name == null) ? 0 : name.hashCode() );
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
        Color other = ( Color ) obj;
        if ( name == null )
        {
            if ( other.name != null )
                return false;
        }
        else if ( !name.equals( other.name ) )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + name + ", " + rgb + "]";
    }
}
