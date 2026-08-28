package patterns.state;

/**
 * Cancelled. A terminal state — every event is rejected.
 *
 * <table>
 * <caption>Behavior</caption>
 * <tr>
 * <td>pay</td>
 * <td>{@code "Order was cancelled."}</td>
 * </tr>
 * <tr>
 * <td>ship</td>
 * <td>{@code "Order was cancelled."}</td>
 * </tr>
 * <tr>
 * <td>deliver</td>
 * <td>{@code "Order was cancelled."}</td>
 * </tr>
 * <tr>
 * <td>cancel</td>
 * <td>{@code "Order is already cancelled."}</td>
 * </tr>
 * </table>
 */
public class CancelledState extends State {

    public CancelledState(Order order) {
        super(order);
    }

    @Override
    public String pay() {
        return "Order was cancelled.";
    }

    @Override
    public String ship() {
        return "Order was cancelled.";
    }

    @Override
    public String deliver() {
        return "Order was cancelled.";
    }

    @Override
    public String cancel() {
        return "Order is already cancelled.";
    }
}
