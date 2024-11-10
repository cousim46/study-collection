package transaction.propagation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.propagation.domain.entity.Member;
import transaction.propagation.domain.entity.Team;
import transaction.propagation.domain.repository.MemberRepository;
import transaction.propagation.domain.repository.TeamRepository;
import transaction.propagation.service.dto.Result;

@SpringBootTest
class TransactionTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
@DisplayName("기존 트랜잭션에서 롤백이 발생해도 새로운 트랜잭션은 커밋한다.")
    @Test
    void rollbackBaseTransactionButCommitNewTransaction() {
        //given
        String teamName = "팀 이름";
        List<String> memberNames = List.of("hoeStory");

        //when
        memberService.saveNew(memberNames, teamName);

        //then
        Optional<Team> team = teamRepository.findByName(teamName);
        Optional<Member> member = memberRepository.findByName(memberNames.get(0));
        Assertions.assertThat(team.isPresent()).isTrue();
        Assertions.assertThat(member.isPresent()).isFalse();
    }

    @DisplayName("기존 트랜잭션의 존재유무와 상관없이 새로운 트랜잭션을 생성한다.")
    @Test
    void ignoreBaseTransactionCreateNewTransaction() {
        //given && when
        Result result = memberService.isNewTransactionPropagationRequiresNew();

        //then
        Assertions.assertThat(result.memberServiceTransactionName())
            .isNotEqualTo(result.teamServiceTransactionName());
        Assertions.assertThat(result.isNewTransactionMemberService()).isTrue();
        Assertions.assertThat(result.isNewTransactionTeamService()).isTrue();
    }
    @Test
    @DisplayName("기존 트랜잭션(MemberService)이 존재하면 새로운 트랜잭션(TeamService)은 기존 트랜잭션에 합류한다.")
    void returnTrueNewTransaction() {
        //give && when
        Result result = memberService.isNewTransactionPropagationRequired();
        //then
        assertThat(result.memberServiceTransactionName()).isEqualTo(
            result.teamServiceTransactionName());
        assertThat(result.isNewTransactionMemberService()).isTrue();
        assertThat(result.isNewTransactionTeamService()).isFalse();
    }

    @DisplayName("기존 트랜잭션이 없으면 새로운 트랜잭션을 생성한다.")
    @Test
    void notBaseTransactionCreateNewTransaction() {
        //give && when
        Result result = memberService.notTransactionMemberServiceAndExistsTransactionTeamService();

        //then
        assertThat(result.memberServiceTransactionName()).isNull();
        assertThat(result.teamServiceTransactionName()).isNotNull();
        assertThat(result.isNewTransactionMemberService()).isNull();
        assertThat(result.isNewTransactionTeamService()).isTrue();
    }

    @DisplayName("합류하게 된 새로운 트랜잭션은 기존 트랜잭션이 커밋되면 커밋된다.")
    @Test
    void commitBaseTransactionAndCommitNewTransaction() {
        //given
        String teamName = "팀 이름";
        List<String> memberNames = List.of("hoeStory");

        //when
        memberService.save(memberNames, teamName);

        //then
        Team team = teamRepository.findByName(teamName).get();
        Member member = memberRepository.findByName(memberNames.get(0)).get();
        Assertions.assertThat(team).isNotNull();
        Assertions.assertThat(member).isNotNull();
    }

}