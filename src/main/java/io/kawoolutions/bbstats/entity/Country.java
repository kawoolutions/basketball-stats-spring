package io.kawoolutions.bbstats.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Countries")
@DiscriminatorValue("COUNTRY")
@NamedQuery(name = Country.FIND_ALL, query = "SELECT co FROM Country co ORDER BY co.isoCode")
@NamedQuery(name = Country.FIND_BY_ISO_CODE, query = "SELECT co FROM Country co WHERE co.isoCode = :isoCode")
public class Country extends GeoContext
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Country.findAll";
    public static final String FIND_BY_ISO_CODE = "Country.findByIsoCode";

    @Basic(optional = false)
    @Column(name = "iso_code")
    private String isoCode;

    @Basic(optional = false)
    @Column(name = "iso_nbr")
    private String isoNbr;

    public Country()
    {
    }

    public Country(Country c)
    {
        this(c.getParentId(), c.getName(), c.getIsoCode(), c.getIsoNbr());
    }

    public Country(String name, String isoCode, String isoNbr)
    {
        this(null, name, isoCode, isoNbr);
    }

    public Country(Integer parentId, String name, String isoCode, String isoNbr)
    {
        super(parentId, name);

        this.isoCode = isoCode;
        this.isoNbr = isoNbr;
    }

    public String getIsoCode()
    {
        return isoCode;
    }

    public void setIsoCode(String isoCode)
    {
        this.isoCode = isoCode;
    }

    public String getIsoNbr()
    {
        return isoNbr;
    }

    public void setIsoNbr(String isoNbr)
    {
        this.isoNbr = isoNbr;
    }

    @Override
    public String toString()
    {
        return "[toString()=" + super.toString() + ", " + isoCode + ", " + isoNbr + "]";
    }
}
