package io.kawoolutions.bbstats.dto;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import io.kawoolutions.bbstats.entity.AgeGroup;
import io.kawoolutions.bbstats.entity.Country;
import io.kawoolutions.bbstats.entity.GeoContext;
import io.kawoolutions.bbstats.entity.TeamTypeGender;

@Entity
public class TeamDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer teamId;

    private Integer rosterId;

    private String clubName;

    private String clubCode;
    private String fullClubName;

    private Integer teamOrdinalNumber;
    private String teamName;
    private String fullTeamName;

    private String teamTypeCode;

    private AgeGroup teamTypeAgeGroup;
    private TeamTypeGender teamTypeGender;

    private String teamDesc;
    private String teamLabel;

    private String continentName;
    private String countryCode;
    private String countryName;
    private String regionName;
    private String stateName;
    private String districtName;

    public TeamDto() {

    }

    public TeamDto(Integer rosterId, String clubName, String clubCode, Integer teamOrdinalNumber, String teamTypeCode,
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
