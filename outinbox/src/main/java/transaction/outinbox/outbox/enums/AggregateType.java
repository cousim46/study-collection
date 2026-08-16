package transaction.outinbox.outbox.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AggregateType {
    ORDERS("Orders 테이블");
    private final String name;
}
