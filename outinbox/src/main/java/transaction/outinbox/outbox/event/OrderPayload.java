package transaction.outinbox.outbox.event;

public record OrderPayload(String productName,
                           int price,
                           int quantity,
                           String message) {

}
