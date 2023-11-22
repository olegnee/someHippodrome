package org.oleg.nee.hippodrome;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    @Test
    public void testConstructorWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null),
                "Horses cannot be null.");
    }

    @Test
    public void testConstructorWithNullMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructorWithEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()),
                "Horses cannot be empty.");
    }

    @Test
    public void testConstructorWithEmptyListMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void testGetHorses() {
        List<Horse> originalHorses = createListOfHorses(30);
        Hippodrome hippodrome = new Hippodrome(originalHorses);

        List<Horse> retrievedHorses = hippodrome.getHorses();

        assertIterableEquals(originalHorses, retrievedHorses);
    }

    @Test
    public void testMoveMethodCallsMoveOnAllHorses() {
        List<Horse> mockHorses = createMockListOfHorses(50);

        Hippodrome hippodrome = new Hippodrome(mockHorses);

        hippodrome.move();

        for (Horse horse : mockHorses) {
            verify(horse).move();
        }
    }

    @Test
    public void testGetWinner() {
        List<Horse> horses = Arrays.asList(
                new Horse("Horse1", 10.0, 30.0),
                new Horse("Horse2", 15.0, 25.0),
                new Horse("Horse3", 12.0, 35.0),
                new Horse("Horse4", 18.0, 28.0),
                new Horse("Horse5", 20.0, 40.0)
        );

        Hippodrome hippodrome = new Hippodrome(horses);

        Horse winner = hippodrome.getWinner();

        Horse expectedWinner = horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .orElse(null);

        assertEquals(expectedWinner, winner);
    }

    private List<Horse> createMockListOfHorses(int numberOfHorses) {
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < numberOfHorses; i++) {
            mockHorses.add(mock(Horse.class));
        }
        return mockHorses;
    }
    private List<Horse> createListOfHorses(int numberOfHorses) {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < numberOfHorses; i++) {
            horses.add(new Horse("Horse" + (i + 1), 10.0, 0.0));
        }
        return horses;
    }
}
