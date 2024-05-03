package io.kawoolutions.bbstats;

import io.kawoolutions.bbstats.dto.FinalScoreStatus;
import io.kawoolutions.bbstats.util.GameUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameUtilTests {

    @Test
    public void shouldReturnFinalScoreStatusFuture() {
        // no final scores, not withdrawn, tipoff is clearly in the future
        LocalDateTime tipoff = LocalDateTime.now().plusDays(8);
        assertEquals(FinalScoreStatus.FUTURE, GameUtil.getFinalScoreStatusFor(-1, -1, false, tipoff));
    }

    @Test
    public void shouldReturnFinalScoreStatusPreview() {
        // no final scores, not withdrawn, tipoff is within 7 days in the future
        LocalDateTime tipoff = LocalDateTime.now().plusDays(6);
        assertEquals(FinalScoreStatus.PREVIEW, GameUtil.getFinalScoreStatusFor(-1, -1, false, tipoff));
    }

    @Test
    public void shouldReturnFinalScoreStatusInProgress() {
        // no final scores, not withdrawn, tipoff was exactly 1 hour ago
        LocalDateTime tipoff = LocalDateTime.now().minusHours(1);
        assertEquals(FinalScoreStatus.IN_PROGRESS, GameUtil.getFinalScoreStatusFor(-1, -1, false, tipoff));
    }

    @Test
    public void shouldReturnFinalScoreStatusInPlayed() {
        // final scores (not being 0:0, 20:0 or 0:20), not withdrawn, tipoff is at least 2 hours in the past
        LocalDateTime tipoff = LocalDateTime.now().minusHours(2);
        assertEquals(FinalScoreStatus.PLAYED, GameUtil.getFinalScoreStatusFor(70, 60, false, tipoff));
    }

    public static Stream<Arguments> provideRatedPairs() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(20, 0),
                Arguments.of(0, 20)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRatedPairs")
    public void shouldReturnFinalScoreStatusRated(int homeFinalScore, int awayFinalScore) {
        // final scores (being 0:0, 20:0 or 0:20), not withdrawn, tipoff is at least 2 hours in the past
        LocalDateTime tipoff = LocalDateTime.now().minusHours(2);
        assertEquals(FinalScoreStatus.RATED, GameUtil.getFinalScoreStatusFor(homeFinalScore, awayFinalScore, false, tipoff));
    }

    @Test
    public void shouldReturnFinalScoreStatusOverdue() {
        // no final scores, not withdrawn, tipoff was exactly 26 hours ago (2 hours game time + 24 waiting time)
        LocalDateTime tipoff = LocalDateTime.now().minusHours(26);
        assertEquals(FinalScoreStatus.OVERDUE, GameUtil.getFinalScoreStatusFor(-1, -1, false, tipoff));
    }

    @Test
    public void shouldReturnFinalScoreStatusNeverPlayed() {
        // no final scores, withdrawn, tipoff doesn't matter
        assertEquals(FinalScoreStatus.NEVER_PLAYED, GameUtil.getFinalScoreStatusFor(-1, -1, true, null));
    }
}
