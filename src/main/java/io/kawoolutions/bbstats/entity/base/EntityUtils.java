package io.kawoolutions.bbstats.entity.base;

import io.kawoolutions.bbstats.application.ProjectGlobals;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class EntityUtils {
    public static <T extends Entity<K>, K> String getPkStringFor(T entity) {
        Objects.requireNonNull(entity);

        K pk = entity.getPk();
        String pkString = pk != null ? pk.toString() : "pk=" + ProjectGlobals.NULL_STRING;
        String entityClassName = entity.getClass().getSimpleName();

        return entityClassName + "[" + pkString + "]";
    }

    /**
     * Return the list of PK strings for a pkString of the form Group[roundId|ordinalNbr], here
     * List of {"roundId", "ordinalNbr"}
     *
     * @param pkString
     * @return
     */
    public static List<String> getPkPartsFor(String pkString) {
        if (pkString == null || pkString.trim().isEmpty()) {
            throw new IllegalArgumentException("PK string is not valid!");
        }

        String partString = pkString.substring(pkString.indexOf("[") + 1, pkString.lastIndexOf("]"));
        return Arrays.asList(partString.split("|"));
    }

    /**
     * Collect PKs from a list of entities.
     *
     * @param entities
     * @return
     */
    public static <T extends Entity<K>, K> List<K> getPksFor(List<T> entities) {
        // Java 8 property collector
        return entities.stream().map(Entity::getPk).collect(Collectors.toList());
    }

    public static <T> void replaceEntitiesWith(List<T> entities, T replacement) {
        replaceEntitiesWith(entities, Arrays.asList(replacement));
    }

    /**
     * Updates all original entities/instances with the replacements given, if they are equal.
     * <p>
     * This method requires the equals() method to function properly.
     *
     * @param entities
     * @param replacements
     * @return
     */
    public static <T> void replaceEntitiesWith(List<T> entities, List<T> replacements) {
        Objects.requireNonNull(entities, "Entities is null!");
        Objects.requireNonNull(replacements, "Replacements is null!");

        for (T replacement : replacements) {
            // requires equals to be implemented correctly
            int index = entities.indexOf(replacement);

            if (index != -1) {
                entities.set(index, replacement);
            }
        }
    }
}
