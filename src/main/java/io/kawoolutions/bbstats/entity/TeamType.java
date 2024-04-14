package io.kawoolutions.bbstats.entity;

import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.framework.entity.BaseEntity;

@Entity
@Table(name = "\"TeamTypes\"")
public class TeamType extends BaseEntity<String>
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String code;

    @Basic(optional = false)
    @Column(name = "age_group")
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @Basic(optional = false)
    @Column
    @Enumerated(EnumType.STRING)
    private TeamTypeGender gender;

    public TeamType()
    {
    }

    public TeamType(TeamType t)
    {
        this(t.getCode(), t.getAgeGroup(), t.getGender());
    }

    public TeamType(String code)
    {
        this(code, null, null);
    }

    public TeamType(AgeGroup ageGroup, TeamTypeGender gender)
    {
        this(null, ageGroup, gender);
    }

    public TeamType(String code, AgeGroup ageGroup, TeamTypeGender gender)
    {
        this.code = Objects.requireNonNull(code);
        this.ageGroup = ageGroup;
        this.gender = gender;
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

    public AgeGroup getAgeGroup()
    {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup)
    {
        this.ageGroup = ageGroup;
    }

    public TeamTypeGender getGender()
    {
        return gender;
    }

    public void setGender(TeamTypeGender gender)
    {
        this.gender = gender;
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
        TeamType other = ( TeamType ) obj;
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
        return "[" + code + ", " + ageGroup + ", " + gender + "]";
    }
}
