package io.kawoolutions.bbstats.entity.base;

import java.util.stream.Collectors;

public abstract class BaseCompositePk implements CompositePk {
    private static final long serialVersionUID = 1L;

    /**
     * Return a string of the form "4195|DE|2019-03-23" (without the class name or anything).
     */
    @Override
    public String toString() {
        return getComponents().stream().map(Object::toString).collect(Collectors.joining("|"));
    }
}
