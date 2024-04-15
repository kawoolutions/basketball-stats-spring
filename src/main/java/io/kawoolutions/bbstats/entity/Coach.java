package io.kawoolutions.bbstats.entity;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kawoolutions.bbstats.framework.entity.BaseIdEntity;

@Entity
@Table(name = "\"Coaches\"")
@NamedEntityGraph(name = Coach.FETCH_STAFF_MEMBERS, attributeNodes = {@NamedAttributeNode("staffMembers")})
public class Coach extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    public static final String FETCH_STAFF_MEMBERS = "Coach.fetchStaffMembers";

    @Id
    @Column
    private Integer id;

    @Basic
    @Column(name = "license_nbr")
    private String licenseNbr;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Person person;

    @OneToMany(mappedBy = "coach")
    @JsonIgnore
    private Set<StaffMember> staffMembers;

    public Coach()
    {
    }

    public Coach(Coach c)
    {
        this(c.getId(), c.getLicenseNbr());
    }

    public Coach(Integer id)
    {
        this(id, null);
    }

    public Coach(String licenseNbr)
    {
        this(null, licenseNbr);
    }

    public Coach(Integer id, String licenseNbr)
    {
        this.id = Objects.requireNonNull(id);
        this.licenseNbr = licenseNbr;

        this.person = new Person();
        this.person.setId(id);
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

    public String getLicenseNbr()
    {
        return licenseNbr;
    }

    public void setLicenseNbr(String licenseNbr)
    {
        this.licenseNbr = licenseNbr;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public Set<StaffMember> getStaffMembers()
    {
        return staffMembers;
    }

    public void setStaffMembers(Set<StaffMember> staffMembers)
    {
        this.staffMembers = staffMembers;
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
        Coach other = ( Coach ) obj;
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
        return "[" + id + ", " + licenseNbr + "]";
    }
}
