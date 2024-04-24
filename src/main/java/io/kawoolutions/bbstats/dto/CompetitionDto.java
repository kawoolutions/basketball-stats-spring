package io.kawoolutions.bbstats.dto;

import io.kawoolutions.bbstats.entity.AgeGroup;
import io.kawoolutions.bbstats.entity.CompetitionType;
import io.kawoolutions.bbstats.entity.Country;
import io.kawoolutions.bbstats.entity.GeoContext;
import io.kawoolutions.bbstats.entity.GeoContextType;
import io.kawoolutions.bbstats.entity.TeamTypeGender;
import io.kawoolutions.bbstats.util.NamingUtil;

import java.util.ArrayList;
import java.util.List;

public class CompetitionDto extends BaseDto {
    private static final long serialVersionUID = 1L;

    private final String groupCode;
    private final String groupOfficialCode;
    private final String groupName;
    private final String groupLabel;

    private final Integer roundId;

    private final int competitionLevel;
    private final CompetitionType competitionType;

    private final String hierarchyLevelString;
    private final int countryHierarchyLevel;
//    private final int totalHierarchyLevels;

    private final String seasonLabel;

    private final String teamTypeCode;
    private final AgeGroup teamTypeAgeGroup;
    private final TeamTypeGender teamTypeGender;

    private final Integer geoContextId;
    private final GeoContextType geoContextType;

    private GeoContext geoContext;

    private String continentName;
    private String countryCode;
    private String regionName;
    private String stateName;
    private String districtName;

    private Country country;

    private long rosterCount;
    private final boolean populated;

    // debug constructors
//    public CompetitionListDto( Group group, String groupName, int seasonStartYear )
//    {
//        this( group, groupName, seasonStartYear, null, 66, 1950, "LALA", "Lala League" );
//    }
//    public CompetitionListDto( Group group, String groupName, int seasonStartYear,
//                               Round round, int groupCount,
//                               int competitionLevel, String competitionCode )
//    {
//        this( group, groupName, seasonStartYear, round, groupCount, competitionLevel, competitionCode, competitionCode,
//              AgeGroup.O50, TeamTypeGender.MIXED, CompetitionType.REGULAR_SEASON, "Kasi", new Country("DE", "Deu", "2"), false );
//    }

//    public CompetitionListDto( String groupCode, String groupOfficialCode, String groupName,
//                               int seasonStartYear,
//                               int competitionLevel, String competitionCode, String competitionLabel,
//                               String teamTypeCode, AgeGroup teamTypeAgeGroup, TeamTypeGender teamTypeGender,
//                               CompetitionType competitionType, long rosterCount )
//    {
//        this(null, groupName, seasonStartYear,
//             null,
//             competitionLevel, competitionCode, competitionLabel,
//             teamTypeCode, teamTypeAgeGroup, teamTypeGender,
//             competitionType, null, rosterCount);
//    }

//    public CompetitionListDto( Group group, String groupName, int seasonStartYear,
//                               Round round,
//                               int competitionLevel, String competitionCode, String competitionLabel,
//                               String teamTypeCode, AgeGroup teamTypeAgeGroup, TeamTypeGender teamTypeGender,
//                               CompetitionType competitionType, GeoContext geoContext, long rosterCount )
//    {
//
//    }

