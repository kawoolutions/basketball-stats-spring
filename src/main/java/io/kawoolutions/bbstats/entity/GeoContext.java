package io.kawoolutions.bbstats.entity;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.framework.entity.BaseIdEntity;

@Entity
@Table(name = "\"GeoContexts\"")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class GeoContext extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Integer id;

    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    protected GeoContextType type;

    @Basic(optional = false)
    @Column
    protected String name;

    @Basic
    @Column(name = "parent_id")
    protected Integer parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @JsonIgnore
    protected GeoContext parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    protected List<GeoContext> children;

    @OneToMany(mappedBy = "geoContext")
    @JsonIgnore
    protected List<Competition> competitions;

    protected GeoContext()
    {
    }

    protected GeoContext(Integer parentId)
    {
        this(parentId, null);
    }

    protected GeoContext(String name)
    {
        this(null, name);
    }

    protected GeoContext(Integer parentId, String name)
    {
        this.name = name;
        this.parentId = parentId;
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

    public GeoContextType getType()
    {
        return type;
    }

    public void setType(GeoContextType type)
    {
        this.type = type;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public GeoContext getParent()
    {
        return parent;
    }

    public void setParent(GeoContext parent)
    {
        this.parent = parent;
    }

    public List<GeoContext> getChildren()
    {
        return children;
    }

    public void setChildren(List<GeoContext> children)
    {
        this.children = children;
    }

    public List<Competition> getCompetitions()
    {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions)
    {
        this.competitions = competitions;
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
        GeoContext other = ( GeoContext ) obj;
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
        return "[" + id + ", " + type + ", " + name + ", " + parentId + "]";
    }
}
