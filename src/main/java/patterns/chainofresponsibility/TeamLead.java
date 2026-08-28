package patterns.chainofresponsibility;

/**
 * Approves purchases up to <b>$1,000</b> (inclusive). Title:
 * {@code "Team Lead"}.
 */
public class TeamLead extends Approver {

    @Override
    protected boolean canApprove(PurchaseRequest request) {
        if (request.amount() <= 1000) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected String title() {
        return "Team Lead";
    }
}
