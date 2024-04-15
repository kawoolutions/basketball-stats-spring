package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.entity.base.BaseIdEntity;

@Entity
@Table(name = "\"Roles\"")
public class Role extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private Integer id;

    @Basic(optional = false)
    @Column
    private String name;

    @ManyToMany
    @JoinTable(name = "RoleLinks", joinColumns = @JoinColumn(name = "parent_role_id"), inverseJoinColumns = @JoinColumn(name = "child_role_id"))
    @JsonIgnore
    private List<Role> children;

    @ManyToMany(mappedBy = "children")
    @JsonIgnore
    private Set<Role> parents;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    public Role()
    {
    }

    public Role(Role r)
    {
        this(r.getId(), r.getName());
    }

    public Role(Integer id)
    {
        this(id, null);
    }

    public Role(String name)
    {
        this(null, name);
    }

    public Role(Integer id, String name)
    {
        this.id = Objects.requireNonNull(id);
        this.name = name;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Role> getChildren()
    {
        return children;
    }

    public void setChildren(List<Role> children)
    {
        this.children = children;
    }

    public Set<Role> getParents()
    {
        return parents;
    }

    public void setParents(Set<Role> parents)
    {
        this.parents = parents;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers(List<User> users)
    {
        this.users = users;
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
        Role other = ( Role ) obj;
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
        return "[" + id + ", " + name + "]";
    }
}
