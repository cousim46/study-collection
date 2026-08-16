package transaction.outinbox.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transaction.outinbox.outbox.dto.OrderCreateRequest;
import transaction.outinbox.outbox.dto.Product;
import transaction.outinbox.outbox.dto.ProductJpaRepository;
import transaction.outinbox.outbox.event.OrderCreateEvent;
import transaction.outinbox.outbox.event.OrderPayload;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersJpaRepository ordersJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void save(OrderCreateRequest request) {
        Product product = productJpaRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        Orders save = ordersJpaRepository.save(Orders.create(product, request.getQuantity()));
        applicationEventPublisher.publishEvent(
            new OrderCreateEvent(save.getId(),
                new OrderPayload(product.getName(),product.getPrice(), save.getQuantity(), "주문 와료"))
        );


    }
}
