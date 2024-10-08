package io.kawoolutions.bbstats;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.kawoolutions.bbstats.dto.FinalScoreStatus;
import io.kawoolutions.bbstats.util.GameUtil;

class GameUtilTests {

    @Test
    public void shouldReturnFinalScoreStatusFuture() {
        // no final scores, not withdrawn, tipoff is clearly in the future
        LocalDateTime tipoff = LocalDateTime.now().plusDays(8);

        FinalScoreStatus status = GameUtil.getFinalScoreStatusFor(null, null, false, tipoff);

        assertEquals(FinalScoreStatus.FUTURE, status);
        assertThat(status).isEqualTo(FinalScoreStatus.FUTURE);
    }

    @Test
    public void shouldReturnFinalScoreStatusPreview() {
        // no final scores, not withdrawn, tipoff is within 7 days in the future
        LocalDateTime tipoff = LocalDateTime.now().plusDays(6);

        FinalScoreStatus status = GameUtil.getFinalScoreStatusFor(null, null, false, tipoff);

        assertEquals(FinalScoreStatus.PREVIEW, status);
        assertThat(status).isEqualTo(FinalScoreStatus.PREVIEW);
    }

    @Test
    public void shouldReturnFinalScoreStatusInProgress() {
        // no final scores, not withdrawn, tipoff was exactly 1 hour ago
        LocalDateTime tipoff = LocalDateTime.now().minusHours(1);

        FinalScoreStatus status = GameUtil.getFinalScoreStatusFor(null, null, false, tipoff);

        assertEquals(FinalScoreStatus.IN_PROGRESS, status);
        assertThat(status).isEqualTo(FinalScoreStatus.IN_PROGRESS);
    }

    @Test
    public void shouldReturnFinalScoreStatusPlayed() {
        // final scores (not being 0:0, 20:0 or 0:20), not withdrawn, tipoff is at least 2 hours in the past
        LocalDateTime tipoff = LocalDateTime.now().minusHours(2);

        FinalScoreStatus status = GameUtil.getFinalScoreStatusFor(70, 60, false, tipoff);

        assertEquals(FinalScoreStatus.REGULARLY_PLAYED, status);
        assertThat(status).isEqualTo(FinalScoreStatus.REGULARLY_PLAYED);
    }

    @Test
    public void shouldReturnFinalScoreStatusReportPending() {
        // no final scores, not withdrawn, tipoff was exactly 20 hours ago
        LocalDateTime tipoff = LocalDateTime.now().minusHours(20);

        FinalScoreStatus status = GameUtil.getFinalScoreStatusFor(null, null, false, tipoff);

        assertEquals(FinalScoreStatus.REPORT_PENDING, status);
        assertThat(status).isEqualTo(FinalScoreStatus.REPORT_PENDING);
    }

    @Test
    public void shouldReturnFinalScoreStatusReportOverdue() {
        // no final scores, not withdrawn, tipoff was exactly 26 hours ago (2 hours game time + 24 waiting time)
        LocalDateTime tipoff = LocalDateTime.now().minusHours(26);

        FinalScoreStatus status = GameUtil.getFinalScoreStatusFor(null, null, false, tipoff);

        assertEquals(FinalScoreStatus.REPORT_OVERDUE, status);
        assertThat(status).isEqualTo(FinalScoreStatus.REPORT_OVERDUE);
    }

    public static Stream<Arguments> provideFormallyRatedScores() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(20, 0),
                Arguments.of(0, 20));
    }

    @ParameterizedTest
    @MethodSource("provideFormallyRatedScores")
    public void shouldReturnFinalScoreStatusFormallyRated(int homeFinalScore, int awayFinalScore) {
        // final scores (being 0:0, 20:0 or 0:20), not withdrawn, tipoff is at least 2 hours in the past
        LocalDateTime tipoff = LocalDateTime.now().minusHours(2);

        FinalScoreStatus status = GameUtil.getFinalScoreStatusFor(homeFinalScore, awayFinalScore, false, tipoff);

        assertEquals(FinalScoreStatus.FORMALLY_RATED, status);
        assertThat(status).isEqualTo(FinalScoreStatus.FORMALLY_RATED);
    }

    @Test
    public void shouldReturnFinalScoreStatusWithdrawnBeforeBeingPlayed() {
        // no final scores, withdrawn, tipoff doesn't matter
        FinalScoreStatus status = GameUtil.getFinalScoreStatusFor(null, null, true, null);

        assertEquals(FinalScoreStatus.WITHDRAWN_BEFORE_BEING_PLAYED, status);
        assertThat(status).isEqualTo(FinalScoreStatus.WITHDRAWN_BEFORE_BEING_PLAYED);
    }

    @Test
    public void shouldReturnFinalScoreStatusWithdrawnAfterBeingPlayed() {
        // final scores (not being 0:0, 20:0 or 0:20), withdrawn, tipoff doesn't matter
        FinalScoreStatus status = GameUtil.getFinalScoreStatusFor(66, 77, true, null);

        assertEquals(FinalScoreStatus.WITHDRAWN_AFTER_BEING_PLAYED, status);
        assertThat(status).isEqualTo(FinalScoreStatus.WITHDRAWN_AFTER_BEING_PLAYED);
    }
}
