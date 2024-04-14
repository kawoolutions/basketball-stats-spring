package io.kawoolutions.bbstats.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"Continents\"")
@DiscriminatorValue("CONTINENT")
public class Continent extends GeoContext
{
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "iso_code")
    private String isoCode;

    public Continent()
    {
    }

    public Continent(Continent c)
    {
        this(c.getParentId(), c.getName(), c.getIsoCode());
    }

    public Continent(String name, String isoCode)
    {
        this(null, name, isoCode);
    }

    public Continent(Integer parentId, String name, String isoCode)
    {
        super(parentId, name);

        this.isoCode = isoCode;
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
        return "[toString()=" + super.toString() + ", " + isoCode + "]";
    }
}
