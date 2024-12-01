package transaction.propagation.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.propagation.domain.entity.OptimisticLock;

@SpringBootTest
class OptimisticLockRepositoryTest {
    @Autowired
    private OptimisticLockRepository optimisticLockRepository;

    @BeforeEach
    void save() {
        optimisticLockRepository.save(new OptimisticLock("테스트1"));
        optimisticLockRepository.save(new OptimisticLock("테스트2"));
    }

    @DisplayName("")
    @Test
    void test() {
        //given
        OptimisticLock 테스트1 = optimisticLockRepository.findByName("테스트1").get();
        OptimisticLock 테스트2 = optimisticLockRepository.findByName("테스트2").get();

        //when

        //then

    }

}