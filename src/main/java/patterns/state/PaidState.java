package patterns.state;

/**
 * Paid, waiting to be shipped.
 *
 * <table>
 * <caption>Behavior</caption>
 * <tr>
 * <td>pay</td>
 * <td>{@code "Order is already paid."}</td>
 * </tr>
 * <tr>
 * <td>ship</td>
 * <td>→ {@code ShippedState}; {@code "Order shipped."}</td>
 * </tr>
 * <tr>
 * <td>deliver</td>
 * <td>{@code "Cannot deliver: order not shipped yet."}</td>
 * </tr>
 * <tr>
 * <td>cancel</td>
 * <td>→ {@code CancelledState};
 * {@code "Order cancelled and payment refunded."}</td>
 * </tr>
 * </table>
 */
public class PaidState extends State {

    public PaidState(Order order) {
        super(order);
    }

    @Override
    public String pay() {
        return "Order is already paid.";
    }

    @Override
    public String ship() {
        order.setState(order.shippedState());
        return "Order shipped.";
    }

    @Override
    public String deliver() {
        return "Cannot deliver: order not shipped yet.";
    }

    @Override
    public String cancel() {
        order.setState(order.cancelledState());
        return "Order cancelled and payment refunded.";
    }
}
