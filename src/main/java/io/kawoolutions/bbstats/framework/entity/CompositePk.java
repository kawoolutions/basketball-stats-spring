package io.kawoolutions.bbstats.framework.entity;

import java.io.Serializable;
import java.util.List;

public interface CompositePk extends Serializable {
    /**
     * Subclasses shall provide the list of components that the PK consists of so that the toString
     * implementation will build a toString in the form "4195|DE|2019-03-23" as the PK's string
     * representation (e.g. used for entity converters).
     *
     * @return
     */
    public List<Object> getComponents();
}
