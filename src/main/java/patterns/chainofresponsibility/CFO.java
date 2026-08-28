package patterns.chainofresponsibility;

/**
 * Top of the chain. Approves purchases up to <b>$1,000,000</b> (inclusive).
 * Anything larger falls off the end of the chain and is denied.
 * Title: {@code "CFO"}.
 */
public class CFO extends Approver {

    @Override
    protected boolean canApprove(PurchaseRequest request) {
        if (request.amount() <= 1000000) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected String title() {
        return "CFO";
    }
}
