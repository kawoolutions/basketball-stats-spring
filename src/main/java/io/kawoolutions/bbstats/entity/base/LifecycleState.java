package io.kawoolutions.bbstats.entity.base;

public enum LifecycleState {
    NEW,  // marked as to be persisted
    LIVE, // marked as to be merged (modified or unmodified)
    DEAD  // marked as to be deleted
}

