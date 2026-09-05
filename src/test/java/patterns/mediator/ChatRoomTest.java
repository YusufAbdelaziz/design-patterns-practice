package patterns.mediator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/** Behavior spec for the Mediator exercise. Green = done. */
class ChatRoomTest {

    @Test
    void broadcastReachesEveryoneButTheSender() {
        ChatRoom room = new ChatRoom();
        User alice = new User("Alice", room);
        User bob = new User("Bob", room);
        User carol = new User("Carol", room);
        room.register(alice);
        room.register(bob);
        room.register(carol);

        alice.send("Hi everyone");

        assertEquals(List.of("Alice: Hi everyone"), bob.formattedMessages());
        assertEquals(List.of("Alice: Hi everyone"), carol.formattedMessages());
        assertTrue(alice.formattedMessages().isEmpty(), "sender should not receive their own message");
    }

    @Test
    void recipientsSeeTheSenderName() {
        ChatRoom room = new ChatRoom();
        User alice = new User("Alice", room);
        User bob = new User("Bob", room);
        room.register(alice);
        room.register(bob);

        bob.send("Hello Alice");

        assertEquals(List.of("Bob: Hello Alice"), alice.formattedMessages());
    }

    @Test
    void messagesAccumulateInOrder() {
        ChatRoom room = new ChatRoom();
        User alice = new User("Alice", room);
        User bob = new User("Bob", room);
        room.register(alice);
        room.register(bob);

        alice.send("first");
        alice.send("second");
        alice.send("third");
        assertEquals(List.of("Alice: first", "Alice: second", "Alice: third"), bob.formattedMessages());

    }

    @Test
    void aTwoWayConversation() {
        ChatRoom room = new ChatRoom();
        User alice = new User("Alice", room);
        User bob = new User("Bob", room);
        room.register(alice);
        room.register(bob);

        alice.send("hey Bob");
        bob.send("hey Alice");

        assertEquals(List.of("Bob: hey Alice"), alice.formattedMessages());
        assertEquals(List.of("Alice: hey Bob"), bob.formattedMessages());
    }

    @Test
    void broadcastingToAnEmptyRoomHarmsNoOne() {
        ChatRoom room = new ChatRoom();
        User loner = new User("Loner", room);
        room.register(loner);

        loner.send("anyone there?");

        assertTrue(loner.formattedMessages().isEmpty());
    }

    @Test
    void onlyRegisteredUsersReceive() {
        ChatRoom room = new ChatRoom();
        User alice = new User("Alice", room);
        User bob = new User("Bob", room);
        User lurker = new User("Lurker", room); // created but never registered
        room.register(alice);
        room.register(bob);

        alice.send("members only");

        assertEquals(List.of("Alice: members only"), bob.formattedMessages());
        assertTrue(lurker.formattedMessages().isEmpty(), "an unregistered user should hear nothing");
    }
}
