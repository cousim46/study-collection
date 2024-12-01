package transaction.propagation.domain.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transaction.propagation.domain.repository.OptimisticLockRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OptimisticLockService {
    private final OptimisticLockRepository optimisticLockRepository;

    public void update(Long id, String updateName) {
        OptimisticLock optimisticLock = optimisticLockRepository.findById(id).get();
        optimisticLock.updateName(updateName);
    }
}