    public CompetitionDto(Integer roundId, String groupCode, String groupOfficialCode, String groupName, int seasonStartYear,
                          CompetitionType competitionType, int competitionLevel, String competitionCode, String competitionLabel,
                          String teamTypeCode, AgeGroup teamTypeAgeGroup, TeamTypeGender teamTypeGender,
                          Integer geoContextId, GeoContextType geoContextType,
                          long rosterCount)
//    String continentName, String countryName, String regionName, String stateName, String districtName,
    {
        this.groupCode = groupCode;
        this.groupOfficialCode = groupOfficialCode;
        this.groupName = groupName;

//        this.roundType = round != null ? round.getClass() : null;
        this.roundId = roundId;

        this.geoContextId = geoContextId;
        this.geoContextType = geoContextType;

        this.competitionLevel = competitionLevel;

        // continent = 0, country = 1, ...
        // concat string to sort table by: take ordinal for regular season only, the rest will sort by first char of type
        this.hierarchyLevelString = (geoContextType != null ? geoContextType.ordinal() + 1 : 666) + "-" + (competitionType != CompetitionType.REGULAR_SEASON ? competitionType.name().substring(0, 1) : Integer.valueOf(competitionLevel)) + " " + groupCode;

        int countryHierarchyLevel = -1;

        if (seasonStartYear < 2007) {
            switch (geoContextType) {
                case COUNTRY:
                    countryHierarchyLevel = competitionLevel;
                    break;

                case REGION:
                    countryHierarchyLevel = competitionLevel + 2;
                    break;

                case STATE:
                    countryHierarchyLevel = competitionLevel + 4;
                    break;

                case DISTRICT:
                    countryHierarchyLevel = competitionLevel + 6;
                    break;

                case CONTINENT:
                default:
                    break;
            }
        } else {
            switch (geoContextType) {
                case COUNTRY:
                    countryHierarchyLevel = competitionLevel;
                    break;

                case REGION:
                    countryHierarchyLevel = competitionLevel + 3;
                    break;

                case STATE:
                    countryHierarchyLevel = competitionLevel + 5;
                    break;

                case DISTRICT:
                    countryHierarchyLevel = competitionLevel + 7;
                    break;

                case CONTINENT:
                default:
                    break;
            }
        }

        this.countryHierarchyLevel = countryHierarchyLevel;

        this.seasonLabel = NamingUtil.getSeasonLabelForStartYear(seasonStartYear);

        this.teamTypeCode = teamTypeCode;
        this.teamTypeGender = teamTypeGender;

        // "O20", "U18", ...
        this.teamTypeAgeGroup = teamTypeAgeGroup;

        this.competitionType = competitionType;

        this.groupLabel = NamingUtil.getGroupLabelFor(competitionCode, competitionLabel, groupCode, groupOfficialCode, groupName) + ", {0}";

        // produce "10th league" etc.
//        Group g = group;
//        int parentCount = -1;
//        int childCount = 0;
//
//        if ( country != null )
//        {
//            // we have a non-continent context, country determinable
//            parentCount = 0;
//
//            // iterate bottom to top and count until continent (the nth league count is country-wide only)
//            while ( g != null && g.getRound().getCompetition().getGeoContext().getType() != GeoContextType.CONTINENT )
//            {
//                parentCount++;
//
//                List<Group> parentGroups = g.getParents();
//
//                if ( parentGroups.size() == 0 )
//                {
//                    break;
//                }
//
//                g = parentGroups.get( 0 );
//            }
//
//            // for CUP etc. competition types, we must count children
//            g = group;
//            childCount = 0;
//
////            if ( competitionType == CompetitionType.CUP )
//            {
//                // pick the first and rely on balanced trees!
//                List<Group> children = g.getChildren();
//
//                while ( !children.isEmpty() )
//                {
//                    childCount++;
//
//                    g = children.get( 0 );
//                    children = g.getChildren();
//                }
//            }
//        }
//
//        this.countryHierarchyLevel = parentCount;
//
//        // may be incomplete for REGULAR_SEASON competition type
//        this.totalHierarchyLevels = parentCount + childCount;

        this.rosterCount = rosterCount;
        this.populated = rosterCount > 0;
//        this.populated = false;
    }

    public int getCompetitionLevel() {
        return competitionLevel;
    }

    public String getHierarchyLevelString() {
        // "4-1"
        return hierarchyLevelString;
    }

    public int getCountryHierarchyLevel() {
        // 1 - n: country-wide level -> "10. Liga" or "10th league"
        return countryHierarchyLevel;
    }

//    public int getTotalHierarchyLevels()
//    {
//        return totalHierarchyLevels;
//    }

    // group info

    public String getGroupCode() {
        return groupCode;
    }

    public String getGroupOfficialCode() {
        return groupOfficialCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupLabel() {
        return groupLabel;
    }

    // round info

    public Integer getRoundId() {
        return roundId;
    }

    public String getSeasonLabel() {
        return seasonLabel;
    }

    // team type info

    public String getTeamTypeCode() {
        return teamTypeCode;
    }

    public TeamTypeGender getTeamTypeGender() {
        return teamTypeGender;
    }

    public AgeGroup getTeamTypeAgeGroup() {
        return teamTypeAgeGroup;
    }

    // competition info

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    // group members -> rosters

    public long getRosterCount() {
        return rosterCount;
    }

    public boolean isPopulated() {
        return populated;
    }

    // geo info

    public Integer getGeoContextId() {
        return geoContextId;
    }

    public GeoContextType getGeoContextType() {
        return geoContextType;
    }

    public GeoContext getGeoContext() {
        return geoContext;
    }

    public void setGeoContext(GeoContext geoContext) {
        this.geoContext = geoContext;

        // all names of a geo context (unknown values will be null)
        String[] geoNames = new String[5];

        List<String> geoNamesList = new ArrayList<>();

        GeoContext gc = geoContext;

        while (gc != null) {
//            System.out.println( "GC class: " + gc.getClass().getSimpleName() );

            if (gc instanceof Country) {
                country = (Country) gc;

//                System.out.println( "Country code for " + geoContext.getName() + " is " + country.getIsoCode() );
            }

            geoNamesList.add(0, gc instanceof Country ? ((Country) gc).getIsoCode() : gc.getName());
            gc = gc.getParent();

//            System.out.println( "setGeoContext for competition DTO: " + geoContext.getName() + ", parent = " + ( gc != null ? gc.getName() : null ) );
        }

        System.arraycopy(geoNamesList.toArray(new String[geoNamesList.size()]), 0, geoNames, 0, geoNamesList.size());

        this.continentName = geoNames[0];
        this.countryCode = geoNames[1];
        this.regionName = geoNames[2];
        this.stateName = geoNames[3];
        this.districtName = geoNames[4];
    }

    public String getContinentName() {
        return continentName;
    }

    public String getCountryCode() {
        return countryCode;
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
}
