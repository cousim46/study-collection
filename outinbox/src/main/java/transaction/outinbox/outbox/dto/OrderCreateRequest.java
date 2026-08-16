package transaction.outinbox.outbox.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class OrderCreateRequest {
    private Long productId;
    private int quantity;
}
