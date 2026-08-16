package transaction.outinbox.outbox.event;

import transaction.outinbox.outbox.enums.AggregateType;

public abstract class MessageEvent {

    private Long outboxId;

    public abstract String getEventType();
    public abstract Long getEventId();
    public abstract AggregateType getAggregateType();
    public abstract String getPayload();

    public void setOutboxId(Long outboxId) {
        this.outboxId = outboxId;
    }

    public Long getOutboxId() {
        return outboxId;
    }
}
