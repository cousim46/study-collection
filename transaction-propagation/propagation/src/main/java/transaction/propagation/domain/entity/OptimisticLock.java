package transaction.propagation.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Version;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptimisticLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Version
    private Integer version;

    public OptimisticLock(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
