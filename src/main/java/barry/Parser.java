package barry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The Parser class is responsible for interpreting user input and converting it into executable commands.
 * It parses different types of user commands and provides the appropriate Command object to be executed.
 */
public class Parser {

    /**
     * Parses the user input and returns the corresponding Command object.
     *
     * @param input The user input string to parse.
     * @return The Command object that corresponds to the user's input.
     * @throws BarryException If the input does not correspond to any known command, or if there is an error in the input format.
     */
    public static Command parse(String input) throws BarryException {
        assert input != null && !input.isEmpty() : "Input should not be null or empty";  // Assumption that the input should not be null or empty
        
        String[] parts = input.split(" ", 2);
        String commandWord = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandWord) {
            case "bye":
                return new Command.ExitCommand();
            case "list":
                return new Command.ListCommand();
            case "mark":
                assert isInteger(arguments) : "Arguments for 'mark' command should be an integer";  // Assumption that the argument for mark is a valid integer
                return new Command.MarkCommand(Integer.parseInt(arguments));
            case "unmark":
                assert isInteger(arguments) : "Arguments for 'unmark' command should be an integer";  // Assumption that the argument for unmark is a valid integer
                return new Command.UnmarkCommand(Integer.parseInt(arguments));
            case "todo":
                assert !arguments.isEmpty() : "Arguments for 'todo' should not be empty";  // Assumption that todo has a description
                return new Command.AddTodoCommand(arguments);
            case "find":
                assert !arguments.isEmpty() : "Arguments for 'find' should not be empty";  // Assumption that find command has a search term
                return new Command.FindCommand(arguments);
            case "deadline":
                String[] deadlineParts = arguments.split(" /by ");
                assert deadlineParts.length == 2 : "Deadline command should follow the format: 'task /by date'";  // Assumption that deadline follows the correct format
                LocalDateTime byDateTime = parseDateTime(deadlineParts[1].trim());
                return new Command.AddDeadlineCommand(deadlineParts[0].trim(), byDateTime);
            case "event":
                String[] eventParts = arguments.split(" /from ");
                assert eventParts.length == 2 : "Event command should follow the format: 'task /from date /to date'";  // Assumption that event command follows the correct format
                String[] timeParts = eventParts[1].split(" /to ");
                assert timeParts.length == 2 : "Event command should contain both 'from' and 'to' times";  // Assumption that the event has both start and end times
                LocalDateTime fromTime = parseDateTime(timeParts[0].trim());
                LocalDateTime toTime = parseDateTime(timeParts[1].trim());
                return new Command.AddEventCommand(eventParts[0].trim(), fromTime, toTime);
            case "delete":
                assert isInteger(arguments) : "Arguments for 'delete' command should be an integer";  // Assumption that the argument for delete is a valid integer
                return new Command.DeleteCommand(Integer.parseInt(arguments));
            case "help":
                return new Command.HelpCommand();
            default:
                throw new BarryException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Parses a date and time string in the format "d/M/yyyy HHmm" and converts it to a LocalDateTime object.
     *
     * @param dateTimeString The string representing the date and time.
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws BarryException If the input string is not in the correct format.
     */
    private static LocalDateTime parseDateTime(String dateTimeString) throws BarryException {
        assert dateTimeString != null && !dateTimeString.isEmpty() : "Date time string should not be null or empty";  // Assumption that the date string is valid
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new BarryException("The date and time format is incorrect. Please use d/M/yyyy HHmm format.");
        }
    }

    /**
     * Helper method to check if a string is an integer.
     *
     * @param str The string to check.
     * @return true if the string can be parsed as an integer, false otherwise.
     */
    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
