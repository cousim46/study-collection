package rabbitmq.sender.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SendMessage {
    private String blogAddress;
    private String blogName;
    private String blogSubject;
}
