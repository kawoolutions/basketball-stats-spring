package io.kawoolutions.bbstats.util;

import java.time.LocalDateTime;
import java.util.Map;

import io.kawoolutions.bbstats.dto.FinalScoreStatus;
import io.kawoolutions.bbstats.entity.Game;
import io.kawoolutions.bbstats.entity.Score;

import static io.kawoolutions.bbstats.dto.FinalScoreStatus.FUTURE;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.IN_PROGRESS;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.NEVER_PLAYED;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.PENDING;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.PLAYED;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.PREVIEW;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.RATED;
import static io.kawoolutions.bbstats.dto.FinalScoreStatus.SCORE_OVERDUE;

public abstract class GameUtil {

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
}
