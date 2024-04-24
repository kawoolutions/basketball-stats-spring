package io.kawoolutions.bbstats.dto;

import io.kawoolutions.bbstats.entity.Continent;
import io.kawoolutions.bbstats.entity.Country;
import io.kawoolutions.bbstats.entity.GeoContext;
import io.kawoolutions.bbstats.entity.PersonGender;
import io.kawoolutions.bbstats.entity.Player;
import io.kawoolutions.bbstats.entity.Region;
import io.kawoolutions.bbstats.entity.Roster;
import io.kawoolutions.bbstats.entity.State;
import io.kawoolutions.bbstats.entity.TeamMember;
import io.kawoolutions.bbstats.util.NamingUtil;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerDto extends BaseDto {
    private static final long serialVersionUID = 1L;

    //    private final Integer rosterId;
    private final Integer playerId;

    private final String firstName;
    private final String lastName;
    private final boolean incognito;

    private final String name;

    private final PersonGender gender;
    private final LocalDate birthDate;

    private final List<Roster> rosters;

    private final String continentName;
    private final String countryCode;
    private final String countryName;
    private final String regionName;
    private final String stateName;
    private final String districtName;

    public PlayerDto(Integer seasonStartYear, Integer playerId, Player pl,
                     String firstName, String lastName, PersonGender gender, LocalDate birthDate, boolean incognito,
                     GeoContext continent, GeoContext country, GeoContext region, GeoContext state, GeoContext district) {
//        this.rosterId = rosterId;
        this.playerId = playerId;

        this.firstName = firstName;
        this.lastName = lastName;
        this.incognito = incognito;

        this.name = NamingUtil.getFormalPersonNameFor(lastName, firstName, incognito);

        this.gender = gender;
        this.birthDate = birthDate;

        List<TeamMember> teamMembers = pl.getTeamMembers();
        this.rosters = teamMembers.stream().map(tm -> tm.getRoster()).filter(ro -> seasonStartYear.equals(ro.getSeasonStartYear())).collect(Collectors.toList());

//        System.out.println( seasonStartYear + " rosters for " + this.name + ": " + rosters );

        try {
            // Hibernate isn't always present on the classpath, use reflection to unproxy when on Hibernate
//          country = ( Country ) Hibernate.unproxy( country );

            Class<?> cls = Class.forName("org.hibernate.Hibernate");
            Method unproxyMethod = cls.getMethod("unproxy", new Class[]{Object.class});

            continent = (Continent) unproxyMethod.invoke(null, continent);
            country = (Country) unproxyMethod.invoke(null, country);
            region = (Region) unproxyMethod.invoke(null, region);
            state = (State) unproxyMethod.invoke(null, state);
        } catch (Throwable t) {
            // just ignore
//            t.printStackTrace();
        }

        this.continentName = continent.getName();
        this.countryName = country.getName();
        this.countryCode = ((Country) country).getIsoCode();
        this.regionName = region.getName();
        this.stateName = state.getName();
        this.districtName = district.getName();
    }

//    // roster info
//
//    public Integer getRosterId()
//    {
//        return rosterId;
//    }

    // player info

    public Integer getPlayerId() {
        return playerId;
    }

    // person info

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isIncognito() {
        return incognito;
    }

    public String getName() {
        return name;
    }

    public PersonGender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Roster> getRosters() {
        return this.rosters;
    }

    // geo info

    public String getContinentName() {
        return continentName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getStateName() {
        return stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    /*
     * @Override public List<Object> getValues() { return null; }
     */
}
