package transaction.outinbox.outbox.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageStatus {
    READY("메시지 발행 준비"), PUBLISHED("메시지 발행");
    private final String name;
}
