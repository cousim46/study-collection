package transaction.propagation.service.dto;


public record Result(
    String memberServiceTransactionName,
    Boolean isNewTransactionMemberService,
    String teamServiceTransactionName,
    Boolean isNewTransactionTeamService) {
}
