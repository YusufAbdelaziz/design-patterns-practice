package patterns.state;

/**
 * Order placed, awaiting payment. The starting state.
 *
 * <table>
 * <caption>Behavior</caption>
 * <tr>
 * <td>pay</td>
 * <td>→ {@code PaidState}; {@code "Payment received."}</td>
 * </tr>
 * <tr>
 * <td>ship</td>
 * <td>{@code "Cannot ship: order not paid yet."}</td>
 * </tr>
 * <tr>
 * <td>deliver</td>
 * <td>{@code "Cannot deliver: order not shipped yet."}</td>
 * </tr>
 * <tr>
 * <td>cancel</td>
 * <td>→ {@code CancelledState}; {@code "Order cancelled."}</td>
 * </tr>
 * </table>
 */
public class PendingState extends State {

    public PendingState(Order order) {
        super(order);
    }

    @Override
    public String pay() {
        order.setState(order.paidState());
        return "Payment received.";
    }

    @Override
    public String ship() {
        return "Cannot ship: order not paid yet.";
    }

    @Override
    public String deliver() {
        return "Cannot deliver: order not shipped yet.";
    }

    @Override
    public String cancel() {
        order.setState(order.cancelledState());
        return "Order cancelled.";
    }
}
