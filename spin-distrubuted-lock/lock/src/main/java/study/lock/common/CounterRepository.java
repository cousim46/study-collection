package study.lock.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CounterRepository extends JpaRepository<Counter, Long> {

    @Query(value = """
        SELECT GET_LOCK(:lockName, :second)
        """, nativeQuery = true)
    int acquireLock(@Param("lockName") String lockName, @Param("second") long second);

    @Query(value = """
        SELECT RELEASE_LOCK(:lockName)
        """, nativeQuery = true)
    int releaseLock(@Param("lockName") String lockName);
}
