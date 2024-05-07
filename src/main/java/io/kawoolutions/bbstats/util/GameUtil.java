package io.kawoolutions.bbstats.util;

import java.time.LocalDateTime;
import java.util.Map;

import io.kawoolutions.bbstats.dto.FinalScoreStatus;
import io.kawoolutions.bbstats.entity.Game;
import io.kawoolutions.bbstats.entity.HomeAway;
import io.kawoolutions.bbstats.entity.Score;

import static io.kawoolutions.bbstats.dto.FinalScoreStatus.FUTURE;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.IN_PROGRESS;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.REPORT_PENDING;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.REGULARLY_PLAYED;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.PREVIEW;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.FORMALLY_RATED;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.REPORT_OVERDUE;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.WITHDRAWN_AFTER_BEING_PLAYED;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.WITHDRAWN_BEFORE_BEING_PLAYED;

public final class GameUtil {

    private GameUtil() {
    }

    public static FinalScoreStatus getFinalScoreStatusFor(Game game) {
//        System.out.println( "getFinalScoreStatusFor(): " + game );

        Map<HomeAway, Score> scores = game.getScores();

        if (scores == null) {
            return FinalScoreStatus.IN_PROGRESS;
        }

//        System.out.println( "Scores: " + scores );

        Score homeScore = scores.get(HomeAway.HOME);
        Score awayScore = scores.get(HomeAway.AWAY);

//        System.out.println( "Score home: " + homeScore );
//        System.out.println( "Score away: " + awayScore );

        // scores may be null for games that haven't been played yet
        int homeFinalScore = homeScore != null && homeScore.getFinalScore() != null ? homeScore.getFinalScore().intValue() : -1;
        int awayFinalScore = awayScore != null && awayScore.getFinalScore() != null ? awayScore.getFinalScore().intValue() : -1;

        LocalDateTime scheduledTipoff = game.getScheduledTipoff();
        LocalDateTime actualTipoff = game.getActualTipoff();
        LocalDateTime tipoff = actualTipoff != null ? actualTipoff : scheduledTipoff;

        return getFinalScoreStatusFor(homeFinalScore, awayFinalScore, false, tipoff);
    }

    public static FinalScoreStatus getFinalScoreStatusFor(int homeFinalScore, int awayFinalScore, boolean withdrawn, LocalDateTime tipoff) {

        boolean hasFinalScore = homeFinalScore > -1 && awayFinalScore > -1;

        if (withdrawn) {
            return hasFinalScore ? WITHDRAWN_AFTER_BEING_PLAYED : WITHDRAWN_BEFORE_BEING_PLAYED;
        }

        if (!hasFinalScore) {

            // "now" differs slightly per call
            LocalDateTime now = LocalDateTime.now();

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
                    return REPORT_PENDING;
                } else {
                    return REPORT_OVERDUE;
                }
            }
        }

        // game was played and entered... means either regular score or formal decision (score of 0:20, 20:0, or 0:0)
        if (homeFinalScore == 0 && awayFinalScore == 0 ||
                homeFinalScore == 20 && awayFinalScore == 0 ||
                homeFinalScore == 0 && awayFinalScore == 20) {
            return FORMALLY_RATED;
        }

        return REGULARLY_PLAYED;
    }
}
