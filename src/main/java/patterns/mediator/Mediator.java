package patterns.mediator;

public interface Mediator {

  void sendMessage(String message, User user);

  void register(User user);
}
