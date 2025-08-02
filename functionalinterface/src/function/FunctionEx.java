package function;

import java.util.List;
import java.util.function.Function;

public class FunctionEx {

    public static void main(String[] args) {
        Function<String, Integer> stringToInteger = (str) -> {
            try{
                return Integer.parseInt(str);
            }catch (NumberFormatException e) {
                throw new IllegalStateException("숫자로 변환할 수 없는 문자열입니다.");
            }
        };
        Integer apply = stringToInteger.apply("51");
        System.out.println("apply = " + apply);

        String convert = convert((String str) -> {
            return "Function Interface Ex".concat(str);
        }, "- concat");
        System.out.println("convert = " + convert);

        Integer stringToIntegerConvert = convert((str) -> {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                throw new IllegalStateException("숫자로 변환할 수 없는 문자열입니다.");
            }
        }, "-51");
        System.out.println("convert = " + stringToIntegerConvert);

        //Function 사용중인 코드
        List<String> strings = List.of("1", "2", "3", "4", "5");
        List<Integer> integers = strings.stream()
            .map(str -> Integer.parseInt(str))
            .toList();
    }

    public static <T,R> R convert(Function<T,R> function, T t) {
        return function.apply(t);
    }
}
