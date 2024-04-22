package io.kawoolutions.bbstats.entity;

import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Districts")
@DiscriminatorValue("DISTRICT")
@NamedQuery(name = District.FIND_ALL, query = "SELECT di FROM District di ORDER BY di.name")
@NamedEntityGraph(name = District.FETCH_COUNTRY,
    attributeNodes = @NamedAttributeNode(value = "parent", subgraph = State.FETCH_COUNTRY),
    subgraphs = {
        @NamedSubgraph(name = State.FETCH_COUNTRY, attributeNodes = @NamedAttributeNode(value = "parent", subgraph = Region.FETCH_COUNTRY)),
        @NamedSubgraph(name = Region.FETCH_COUNTRY, attributeNodes = @NamedAttributeNode("parent"))}
)
public class District extends GeoContext
{
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "District.findAll";
    public static final String FETCH_COUNTRY = "District.fetchCountry";

    @OneToMany(mappedBy = "district")
    @JsonIgnore
    private List<Club> clubs;

    public District()
    {
    }

    public District(District d)
    {
        this(d.getParentId(), d.getName());
    }

    public District(String name)
    {
        this(null, name);
    }

    public District(Integer parentId, String name)
    {
        super(parentId, name);
    }

    public List<Club> getClubs()
    {
        return clubs;
    }

    public void setClubs(List<Club> clubs)
    {
        this.clubs = clubs;
    }

    @Override
    public String toString()
    {
        return "[toString()=" + super.toString() + "]";
    }
}
