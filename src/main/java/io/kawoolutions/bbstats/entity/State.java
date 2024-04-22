package io.kawoolutions.bbstats.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "States")
@DiscriminatorValue("STATE")
public class State extends GeoContext
{
    private static final long serialVersionUID = 1L;

    public static final String FETCH_COUNTRY = "State.fetchCountry";

    @Basic
    @Column(name = "country_code")
    private String countryCode;

    @Basic(optional = false)
    @Column(name = "iso_code")
    private String isoCode;

    public State()
    {
    }

    public State(State s)
    {
        this(s.getParentId(), s.getName(), s.getCountryCode(), s.getIsoCode());
    }

    public State(String name, String isoCode)
    {
        this(name, null, isoCode);
    }

    public State(String name, String countryCode, String isoCode)
    {
        this(null, name, countryCode, isoCode);
    }

    public State(Integer parentId, String name, String countryCode, String isoCode)
    {
        super(parentId, name);

        this.countryCode = countryCode;
        this.isoCode = isoCode;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getIsoCode()
    {
        return isoCode;
    }

    public void setIsoCode(String isoCode)
    {
        this.isoCode = isoCode;
    }

    @Override
    public String toString()
    {
        return "[toString()=" + super.toString() + ", " + countryCode + ", " + isoCode + "]";
    }
}
