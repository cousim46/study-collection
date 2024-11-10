package transaction.propagation.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import transaction.propagation.domain.entity.Team;
import transaction.propagation.domain.repository.TeamRepository;
import transaction.propagation.service.dto.TeamResult;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Team saveNew(String name, int newMemberCount) {
        Optional<Team> findTeam = teamRepository.findByName(name);
        Team team = null;
        if(findTeam.isPresent()) {
            team = findTeam.get();
            team.updateTotalCount(newMemberCount);
        }else {
            team = teamRepository.save(Team.of(name, newMemberCount));
        }
        return team;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TeamResult isNewTransactionPropagationRequiresNew() {
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        boolean isNewTransaction = TransactionAspectSupport.currentTransactionStatus()
            .isNewTransaction();
        return new TeamResult(currentTransactionName, isNewTransaction);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TeamResult isNewTransactionPropagationRequired() {
        String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        boolean isNewTransaction = TransactionAspectSupport.currentTransactionStatus()
            .isNewTransaction();
        return new TeamResult(currentTransactionName, isNewTransaction);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Team save(String name, int newMemberCount) {
        Optional<Team> findTeam = teamRepository.findByName(name);
        Team team = null;
        if(findTeam.isPresent()) {
            team = findTeam.get();
            team.updateTotalCount(newMemberCount);
        }else {
             team = teamRepository.save(Team.of(name, newMemberCount));
        }
        return team;
    }
}
