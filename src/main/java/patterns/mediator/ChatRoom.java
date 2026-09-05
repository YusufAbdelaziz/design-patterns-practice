package patterns.mediator;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements Mediator {

  private List<User> users = new ArrayList<>();

  @Override
  public void sendMessage(String message, User user) {
    for (User u : users) {
      if (u != user) {
        u.receive(new Message(user.getName(), message));
      }
    }
  }

  @Override
  public void register(User user) {
    users.add(user);
  }

}
