package gis.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Common utilities.
 */
public final class Util {

    private Util() {
        // Utility class
    }

    /**
     * Returns a list consisting of the results of applying the given function to the elements of this list.
     */
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }
}
