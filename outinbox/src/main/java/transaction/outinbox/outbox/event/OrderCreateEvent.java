package transaction.outinbox.outbox.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tools.jackson.databind.json.JsonMapper;
import transaction.outinbox.outbox.enums.AggregateType;

@Getter
@AllArgsConstructor
public class OrderCreateEvent extends MessageEvent{
    private static final JsonMapper jsonMapper = JsonMapper.builder().build();

    private Long  orderId;
    private OrderPayload orderPayload;

    private static OrderCreateEvent of(Long orderId, OrderPayload orderPayload) {
        return new OrderCreateEvent(orderId, orderPayload);
    }

    @Override
    public String getEventType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getPayload() {
        try {
            return jsonMapper.writeValueAsString(orderPayload);
        } catch (Exception e) {
            throw new RuntimeException("payload 직렬화 실패", e);
        }
    }

    @Override
    public AggregateType getAggregateType() {
        return AggregateType.ORDERS;
    }

    @Override
    public Long getEventId() {
        return orderId;
    }
}
