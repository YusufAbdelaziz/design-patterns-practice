package patterns.state;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Behavior spec for the State exercise. Green = done. */
class OrderTest {

    @Test
    void startsPending() {
        assertEquals("PendingState", new Order().stateName());
    }

    @Test
    void payingMovesToPaid() {
        Order order = new Order();

        assertEquals("Payment received.", order.pay());
        assertEquals("PaidState", order.stateName());
    }

    @Test
    void shippingBeforePaymentIsRejected() {
        Order order = new Order();

        assertEquals("Cannot ship: order not paid yet.", order.ship());
        assertEquals("PendingState", order.stateName());
    }

    @Test
    void deliveringBeforeShippingIsRejected() {
        Order order = new Order();
        order.pay();

        assertEquals("Cannot deliver: order not shipped yet.", order.deliver());
        assertEquals("PaidState", order.stateName());
    }

    @Test
    void cancellingWhilePendingJustCancels() {
        Order order = new Order();

        assertEquals("Order cancelled.", order.cancel());
        assertEquals("CancelledState", order.stateName());
    }

    @Test
    void payingTwiceIsRejected() {
        Order order = new Order();
        order.pay();

        assertEquals("Order is already paid.", order.pay());
        assertEquals("PaidState", order.stateName());
    }

    @Test
    void shippingAPaidOrder() {
        Order order = new Order();
        order.pay();

        assertEquals("Order shipped.", order.ship());
        assertEquals("ShippedState", order.stateName());
    }

    @Test
    void cancellingAPaidOrderRefunds() {
        Order order = new Order();
        order.pay();

        assertEquals("Order cancelled and payment refunded.", order.cancel());
        assertEquals("CancelledState", order.stateName());
    }

    @Test
    void theHappyPathReachesDelivered() {
        Order order = new Order();
        order.pay();
        order.ship();

        assertEquals("Order delivered.", order.deliver());
        assertEquals("DeliveredState", order.stateName());
    }

    @Test
    void cancellingAShippedOrderIsRejected() {
        Order order = new Order();
        order.pay();
        order.ship();

        assertEquals("Cannot cancel: order already shipped.", order.cancel());
        assertEquals("ShippedState", order.stateName());
    }

    @Test
    void deliveredIsTerminal() {
        Order order = new Order();
        order.pay();
        order.ship();
        order.deliver();

        assertEquals("Order is already delivered.", order.deliver());
        assertEquals("Cannot cancel: order already delivered.", order.cancel());
        assertEquals("DeliveredState", order.stateName());
    }

    @Test
    void cancelledIsTerminal() {
        Order order = new Order();
        order.cancel();

        assertEquals("Order was cancelled.", order.pay());
        assertEquals("Order was cancelled.", order.ship());
        assertEquals("Order is already cancelled.", order.cancel());
        assertEquals("CancelledState", order.stateName());
    }

    @Test
    void sameActionBehavesDifferentlyPerState() {
        // The essence of the pattern: cancel() means different things depending
        // on where the order is in its lifecycle.
        assertEquals("Order cancelled.", new Order().cancel()); // from Pending

        Order paid = new Order();
        paid.pay();
        assertEquals("Order cancelled and payment refunded.", paid.cancel()); // from Paid

        Order shipped = new Order();
        shipped.pay();
        shipped.ship();
        assertEquals("Cannot cancel: order already shipped.", shipped.cancel()); // from Shipped
    }
}
