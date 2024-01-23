package GenericFilter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * GenericFilter class
 * using for sorting list of objects in my program
 * @param <T>
 * @return list of objects sorted
 */
public class GenericFilter<T> {
    private Predicate<T> predicate;

    /**
     * GenericFilter constructor with predicate
     * @param predicate
     */
    public GenericFilter(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    /**
     * filter method
     * @param list
     * @return list of objects sorted
     */
    public List<T> filter(List<T> list) {
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
