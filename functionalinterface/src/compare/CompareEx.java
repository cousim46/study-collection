package compare;

import java.util.Comparator;

public class CompareEx {

    public static void main(String[] args) {
        Comparator<Integer> cm = (num1, num2) -> num1.compareTo(num2);
        int negativeInteger = cm.compare(1, 2);
        int zero = cm.compare(2, 2);
        int positiveInteger = cm.compare(2, 1);
        System.out.println("negativeInteger = " + negativeInteger);
        System.out.println("zero = " + zero);
        System.out.println("positiveInteger = " + positiveInteger);
    }
}
