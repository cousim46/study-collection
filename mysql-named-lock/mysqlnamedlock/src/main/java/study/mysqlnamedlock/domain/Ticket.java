package study.mysqlnamedlock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {
    @Id
    private Long id;

    private String name;
    private int amount;
    public Ticket(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public void decrease() {
        amount--;
    }

    public Ticket(Long id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }
}
