import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    public static Command parse(String input) throws BarryException {
        String[] parts = input.split(" ", 2);
        String commandWord = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandWord) {
            case "bye":
                return new Command.ExitCommand();
            case "list":
                return new Command.ListCommand();
            case "mark":
                return new Command.MarkCommand(Integer.parseInt(arguments));
            case "unmark":
                return new Command.UnmarkCommand(Integer.parseInt(arguments));
            case "todo":
                return new Command.AddTodoCommand(arguments);
            case "deadline":
                String[] deadlineParts = arguments.split(" /by ");
                if (deadlineParts.length < 2) throw new BarryException("The deadline format is incorrect.");
                LocalDateTime byDateTime = parseDateTime(deadlineParts[1].trim());
                return new Command.AddDeadlineCommand(deadlineParts[0].trim(), byDateTime);
            case "event":
                String[] eventParts = arguments.split(" /from ");
                if (eventParts.length < 2) throw new BarryException("The event format is incorrect.");
                String[] timeParts = eventParts[1].split(" /to ");
                if (timeParts.length < 2) throw new BarryException("The event time format is incorrect.");
                LocalDateTime fromTime = parseDateTime(timeParts[0].trim());
                LocalDateTime toTime = parseDateTime(timeParts[1].trim());
                return new Command.AddEventCommand(eventParts[0].trim(), fromTime, toTime);
            case "delete":
                return new Command.DeleteCommand(Integer.parseInt(arguments));
            default:
                throw new BarryException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeString) throws BarryException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new BarryException("The date and time format is incorrect. Please use d/M/yyyy HHmm format.");
        }
    }
}
