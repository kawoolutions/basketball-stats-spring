package io.kawoolutions.bbstats.dto;

public enum FinalScoreStatus {
    FUTURE,
    PREVIEW,
    IN_PROGRESS,
    REPORT_PENDING,
    REPORT_OVERDUE,
    REGULARLY_PLAYED,
    FORMALLY_RATED,
    WITHDRAWN_AFTER_BEING_PLAYED, // e.g. a team that was withdrawn near the end of the season
    WITHDRAWN_BEFORE_BEING_PLAYED  // e.g. a team that was withdrawn near the beginning of the season
}
