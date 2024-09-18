package rabbitmq.receiver.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
public class SendMessage {
    private String blogAddress;
    private String blogName;
    private String blogSubject;

    @Override
    public String toString() {
        return "" +
            "블로그 주소='" + blogAddress + '\'' +
            ", 블로그 이름='" + blogName + '\'' +
            ", 블로그 주제='" + blogSubject + '\'' +
            '}';
    }
}
