package patterns.state;

/**
 * Delivered. A terminal state — every event is rejected.
 *
 * <table>
 * <caption>Behavior</caption>
 * <tr>
 * <td>pay</td>
 * <td>{@code "Order is already paid."}</td>
 * </tr>
 * <tr>
 * <td>ship</td>
 * <td>{@code "Order is already shipped."}</td>
 * </tr>
 * <tr>
 * <td>deliver</td>
 * <td>{@code "Order is already delivered."}</td>
 * </tr>
 * <tr>
 * <td>cancel</td>
 * <td>{@code "Cannot cancel: order already delivered."}</td>
 * </tr>
 * </table>
 */
public class DeliveredState extends State {

    public DeliveredState(Order order) {
        super(order);
    }

    @Override
    public String pay() {
        return "Order is already paid.";
    }

    @Override
    public String ship() {
        return "Order is already shipped.";
    }

    @Override
    public String deliver() {
        return "Order is already delivered.";
    }

    @Override
    public String cancel() {
        return "Cannot cancel: order already delivered.";
    }
}
