package io.kawoolutions.bbstats.util;

import io.kawoolutions.bbstats.dto.FinalScoreStatus;
import io.kawoolutions.bbstats.entity.AgeGroup;
import io.kawoolutions.bbstats.entity.Club;
import io.kawoolutions.bbstats.entity.Competition;
import io.kawoolutions.bbstats.entity.CompetitionLabel;
import io.kawoolutions.bbstats.entity.Game;
import io.kawoolutions.bbstats.entity.Group;
import io.kawoolutions.bbstats.entity.Person;
import io.kawoolutions.bbstats.entity.Player;
import io.kawoolutions.bbstats.entity.PlayerStat;
import io.kawoolutions.bbstats.entity.Roster;
import io.kawoolutions.bbstats.entity.Round;
import io.kawoolutions.bbstats.entity.Score;
import io.kawoolutions.bbstats.entity.Season;
import io.kawoolutions.bbstats.entity.Stat;
import io.kawoolutions.bbstats.entity.Team;
import io.kawoolutions.bbstats.entity.TeamMember;
import io.kawoolutions.bbstats.entity.TeamTypeGender;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static io.kawoolutions.bbstats.dto.FinalScoreStatus.FUTURE;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.IN_PROGRESS;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.NEVER_PLAYED;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.PENDING;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.PLAYED;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.PREVIEW;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.RATED;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.SCORE_OVERDUE;

public class NamingUtils {

    public static String getSeasonLabelFor(Season season) {
        return getSeasonLabelForStartYear(season.getStartYear().intValue());
    }

    /**
     * Not overloaded due to EL under EclipseLink not able to find method.
     *
     * @param startYear
     * @return
     */
    public static String getSeasonLabelForStartYear(int startYear) {
        // "2018/19"
        return startYear + "/" + Integer.valueOf(startYear + 1).toString().substring(2);
    }

    public static String englishOrdinalFor(int number) {
        if (number <= 0) {
            return number + "th";
        }

        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};

