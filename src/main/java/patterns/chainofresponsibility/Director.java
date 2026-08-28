package patterns.chainofresponsibility;

/**
 * Approves purchases up to <b>$100,000</b> (inclusive). Title:
 * {@code "Director"}.
 */
public class Director extends Approver {

    @Override
    protected boolean canApprove(PurchaseRequest request) {
        if (request.amount() <= 100_000) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected String title() {
        return "Director";
    }
}
