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

import io.kawoolutions.bbstats.entity.base.BaseIdEntity;

@Entity
@Table(name = "Referees")
@NamedEntityGraph(name = Referee.FETCH_REFPOOL_MEMBERS, attributeNodes = {@NamedAttributeNode("refpoolMembers")})
public class Referee extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    public static final String FETCH_REFPOOL_MEMBERS = "Referee.fetchRefpoolMembers";

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

    @OneToMany(mappedBy = "referee")
    @JsonIgnore
    private Set<RefpoolMember> refpoolMembers;

    public Referee()
    {
    }

    public Referee(Referee r)
    {
        this(r.getId(), r.getLicenseNbr());
    }

    public Referee(Integer id)
    {
        this(id, null);
    }

    public Referee(String licenseNbr)
    {
        this(null, licenseNbr);
    }

    public Referee(Integer id, String licenseNbr)
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

    public Set<RefpoolMember> getRefpoolMembers()
    {
        return refpoolMembers;
    }

    public void setRefpoolMembers(Set<RefpoolMember> refpoolMembers)
    {
        this.refpoolMembers = refpoolMembers;
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
        Referee other = ( Referee ) obj;
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
