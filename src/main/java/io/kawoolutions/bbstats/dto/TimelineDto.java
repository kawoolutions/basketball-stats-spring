package io.kawoolutions.bbstats.dto;

import java.util.Arrays;
import java.util.List;

public class TimelineDto extends BaseDto {
    private static final long serialVersionUID = 1L;

    private final String teamName;
    private final boolean home;

    private final Long pointsQ1;
    private final Long pointsQ2;
    private final Long pointsQ3;
    private final Long pointsQ4;

    private final Long pointsOT1;
    private final Long pointsOT2;
    private final Long pointsOT3;
    private final Long pointsOT4;
    private final Long pointsOT5;
    private final Long pointsOT6;

    private final Long points;

//    /**
//     * Eclipse DALI compatibility constructor.
//     *
//     * @param clubName
//     * @param ordinalNumber
//     * @param home
//     * @param pointsQ1
//     * @param pointsQ2
//     * @param pointsQ3
//     * @param pointsQ4
//     */
//    public TimelineDto( String clubName, Integer ordinalNumber, boolean home, Integer pointsQ1, Integer pointsQ2, Integer pointsQ3,
//                        Integer pointsQ4 )
//    {
//        this( clubName, ordinalNumber, home, ( long ) pointsQ1, ( long ) pointsQ2, ( long ) pointsQ3, ( long ) pointsQ4, null, null, null,
//              null, null, null );
//    }

    public TimelineDto(String clubName, Integer ordinalNumber, boolean home, Long pointsQ1, Long pointsQ2, Long pointsQ3, Long pointsQ4) {
        this(clubName, ordinalNumber, home, pointsQ1, pointsQ2, pointsQ3, pointsQ4, null, null, null, null, null, null);
    }

    public TimelineDto(String clubName, Integer ordinalNumber, boolean home, Long pointsQ1, Long pointsQ2, Long pointsQ3, Long pointsQ4,
                       Long pointsOT1, Long pointsOT2, Long pointsOT3, Long pointsOT4, Long pointsOT5, Long pointsOT6) {
        this.home = home;

        this.teamName = clubName + " " + ordinalNumber;

        // make regular quarters non-null
        this.pointsQ1 = pointsQ1;
        this.pointsQ2 = pointsQ2;
        this.pointsQ3 = pointsQ3;
        this.pointsQ4 = pointsQ4;

        this.pointsOT1 = pointsOT1;
        this.pointsOT2 = pointsOT2;
        this.pointsOT3 = pointsOT3;
        this.pointsOT4 = pointsOT4;
        this.pointsOT5 = pointsOT5;
        this.pointsOT6 = pointsOT6;

//        this.pointsOT1 = 6L;
//        this.pointsOT2 = 7L;
//        this.pointsOT3 = 8l;
//        this.pointsOT4 = 9l;
//        this.pointsOT5 = 10l;
//        this.pointsOT6 = 11l;

        // add up points if not null
        long total = 0;

        total += pointsQ1 != null ? pointsQ1.longValue() : 0;
        total += pointsQ2 != null ? pointsQ2.longValue() : 0;
        total += pointsQ3 != null ? pointsQ3.longValue() : 0;
        total += pointsQ4 != null ? pointsQ4.longValue() : 0;

        total += pointsOT1 != null ? pointsOT1.longValue() : 0;
        total += pointsOT2 != null ? pointsOT2.longValue() : 0;
        total += pointsOT3 != null ? pointsOT3.longValue() : 0;
        total += pointsOT4 != null ? pointsOT4.longValue() : 0;
        total += pointsOT5 != null ? pointsOT5.longValue() : 0;
        total += pointsOT6 != null ? pointsOT6.longValue() : 0;

        this.points = total != 0 ? Long.valueOf(total) : null;
    }

    // line type info
    public boolean gethome() {
        return home;
    }

    public boolean home() {
        return home;
    }

    // team info
    public String getTeamName() {
        return teamName;
    }

    // points regular quarters
    public Long getPointsQ1() {
        return pointsQ1;
    }

    public Long getPointsQ2() {
        return pointsQ2;
    }

    public Long getPointsQ3() {
        return pointsQ3;
    }

    public Long getPointsQ4() {
        return pointsQ4;
    }

    // points overtimes
    public Long getPointsOT1() {
        return pointsOT1;
    }

    public Long getPointsOT2() {
        return pointsOT2;
    }

    public Long getPointsOT3() {
        return pointsOT3;
    }

    public Long getPointsOT4() {
        return pointsOT4;
    }

    public Long getPointsOT5() {
        return pointsOT5;
    }

    public Long getPointsOT6() {
        return pointsOT6;
    }

    public int getNumOts() {
        int numOts = 0;

        numOts += pointsOT1 != null ? 1 : 0;
        numOts += pointsOT2 != null ? 1 : 0;
        numOts += pointsOT3 != null ? 1 : 0;
        numOts += pointsOT4 != null ? 1 : 0;
        numOts += pointsOT5 != null ? 1 : 0;
        numOts += pointsOT6 != null ? 1 : 0;

        return numOts;
    }

    // total
    public Long getPoints() {
        return points;
    }

    // view line impl
    @Override
    public List<Object> getValues() {
        return Arrays.asList(teamName, Boolean.valueOf(home), pointsQ1, pointsQ2, pointsQ3, pointsQ4, pointsOT1, pointsOT2, pointsOT3, pointsOT4, pointsOT5, pointsOT6);
    }
}
