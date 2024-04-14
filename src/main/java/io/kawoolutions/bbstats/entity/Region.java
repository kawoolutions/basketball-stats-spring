package io.kawoolutions.bbstats.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"Regions\"")
@DiscriminatorValue("REGION")
public class Region extends GeoContext
{
    private static final long serialVersionUID = 1L;

    public static final String FETCH_COUNTRY = "Region.fetchCountry";

    public Region()
    {
    }

    public Region(Region r)
    {
        this(r.getParentId(), r.getName());
    }

    public Region(String name)
    {
        this(null, name);
    }

    public Region(Integer parentId, String name)
    {
        super(parentId, name);
    }

    @Override
    public String toString()
    {
        return "[toString()=" + super.toString() + "]";
    }
}
