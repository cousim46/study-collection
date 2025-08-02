package predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateEx {

    public static List<Integer> getResult(Predicate<Integer> condition) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        return numbers.stream()
            .filter(condition)
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Predicate<Integer> predicate = (num) -> num > 0;
        boolean isGrater = predicate.test(1);
        List<Integer> result = getResult((num) -> num % 2 == 0);
        for (Integer i : result) {
            System.out.println("i = " + i);
        }
    }
}
