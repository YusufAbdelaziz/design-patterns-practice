package patterns.chainofresponsibility;

/**
 * A request to buy something, flowing through the approval chain.
 *
 * @param item   what is being purchased
 * @param amount the cost, in dollars
 */
public record PurchaseRequest(String item, double amount) {}