        switch (number % 100) {
            case 11:
            case 12:
            case 13:
                return number + "th";

            default:
                return number + suffixes[number % 10];
        }
    }

    public static String localeOrdinalFor(int number, String localeString) {
        if (number <= 0) {
            return number + "th";
        }

        String ordinal = null;

        switch (localeString) {
            case "en_US":
                ordinal = NamingUtils.englishOrdinalFor(number);
                break;

            case "de_DE":
                ordinal = number + ".";
                break;

            default:
                throw new RuntimeException("Unsupported locale: " + localeString);
        }

        return ordinal;
    }

    public static String getClubLabelFor(Club club) {
        return club.getName() + " (" + club.getCode() + ")";
    }

    public static String getTeamIdentifierForScore(Score score) {
        return getTeamIdentifierForRoster(score.getRoster());
    }

    public static String getTeamIdentifierForRoster(Roster roster) {
        return getTeamIdentifierFor(roster.getTeam());
    }

    public static String getTeamIdentifierFor(Team team) {
        return team.getClub().getCode() + "-" + team.getTeamTypeCode() + "-" + team.getOrdinalNbr();
    }

    public static String getTeamLabelForScore(Score score) {
        return getTeamLabelForTeam(score.getRoster().getTeam());
    }

    public static String getTeamLabelForRoster(Roster roster) {
        return getTeamLabelForTeam(roster.getTeam());
    }

    public static String getTeamLabelForTeam(Team team) {
        Club club = team.getClub();

        return getTeamLabelFor(club.getName(), team.getOrdinalNbr().intValue(), club.getCode());
    }

    public static String getShortTeamLabelFor(String clubName, int teamNumber) {
        return NamingUtils.getTeamLabelFor(clubName, teamNumber, null);
    }

    public static String getTeamLabelFor(String clubName, int teamNumber, String clubCode) {
        Objects.requireNonNull(clubName, "Club name is null!");

        if (clubName.isEmpty()) {
            throw new IllegalArgumentException("Club name is empty!");
        }

        if (teamNumber <= 0) {
            throw new IllegalArgumentException("Team number is zero or less!");
        }

        // setting only last or first name leaves off comma
        String teamName = clubName + " " + teamNumber;

        if (clubCode != null) {
            teamName += " (" + clubCode + teamNumber + ")";
        }

        return teamName;
    }

    public static String getPersonNameFor(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }

    public static String getPersonNameFor(Player player) {
        return getPersonNameFor(player.getPerson());
    }

    public static String getFormalPersonNameFor(Player player) {
        return getFormalPersonNameFor(player.getPerson().getLastName(), player.getPerson().getFirstName(), player.getPerson().getIncognito().booleanValue());
    }

    public static String getFormalPersonNameFor(String lastName, String firstName, boolean incognito) {
        if (incognito && lastName != null) {
            // overwrite param, no good practice :-)
            lastName = NamingUtils.abbreviateLastName(lastName);
        }

        // setting only last or first name leaves off comma
        String formalName = (lastName != null ? lastName : "") + (lastName != null && firstName != null ? ", " : "") + (firstName != null ? firstName : "");

        return formalName;
    }

    public static String abbreviateLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return lastName;
        }

        String abbreviatedLastName = "";

        // split name by whitespace first
        String[] firstLevelFragments = lastName.split("\\s");

        for (String firstLevelFragment : firstLevelFragments) {
            // then split each fragment by hyphen
            String[] secondLevelFragments = firstLevelFragment.split("-");

            if (secondLevelFragments.length > 1) {
                for (String secondLevelFragment : secondLevelFragments) {
                    abbreviatedLastName += secondLevelFragment.substring(0, 1) + ".-";
                }
            } else {
                abbreviatedLastName += firstLevelFragment.substring(0, 1) + ". ";
            }

        }

        // remove trailing character
        return abbreviatedLastName.substring(0, abbreviatedLastName.length() - 1);
    }

    public static String getGroupIdentifierFor(Group group) {
        Round round = group.getRound();

        return getCompetitionIdentifierFor(round.getCompetition()) + round.getSeason().getStartYear() + group.getCode();
    }

    public static String getGroupLabelFor(Group group) {
        if (group == null) {
            return "GROUP IS NULL!";
        }

//        GroupLabel groupLabel = group.getGroupLabel();
        Round round = group.getRound();
        Competition competition = round.getCompetition();
        CompetitionLabel competitionLabel = competition.getCompetitionLabels().toArray(new CompetitionLabel[1])[0]; // the query ensures only one matching label
//        Competition competition = competition.getCompetition();
//        GeoContext gc = competition.getGeoContext();

//        System.out.println( "Comp type: " + competition.getType() );

        String competitionCode = null;
        String competitionName = null;

        if (competitionLabel != null) {
            competitionCode = competitionLabel.getCode();
            competitionName = competitionLabel.getName();
        } else {
            competitionCode = "?";
            competitionName = "???";
        }

        String groupCode = group.getCode();
        String groupOfficialCode = group.getOfficialCode();
//        String groupName = groupLabel.getName();
        String groupName = groupCode;

//        System.out.println( "PARAMS: " + competitionCode + ", " + competitionName + ", " + groupCode + ", " + groupOfficialCode + ", " + groupName );

        String label = NamingUtils.getGroupLabelFor(competitionCode, competitionName, groupCode, groupOfficialCode, groupName);
//        String label = NamingUtils.getGroupLabelFor( "honky", "tonky", groupCode, groupOfficialCode, "HONKYTONKY" );

        return label;
    }

    public static String getGroupLabelFor(String competitionCode, String competitionName,
                                          String groupCode, String groupOfficialCode, String groupName) {
        // group classification: "Südwest" -> empty string if no group classification
        String groupStr = " ";

        if (groupName != null) {
            // we have a joined name, e.g. "Südwest"

            groupStr += groupName;

            if (groupOfficialCode != null) {
                // for leagues having more than two groups
                groupStr += " (" + groupOfficialCode + ")";
            }
        } else {
            // no joined classification name, might be numeric
            boolean groupCodeNumeric = false;

            try {
                Integer.valueOf(groupCode);

                // no exception thrown, all OK, it's a numeric code
                groupCodeNumeric = true;
            } catch (Exception e) {
                // do nothing, it's a non-numeric code
            }

            if (groupCodeNumeric) {
                // numeric code are only numeric, no joined group name, take official code (if present)
                if (groupOfficialCode != null) {
                    groupStr += "(" + groupOfficialCode + ")";
                }
            } else {
                groupStr += "(" + groupCode + ")";
            }
        }

        String competitionLabel = competitionName + groupStr + (competitionCode != null ? " (" + competitionCode + ")" : "");

        return competitionLabel;
    }

    public static String getTeamTypeCodeFor(AgeGroup ageGroup, TeamTypeGender gender) {
        return ageGroup.name() + gender.name().charAt(0);
    }

    public static String getCompetitionIdentifierFor(Competition competition) {
        return competition.getGeoContextId() + "-" + competition.getTeamTypeCode() + "-" + competition.getType() + "-" + competition.getLevel();
    }

    public static String getCompetitionLabelFor(Competition competition) {
        CompetitionLabel competitionLabel = competition.getCompetitionLabels().toArray(new CompetitionLabel[1])[0]; // the query ensures only one matching label
//        Competition competition = competition.getCompetition();
//        GeoContext gc = competition.getGeoContext();

//        System.out.println( "Comp type: " + competition.getType() );

        String competitionCode = competitionLabel.getCode();
        String competitionName = competitionLabel.getName();

        return competitionName + (competitionCode != null ? " (" + competitionCode + ")" : "");
    }

    public static String getGameIdentifierFor(Game game) {
        return getGameIdentifierFor(game.getGroup(), game.getScores().get(Boolean.TRUE), game.getScores().get(Boolean.FALSE));
    }

    public static String getGameIdentifierFor(Group group, Score homeScore, Score awayScore) {
        return getGameIdentifierFor(group, homeScore.getRoster(), awayScore.getRoster());
    }

    public static String getGameIdentifierFor(Group group, Roster homeRoster, Roster awayRoster) {
        return getGroupIdentifierFor(group) + ":" + getTeamIdentifierForRoster(homeRoster) + "-" + getTeamIdentifierForRoster(awayRoster);
    }

    public static FinalScoreStatus getFinalScoreStatusFor(Game game) {
//        System.out.println( "getFinalScoreStatusFor(): " + game );

        Map<Boolean, Score> scores = game.getScores();

        if (scores == null) {
            return FinalScoreStatus.IN_PROGRESS;
        }

//        System.out.println( "Scores: " + scores );

        Score homeScore = scores.get(Boolean.TRUE);
        Score awayScore = scores.get(Boolean.FALSE);

//        System.out.println( "Score home: " + homeScore );
//        System.out.println( "Score away: " + awayScore );

        // scores may be null for games that haven't been played yet
        int homeFinalScore = homeScore != null && homeScore.getFinalScore() != null ? homeScore.getFinalScore().intValue() : -1;
        int awayFinalScore = awayScore != null && awayScore.getFinalScore() != null ? awayScore.getFinalScore().intValue() : -1;

        LocalDateTime scheduledTipoff = game.getScheduledTipoff();
        LocalDateTime actualTipoff = game.getActualTipoff();
        LocalDateTime tipoff = actualTipoff != null ? actualTipoff : scheduledTipoff;

        return getFinalScoreStatusFor(game.getId().intValue(), homeFinalScore, awayFinalScore, false, tipoff);
    }

    /**
     * @param gameId Game ID, mostly for debugging
     */
    public static FinalScoreStatus getFinalScoreStatusFor(int gameId, int homeFinalScore, int awayFinalScore, boolean withdrawn, LocalDateTime tipoff) {

        boolean hasFinalScore = homeFinalScore > -1 && awayFinalScore > -1;

        // "now" differs slightly per line instance
        LocalDateTime now = LocalDateTime.now();

        if (!hasFinalScore) {
            if (withdrawn) {
                return NEVER_PLAYED;
            }

            if (now.isBefore(tipoff)) {
                // we are before the game has started

                LocalDateTime oneWeekBeforeTipoff = tipoff.minusDays(7);

                if (now.isAfter(oneWeekBeforeTipoff)) {
                    return PREVIEW;
                } else {
                    return FUTURE;
                }
            } else {
                // game was tipped off

                // determine end of game
                LocalDateTime endOfGame = tipoff.plusHours(2);

                // if now is before game end, the game is in progress
                if (now.isBefore(endOfGame)) {
                    return IN_PROGRESS;
                }

                // not in progress, some state after end of game

                // determine 24 hours after end of game
                LocalDateTime twentyFourHoursAfterEndOfGame = endOfGame.plusHours(24);

                if (now.isBefore(twentyFourHoursAfterEndOfGame)) {
                    return PENDING;
                } else {
                    return SCORE_OVERDUE;
                }
            }
        }

        // game was played and entered... means either regular score or formal decision (score of 0:20, 20:0, or 0:0)
        if (homeFinalScore == 0 && awayFinalScore == 0 ||
                homeFinalScore == 20 && awayFinalScore == 0 ||
                homeFinalScore == 0 && awayFinalScore == 20) {
            return RATED;
        }

        return PLAYED;
    }

    public static String getPlayerStatLabelFor(PlayerStat playerStat) {
        TeamMember teamMember = playerStat.getTeamMember();
        Player player = teamMember.getPlayer();
        String playerLabel = NamingUtils.getFormalPersonNameFor(player);

        return playerLabel;
    }

    public static String getStatLabelFor(Stat stat) {
        return getPlayerStatLabelFor(stat.getPlayerStat()) + " @ " + stat.getPeriod();
    }
}
