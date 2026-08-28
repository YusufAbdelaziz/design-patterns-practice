package patterns.chainofresponsibility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Behavior spec for the Chain of Responsibility exercise. Green = done. */
class PurchaseApprovalTest {

    /** The standard org chain: Team Lead -> Manager -> Director -> CFO. */
    private static Approver orgChain() {
        TeamLead lead = new TeamLead();
        lead.linkTo(new Manager()).linkTo(new Director()).linkTo(new CFO());
        return lead;
    }

    private static Approval approve(double amount) {
        return orgChain().handle(new PurchaseRequest("widgets", amount));
    }

    @Test
    void smallPurchaseApprovedByTeamLead() {
        Approval result = approve(500);

        assertTrue(result.approved());
        assertEquals("Team Lead", result.approver());
    }

    @Test
    void teamLeadApprovesRightUpToItsLimit() {
        assertEquals("Team Lead", approve(1_000).approver());
    }

    @Test
    void justOverTeamLeadEscalatesToManager() {
        assertEquals("Manager", approve(1_000.01).approver());
    }

    @Test
    void midSizePurchaseEscalatesToDirector() {
        assertEquals("Director", approve(50_000).approver());
    }

    @Test
    void largePurchaseEscalatesToCFO() {
        assertEquals("CFO", approve(750_000).approver());
    }

    @Test
    void firstCapableApproverHandlesIt() {
        // A Manager *could* approve $500, but the request must stop at the
        // first approver in the chain that can — the Team Lead.
        assertEquals("Team Lead", approve(500).approver());
    }

    @Test
    void beyondEveryLimitIsDenied() {
        Approval result = approve(5_000_000);

        assertFalse(result.approved());
        assertNull(result.approver());
    }

    @Test
    void soleApproverDeniesWhatItCannotApprove() {
        // A lone Team Lead with no one to escalate to must deny, not approve.
        TeamLead lead = new TeamLead();

        Approval result = lead.handle(new PurchaseRequest("server rack", 5_000));

        assertFalse(result.approved());
    }
}
