package io.kawoolutions.bbstats.dto;

import io.kawoolutions.bbstats.entity.Arena;
import io.kawoolutions.bbstats.entity.Club;
import io.kawoolutions.bbstats.entity.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArenaDto extends BaseDto {
    private static final long serialVersionUID = 1L;

    private Arena arena;
    //    private List<Tenancy> tenancies;
    private List<Club> clubs;

//    private String arenaLabel;

    private List<String> clubNames;
    private List<String> clubCodes;
    private List<String> clubLabels;

    // sort and filter strings
    private String clubNamesAsString;
    private String clubCodesAsString;
    private String clubLabelsAsString;

    private List<String> continentNames;
    private List<Country> countries;
    private List<String> regionNames;
    private List<String> stateNames;
    private List<String> districtNames;

    public ArenaDto() {
    }

    public ArenaDto(Arena arena) {
        this.arena = arena;
        this.clubs = arena.getClubs();

//        this.tenancies = arena.getTenancies();

//        Tenancy[] tenancies = arena.getTenancies().toArray( new Tenancy[arena.getTenancies().size()] );
//        Arrays.sort( tenancies, new TenancyComparator() );

//        clubs = tenancies.stream().map( Tenancy::getClub ).collect( Collectors.toList() );

//        System.out.println( "Arena " + arena.getName() + " is the home of " + clubs.size() + " clubs!" );

        clubNames = clubs.stream().map(Club::getName).collect(Collectors.toList());
        clubCodes = clubs.stream().map(Club::getCode).collect(Collectors.toList());
        clubLabels = new ArrayList<>();

        int index = 0;
        for (String clubCode : clubCodes) {
            clubLabels.add(index, clubNames.get(index) + " (" + clubCode + ")");

            index++;
        }

        clubLabelsAsString = String.join(", ", clubLabels);

//        System.out.println("arena.getGames().size(): " + arena.getGames().size());

        continentNames = new ArrayList<>();
        countries = new ArrayList<>();
        regionNames = new ArrayList<>();
        stateNames = new ArrayList<>();
        districtNames = new ArrayList<>();

        //
//        String clubs = !clubCodes.isEmpty() ? " (" + StringUtils.join( clubCodes, ", " ) + ")" : "";
//        arenaLabel = arena.getName() + clubs;
    }

    // arena info

    public Arena getArena() {
        return arena;
    }

    // clubs

    public List<Club> getClubs() {
        return clubs;
    }

    public List<String> getClubNames() {
        return clubNames;
    }

    public List<String> getClubCodes() {
        return clubCodes;
    }

    public String getClubNamesAsString() {
        return clubNamesAsString;
    }

    public String getClubCodesAsString() {
        return clubCodesAsString;
    }

    public String getClubLabelsAsString() {
        return clubLabelsAsString;
    }

    // geo info

    public List<String> getContinentNames() {
        return continentNames;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<String> getRegionNames() {
        return regionNames;
    }

    public List<String> getStateNames() {
        return stateNames;
    }

    public List<String> getDistrictNames() {
        return districtNames;
    }

    // as string

    public String getContinentNamesAsString() {
        return String.join(",", continentNames);
    }

    public String getCountryNamesAsString() {
        return String.join(",", getCountries().stream().map(Country::getName).collect(Collectors.toList()));
    }

    public String getRegionNamesAsString() {
        return String.join(",", regionNames);
    }

    public String getStateNamesAsString() {
        return String.join(",", stateNames);
    }

    public String getDistrictNamesAsString() {
        return String.join(",", districtNames);
    }

//    /**
//     * Sorts usage entities by club code (String natural order)
//     */
//    public static class TenancyComparator implements Comparator<Tenancy>
//    {
//        @Override
//        public int compare( Tenancy us1, Tenancy us2 )
//        {
//            if ( us1.equals( us2 ) )
//            {
//                return 0;
//            }
//
//            Club cl1 = us1.getClub();
//            Club cl2 = us2.getClub();
//
//            if ( cl1.equals( cl2 ) )
//            {
//                return 0;
//            }
//
//            return cl1.getCode().compareTo( cl2.getCode() );
//        }
//    }
}
