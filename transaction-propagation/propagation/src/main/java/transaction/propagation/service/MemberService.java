package transaction.propagation.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import transaction.propagation.domain.entity.Member;
import transaction.propagation.domain.entity.Team;
import transaction.propagation.domain.repository.MemberRepository;
import transaction.propagation.service.dto.Result;
import transaction.propagation.service.dto.TeamResult;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final TeamService teamService;
    private final MemberRepository memberRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveNew(List<String> memberName, String teamName) {
        Team team = teamService.saveNew(teamName, memberName.size());
        List<Member> newMembers = memberName.stream()
            .filter(name -> memberRepository.findByName(name).isEmpty())
            .map(name -> Member.of(name, team))
            .collect(Collectors.toList());
        memberRepository.saveAll(newMembers);
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Result isNewTransactionPropagationRequiresNew() {
        String memberTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        boolean isMemberNewTransaction = TransactionAspectSupport.currentTransactionStatus()
            .isNewTransaction();
        TeamResult teamResult = teamService.isNewTransactionPropagationRequiresNew();
        return new Result(memberTransactionName,
            isMemberNewTransaction,
            teamResult.transactionName(),
            teamResult.isNewTransaction());
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public Result isNewTransactionPropagationRequired() {
        String memberTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        boolean isMemberNewTransaction = TransactionAspectSupport.currentTransactionStatus()
            .isNewTransaction();
        TeamResult teamResult = teamService.isNewTransactionPropagationRequired();
        return new Result(memberTransactionName,
            isMemberNewTransaction,
            teamResult.transactionName(),
            teamResult.isNewTransaction());
    }


    public Result notTransactionMemberServiceAndExistsTransactionTeamService() {
        TeamResult teamResult = teamService.isNewTransactionPropagationRequired();
        try {
            String memberTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
            boolean isMemberNewTransaction = TransactionAspectSupport.currentTransactionStatus()
                .isNewTransaction();
            return new Result(memberTransactionName,
                isMemberNewTransaction,
                teamResult.transactionName(),
                teamResult.isNewTransaction());
        }catch (NoTransactionException e) {
            return new Result(null,
                null,
                teamResult.transactionName(),
                teamResult.isNewTransaction());

        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(List<String> memberName, String teamName) {
        Team team = teamService.save(teamName, memberName.size());
        List<Member> newMembers = memberName.stream()
            .filter(name -> memberRepository.findByName(name).isEmpty())
            .map(name -> Member.of(name, team))
            .collect(Collectors.toList());
        memberRepository.saveAll(newMembers);
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
