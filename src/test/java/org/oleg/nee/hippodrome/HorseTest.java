package org.oleg.nee.hippodrome;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;

public class HorseTest {

    @Test
    public void testConstructorWithNullName() {
        String name = null;
        double speed = 10.0;
        double distance = 20.0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void testConstructorWithBlankName(String blankName) {
        double speed = 10.0;
        double distance = 20.0;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(blankName, speed, distance));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void testConstructorWithNegativeNumberInSpeedParameter() {
        String name = "someName";
        double speed = -10.0;
        double distance = 20.0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void testConstructorWithNegativeNumberInDistanceParameter() {
        String name = "someName";
        double speed = 10.0;
        double distance = -20.0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void testGetName() {
        String name = "HorseMan";
        double speed = 10.0;
        double distance = 20.0;
        Horse horse = new Horse(name, speed, distance);

        String result = horse.getName();

        assertEquals(name, result);
    }

    @Test
    public void testGetDistance() {
        String name = "HorseMan";
        double speed = 10.0;
        double distance = 20.0;
        Horse horse = new Horse(name, speed, distance);

        double result = horse.getDistance();

        assertEquals(distance, result, 0.0001);
    }

    @Test
    public void testGetSpeed() {
        String name = "Bucephalus";
        double speed = 10.0;
        double distance = 20.0;
        Horse horse = new Horse(name, speed, distance);

        double result = horse.getSpeed();

        assertEquals(speed, result, 0.0001);
    }

    @Test
    public void testMoveMethod() {
        String name = "HorseMan";
        double speed = 10.0;
        double distance = 20.0;

        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            double mockedRandomValue = 0.5;
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(mockedRandomValue);

            Horse horse = new Horse(name, speed, distance);

            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));

            double expectedDistance = distance + speed * mockedRandomValue;
            assertEquals(expectedDistance, horse.getDistance(), 0.0001);
        }
    }
}
