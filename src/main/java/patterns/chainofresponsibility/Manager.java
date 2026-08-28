package patterns.chainofresponsibility;

/**
 * Approves purchases up to <b>$10,000</b> (inclusive). Title:
 * {@code "Manager"}.
 */
public class Manager extends Approver {

    @Override
    protected boolean canApprove(PurchaseRequest request) {
        if (request.amount() <= 10_000) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected String title() {
        return "Manager";
    }
}
