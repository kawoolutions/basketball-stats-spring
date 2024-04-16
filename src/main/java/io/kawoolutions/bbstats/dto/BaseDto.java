package io.kawoolutions.bbstats.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    protected static final Logger log = LoggerFactory.getLogger(BaseDto.class);

    private List<String> headers;

    /**
     * I don't remember 100%, but I think this was just for the Eclipse HQL editor to show readable results!
     */
    public List<Object> getValues() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        String res = "";

        int len = headers != null ? headers.size() : 0;
        int i = 0;

        for (Object o : getValues()) {
            String value = o == null ? "null" : o.toString();
            String header = i < len ? headers.get(i) : "COL " + i;

            // calculate spaces (only if header wider than value)
            String spaces = "";

            if (header.length() > value.length()) {
                for (int j = 0; j < header.length() - value.length(); j++) {
                    spaces += " ";
                }
            }

            res += "|";

            // right justify if number
            boolean rightJustified = o instanceof Number || o == null;

            if (rightJustified) {
                // append spaces
                res += spaces + value;
            } else {
                // prepend spaces
                res += value + spaces;
            }

            i++;
        }

        return res + "|";
    }
}
