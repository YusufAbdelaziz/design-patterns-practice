package patterns.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Behavior spec for the Command exercise. Green = done.
 *
 * <p>These tests reference the public API you must provide: a {@code Document}
 * (the thing being edited), two commands — {@code AppendCommand(Document, String)}
 * and {@code DeleteLastCommand(Document, int)} — and a {@code CommandHistory} that
 * runs commands and drives undo / redo. What ties the commands together, and how
 * each one reverses itself, is yours to design.
 */
class UndoRedoTest {

    @Test
    void executingCommandsEditsTheDocument() {
        Document doc = new Document();
        CommandHistory history = new CommandHistory();

        history.execute(new AppendCommand(doc, "Hello"));
        assertEquals("Hello", doc.text());

        history.execute(new AppendCommand(doc, " World"));
        assertEquals("Hello World", doc.text());
    }

    @Test
    void undoReversesTheLastCommand() {
        Document doc = new Document();
        CommandHistory history = new CommandHistory();

        history.execute(new AppendCommand(doc, "Hello"));
        history.execute(new AppendCommand(doc, " World"));

        history.undo();
        assertEquals("Hello", doc.text());

        history.undo();
        assertEquals("", doc.text());
    }

    @Test
    void deleteLastRemovesCharacters() {
        Document doc = new Document();
        CommandHistory history = new CommandHistory();

        history.execute(new AppendCommand(doc, "Hello"));
        history.execute(new DeleteLastCommand(doc, 2)); // drops "lo"

        assertEquals("Hel", doc.text());
    }

    @Test
    void undoRestoresDeletedCharacters() {
        Document doc = new Document();
        CommandHistory history = new CommandHistory();

        history.execute(new AppendCommand(doc, "Hello"));
        history.execute(new DeleteLastCommand(doc, 3)); // drops "llo" -> "He"
        assertEquals("He", doc.text());

        history.undo(); // must put "llo" back
        assertEquals("Hello", doc.text());
    }

    @Test
    void redoReappliesAnUndoneCommand() {
        Document doc = new Document();
        CommandHistory history = new CommandHistory();

        history.execute(new AppendCommand(doc, "Hi"));
        history.undo();
        assertEquals("", doc.text());

        history.redo();
        assertEquals("Hi", doc.text());
    }

    @Test
    void undoAndRedoOnEmptyHistoryDoNothing() {
        Document doc = new Document();
        CommandHistory history = new CommandHistory();

        history.undo(); // no command to undo
        history.redo(); // no command to redo

        assertEquals("", doc.text());
    }

    @Test
    void runningANewCommandClearsTheRedoStack() {
        Document doc = new Document();
        CommandHistory history = new CommandHistory();

        history.execute(new AppendCommand(doc, "A"));
        history.undo();                               // back to ""
        history.execute(new AppendCommand(doc, "B")); // new branch: "B"

        history.redo(); // the undone "A" must NOT come back
        assertEquals("B", doc.text());
    }

    @Test
    void aLongerUndoRedoSequence() {
        Document doc = new Document();
        CommandHistory history = new CommandHistory();

        history.execute(new AppendCommand(doc, "abc"));
        history.execute(new AppendCommand(doc, "def"));
        history.execute(new DeleteLastCommand(doc, 2)); // "abcd"
        assertEquals("abcd", doc.text());

        history.undo(); // undo delete -> "abcdef"
        assertEquals("abcdef", doc.text());
        history.undo(); // undo append "def" -> "abc"
        assertEquals("abc", doc.text());

        history.redo(); // redo append "def" -> "abcdef"
        assertEquals("abcdef", doc.text());
        history.redo(); // redo delete 2 -> "abcd"
        assertEquals("abcd", doc.text());
    }
}
