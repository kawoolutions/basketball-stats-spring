package io.kawoolutions.bbstats.dto;

import io.kawoolutions.bbstats.entity.AgeGroup;
import io.kawoolutions.bbstats.entity.Continent;
import io.kawoolutions.bbstats.entity.Country;
import io.kawoolutions.bbstats.entity.GeoContext;
import io.kawoolutions.bbstats.entity.Region;
import io.kawoolutions.bbstats.entity.State;
import io.kawoolutions.bbstats.entity.TeamTypeGender;

import java.lang.reflect.Method;

public class TeamListDto extends BaseDto {
    private static final long serialVersionUID = 1L;

    private final Integer rosterId;

    private final String clubName;
    private final String clubCode;
    private final String fullClubName;

    private final Integer teamOrdinalNumber;
    private final String teamName;
    private final String fullTeamName;

    private final String teamTypeCode;

    private final AgeGroup teamTypeAgeGroup;
    private final TeamTypeGender teamTypeGender;

    private final String teamDesc;
    private final String teamLabel;

    private final String continentName;
    private final String countryCode;
    private final String countryName;
    private final String regionName;
    private final String stateName;
    private final String districtName;

    public TeamListDto(Integer rosterId, String clubName, String clubCode, Integer teamOrdinalNumber, String teamTypeCode,
                       AgeGroup teamTypeAgeGroup, TeamTypeGender teamTypeGender,
                       GeoContext continent, GeoContext country, GeoContext region, GeoContext state, GeoContext district) {
        this.rosterId = rosterId;

        this.clubName = clubName;
        this.clubCode = clubCode;
        this.fullClubName = clubName + " (" + clubCode + ")";

        this.teamOrdinalNumber = teamOrdinalNumber;
        this.teamName = clubName + " " + teamOrdinalNumber;
        this.fullTeamName = teamName + " (" + clubCode + teamOrdinalNumber + ")";

        this.teamTypeCode = teamTypeCode;

        // clause only (no function)
        this.teamDesc = teamName + ", " + teamTypeCode;
        this.teamLabel = fullTeamName + ", {0}";

        this.teamTypeAgeGroup = teamTypeAgeGroup;
        this.teamTypeGender = teamTypeGender;

//        log.info( "CONTINENT before: class = " + continent.getClass().getName() + " " + continent.getName() + ", " + continent );
//        log.info( "COUNTRY   before: class = " + country.getClass().getName() + " " + country.getName() + ", " + country );
//        log.info( "REGION    before: class = " + region.getClass().getName() + " " + region.getName() + ", " + region );
//        log.info( "STATE     before: class = " + state.getClass().getName() + " " + state.getName() + ", " + state );
//        log.info( "DISTRICT  before: class = " + district.getClass().getName() + " " + district.getName() + ", " + district );
//
//        log.info( "" );

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
            // just ignore, we're probably not using Hibernate right now
//            t.printStackTrace();
        }

//        log.info( "CONTINENT after: class = " + continent.getClass().getName() + " " + continent.getName() + ", " + continent );
//        log.info( "COUNTRY   after: class = " + country.getClass().getName() + " " + country.getName() + ", " + country );
//        log.info( "REGION    after: class = " + region.getClass().getName() + " " + region.getName() + ", " + region );
//        log.info( "STATE     after: class = " + state.getClass().getName() + " " + state.getName() + ", " + state );
//        log.info( "DISTRICT  after: class = " + district.getClass().getName() + " " + district.getName() + ", " + district );
//
//        log.info( "" );

        this.continentName = continent.getName();
        this.countryName = country.getName();
        this.countryCode = ((Country) country).getIsoCode();
        this.regionName = region.getName();
        this.stateName = state.getName();
        this.districtName = district.getName();
    }

    // team info

    public Integer getRosterId() {
        return rosterId;
    }

    public String getClubName() {
        return clubName;
    }

    public String getClubCode() {
        return clubCode;
    }

    public String getFullClubName() {
        return fullClubName;
    }

    public Integer getTeamOrdinalNumber() {
        return teamOrdinalNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getFullTeamName() {
        return fullTeamName;
    }

    public String getTeamTypeCode() {
        return teamTypeCode;
    }

    public AgeGroup getTeamTypeAgeGroup() {
        return teamTypeAgeGroup;
    }

    public TeamTypeGender getTeamTypeGender() {
        return teamTypeGender;
    }

    public String getTeamDesc() {
        return teamDesc;
    }

    public String getTeamLabel() {
        return teamLabel;
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
