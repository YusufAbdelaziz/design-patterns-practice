package patterns.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/** Behavior spec for the Iterator exercise. Green = done. */
class PlaylistTest {

    private static Playlist<String> of(String... items) {
        Playlist<String> p = new Playlist<>();
        for (String item : items) {
            p.add(item);
        }
        return p;
    }

    @Test
    void enhancedForWalksInInsertionOrder() {
        Playlist<String> p = of("a", "b", "c");

        List<String> seen = new ArrayList<>();
        for (String s : p) {
            seen.add(s);
        }

        assertEquals(List.of("a", "b", "c"), seen);
    }

    @Test
    void survivesArrayGrowthPastInitialCapacity() {
        Playlist<Integer> p = new Playlist<>();
        for (int i = 0; i < 10; i++) {
            p.add(i);
        }

        List<Integer> seen = new ArrayList<>();
        for (int n : p) {
            seen.add(n);
        }

        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), seen);
    }

    @Test
    void hasNextIsFalseOnEmptyPlaylist() {
        assertFalse(of().iterator().hasNext());
    }

    @Test
    void hasNextTracksRemainingItems() {
        Iterator<String> it = of("only").iterator();

        assertTrue(it.hasNext());
        assertEquals("only", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void nextThrowsWhenExhausted() {
        Iterator<String> it = of("x").iterator();
        it.next();

        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void twoIteratorsAdvanceIndependently() {
        Playlist<String> p = of("a", "b", "c");

        Iterator<String> first = p.iterator();
        Iterator<String> second = p.iterator();

        assertEquals("a", first.next());
        assertEquals("a", second.next());
        assertEquals("b", first.next());
        // second is untouched by first's progress
        assertEquals("b", second.next());
        assertEquals("c", second.next());
        // first still has one left
        assertEquals("c", first.next());
    }

    @Test
    void reversedWalksLastToFirst() {
        Playlist<String> p = of("a", "b", "c");

        List<String> seen = new ArrayList<>();
        for (String s : p.reversed()) {
            seen.add(s);
        }

        assertEquals(List.of("c", "b", "a"), seen);
    }

    @Test
    void reversedOfEmptyIsEmpty() {
        List<String> seen = new ArrayList<>();
        for (String s : of().reversed()) {
            seen.add(s);
        }

        assertTrue(seen.isEmpty());
    }

    @Test
    void reversedLeavesForwardOrderIntact() {
        Playlist<String> p = of("a", "b", "c");

        // consume the reverse view...
        for (String ignored : p.reversed()) {
            // no-op
        }

        // ...forward iteration is unaffected
        List<String> forward = new ArrayList<>();
        for (String s : p) {
            forward.add(s);
        }
        assertEquals(List.of("a", "b", "c"), forward);
    }
}
