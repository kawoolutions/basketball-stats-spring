package io.kawoolutions.bbstats.entity;

import java.util.List;
import java.util.Map;

import jakarta.persistence.Basic;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;

import io.kawoolutions.bbstats.entity.converter.ContactTypeConverter;
import io.kawoolutions.bbstats.entity.base.BaseIdEntity;

@Entity
@Table(name = "\"Contacts\"")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@SecondaryTable(name = "\"Addresses\"", pkJoinColumns = @PrimaryKeyJoinColumn(name = "contact_id", referencedColumnName = "id"))
public abstract class Contact extends BaseIdEntity
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    protected Integer id;

    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    @Convert(converter = ContactTypeConverter.class)
    protected ContactType type;

    @Basic
    @Column(name = "country_code", table = "\"Addresses\"")
    protected String countryCode;

    @Basic
    @Column(name = "zip_code", table = "\"Addresses\"")
    protected String zipCode;

    @Basic
    @Column(name = "city_name", table = "\"Addresses\"")
    protected String cityName;

    @Basic
    @Column(name = "street_name", table = "\"Addresses\"")
    protected String streetName;

    @Basic
    @Column(name = "house_nbr", table = "\"Addresses\"")
    protected String houseNbr;

    @Basic
    @Column(table = "\"Addresses\"")
    protected Double latitude;

    @Basic
    @Column(table = "\"Addresses\"")
    protected Double longitude;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "\"EmailAddresses\"", joinColumns = {@JoinColumn(name = "contact_id", referencedColumnName = "id")})
    @OrderColumn(name = "index")
    @Column(name = "uri")
    protected List<String> emailAddresses;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "\"PhoneNumbers\"", joinColumns = {@JoinColumn(name = "contact_id", referencedColumnName = "id", insertable = false, updatable = false)})
    @MapKeyColumn(name = "type", insertable = false, updatable = false)
    @MapKeyEnumerated(EnumType.STRING)
    protected Map<PhoneNumberType, PhoneNumber> phoneNumbers;

    protected Contact()
    {
    }

    protected Contact(String countryCode, String zipCode, String cityName, String streetName, String houseNbr, Double latitude, Double longitude)
    {
        this.countryCode = countryCode;
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.streetName = streetName;
        this.houseNbr = houseNbr;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public ContactType getType()
    {
        return type;
    }

    public void setType(ContactType type)
    {
        this.type = type;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getStreetName()
    {
        return streetName;
    }

    public void setStreetName(String streetName)
    {
        this.streetName = streetName;
    }

    public String getHouseNbr()
    {
        return houseNbr;
    }

    public void setHouseNbr(String houseNbr)
    {
        this.houseNbr = houseNbr;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public List<String> getEmailAddresses()
    {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses)
    {
        this.emailAddresses = emailAddresses;
    }

    public Map<PhoneNumberType, PhoneNumber> getPhoneNumbers()
    {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Map<PhoneNumberType, PhoneNumber> phoneNumbers)
    {
        this.phoneNumbers = phoneNumbers;
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
        Contact other = ( Contact ) obj;
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
        return "[" + id + ", " + type + ", " + countryCode + ", " + zipCode + ", " + cityName + ", " + streetName + ", " + houseNbr + ", " + latitude + ", " + longitude + "]";
    }
}
