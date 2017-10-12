package challenge.stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class AppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        new App();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void itShouldShowTheFirstVowelEAsOutput() {
        String[] args = new String[1];
        args[0] = "aAbBABacafe";
        App.main(args);
        assertEquals("First char founded: e\n", outContent.toString());
    }

    @Test
    public void itShouldReturnMinValueOfCharacter() {
        String[] args = new String[1];
        args[0] = "aAbBABacafeTE";
        App.main(args);
        assertEquals("No matches were found :(\n", outContent.toString());
    }

    @Test
    public void itShouldShowArgumentExpectedAsOutput() {
        String[] args = new String[0];
        App.main(args);
        assertEquals("Argument expected :(\n", outContent.toString());
    }

}