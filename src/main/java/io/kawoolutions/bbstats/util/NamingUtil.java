package io.kawoolutions.bbstats.util;

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

import java.util.Objects;

public final class NamingUtil {

    private NamingUtil() {
    }

    public static String convertToEnglishOrdinalStringFor(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number is negative!");
        }

        String[] suffixes = new String[] {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};

        return switch (number % 100) {
            case 11, 12, 13 -> number + "th";
            default -> number + suffixes[number % 10];
        };
    }

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

    public static String localeOrdinalFor(int number, String localeString) {
        return switch (localeString) {
            case "en_US" -> NamingUtil.convertToEnglishOrdinalStringFor(number);
            case "de_DE" -> number + ".";
            default -> throw new RuntimeException("Unsupported locale: " + localeString);
        };
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
        return NamingUtil.getTeamLabelFor(clubName, teamNumber, null);
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
            lastName = NamingUtil.abbreviateLastName(lastName);
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

        String label = NamingUtil.getGroupLabelFor(competitionCode, competitionName, groupCode, groupOfficialCode, groupName);
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

    public static String getPlayerStatLabelFor(PlayerStat playerStat) {
        TeamMember teamMember = playerStat.getTeamMember();
        Player player = teamMember.getPlayer();
        String playerLabel = NamingUtil.getFormalPersonNameFor(player);

        return playerLabel;
    }

    public static String getStatLabelFor(Stat stat) {
        return getPlayerStatLabelFor(stat.getPlayerStat()) + " @ " + stat.getPeriod();
    }
}
