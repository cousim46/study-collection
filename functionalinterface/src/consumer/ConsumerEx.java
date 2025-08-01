package consumer;

import java.util.List;

public class ConsumerEx {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4);
        numbers.forEach(number -> System.out.println(number));
    }
}
