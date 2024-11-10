package transaction.propagation.service.dto;

public record TeamResult(
    String transactionName,
    boolean isNewTransaction
) {

}
