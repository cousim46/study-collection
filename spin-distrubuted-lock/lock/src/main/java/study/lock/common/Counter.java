package study.lock.common;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Counter {
    @Id
    private Long id;

    private int count;

    public Counter(Long id) {
        this.id = id;
    }

    public void increaseCounter() {
        count += 1;
    }
}
