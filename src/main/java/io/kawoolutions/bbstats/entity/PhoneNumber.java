package io.kawoolutions.bbstats.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class PhoneNumber implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "contact_id")
    private Integer contactId;

    @Basic(optional = false)
    @Column
    @Enumerated(EnumType.STRING)
    private PhoneNumberType type = PhoneNumberType.MOBILE;

    @Basic(optional = false)
    @Column(name = "country_code")
    private Integer countryCode;

    @Basic(optional = false)
    @Column(name = "area_code")
    private Integer areaCode;

    @Basic(optional = false)
    @Column(name = "subscriber_nbr")
    private Integer subscriberNbr;

    public PhoneNumber()
    {
    }

    public PhoneNumber(PhoneNumber p)
    {
        this(p.getContactId(), p.getType(), p.getCountryCode(), p.getAreaCode(), p.getSubscriberNbr());
    }

    public PhoneNumber(Integer contactId, PhoneNumberType type, Integer countryCode, Integer areaCode, Integer subscriberNbr)
    {
        this.contactId = Objects.requireNonNull(contactId);
        this.type = Objects.requireNonNull(type);
        this.countryCode = countryCode;
        this.areaCode = areaCode;
        this.subscriberNbr = subscriberNbr;
    }

    public Integer getContactId()
    {
        return contactId;
    }

    public void setContactId(Integer contactId)
    {
        this.contactId = contactId;
    }

    public PhoneNumberType getType()
    {
        return type;
    }

    public void setType(PhoneNumberType type)
    {
        this.type = type;
    }

    public Integer getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode)
    {
        this.countryCode = countryCode;
    }

    public Integer getAreaCode()
    {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode)
    {
        this.areaCode = areaCode;
    }

    public Integer getSubscriberNbr()
    {
        return subscriberNbr;
    }

    public void setSubscriberNbr(Integer subscriberNbr)
    {
        this.subscriberNbr = subscriberNbr;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (areaCode == null) ? 0 : areaCode.hashCode() );
        result = prime * result + ( (contactId == null) ? 0 : contactId.hashCode() );
        result = prime * result + ( (countryCode == null) ? 0 : countryCode.hashCode() );
        result = prime * result + ( (subscriberNbr == null) ? 0 : subscriberNbr.hashCode() );
        result = prime * result + ( (type == null) ? 0 : type.hashCode() );
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
        PhoneNumber other = ( PhoneNumber ) obj;
        if ( areaCode == null )
        {
            if ( other.areaCode != null )
                return false;
        }
        else if ( !areaCode.equals( other.areaCode ) )
            return false;
        if ( contactId == null )
        {
            if ( other.contactId != null )
                return false;
        }
        else if ( !contactId.equals( other.contactId ) )
            return false;
        if ( countryCode == null )
        {
            if ( other.countryCode != null )
                return false;
        }
        else if ( !countryCode.equals( other.countryCode ) )
            return false;
        if ( subscriberNbr == null )
        {
            if ( other.subscriberNbr != null )
                return false;
        }
        else if ( !subscriberNbr.equals( other.subscriberNbr ) )
            return false;
        if ( type != other.type )
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "[" + contactId + ", " + type + ", " + countryCode + ", " + areaCode + ", " + subscriberNbr + "]";
    }
}
