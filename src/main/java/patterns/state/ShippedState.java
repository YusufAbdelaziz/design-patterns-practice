package patterns.state;

/**
 * In transit. Too late to cancel.
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
 * <td>→ {@code DeliveredState}; {@code "Order delivered."}</td>
 * </tr>
 * <tr>
 * <td>cancel</td>
 * <td>{@code "Cannot cancel: order already shipped."}</td>
 * </tr>
 * </table>
 */
public class ShippedState extends State {

    public ShippedState(Order order) {
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
        order.setState(order.deliveredState());
        return "Order delivered.";
    }

    @Override
    public String cancel() {
        return "Cannot cancel: order already shipped.";
    }
}
