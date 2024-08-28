package barry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BarryTest {

    private ByteArrayOutputStream outputStream;
    private Ui ui;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        // Setup to capture console output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        // Initialize the Ui, TaskList, and Storage for testing
        ui = new Ui();
        taskList = new TaskList();
        storage = new Storage("data/test_tasks.txt"); // Use a separate test file to avoid overwriting real data
    }

    @Test
    public void testAddDeadline() throws BarryException, IOException {
        // Prepare input for adding a deadline task
        String input = "deadline apple /by 2/2/2222 1900";
        Command command = Parser.parse(input);

        // Execute command
        command.execute(taskList, ui, storage);

        // Capture and validate the output
        String expectedOutput = "Got it. I've added this task:\n[D][ ] apple (by: Feb 02 2222, 07:00 PM)\n\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testAddTodo() throws BarryException, IOException {
        // Prepare input for adding a todo task
        String input = "todo apple";
        Command command = Parser.parse(input);

        // Execute command
        command.execute(taskList, ui, storage);

        // Capture and validate the output
        String expectedOutput = "Got it. I've added this task:\n[T][ ] apple\n\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}
