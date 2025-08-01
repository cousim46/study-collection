package supplier;

import java.util.Optional;

public class SupplierEx {

    public static void main(String[] args) {
        String result = Optional.of("Supplier 함수형 인터페이스").orElseGet(() -> "기본값");
    }
}
