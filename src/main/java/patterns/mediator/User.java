package patterns.mediator;

import java.util.ArrayList;
import java.util.List;

public class User {

  private String name;

  private Mediator mediator;

  private List<Message> messages = new ArrayList<>();

  public User(String name, Mediator mediator) {
    this.name = name;
    this.mediator = mediator;
  }

  public String getName() {
    return name;
  }

  public void send(String message) {
    mediator.sendMessage(message, this);
  }

  public List<String> formattedMessages() {
    return messages.stream().map((msg) -> msg.toString()).toList();
  }

  public void receive(Message message) {
    messages.add(message);
  }

}
