import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Base Task class
abstract class Task {
    protected boolean completed;
    protected String description;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    public void mark() {
        this.completed = true;
    }

    public void unmark() {
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        String output = (this.completed ? "[X] " : "[ ] ");
        return output + this.description;
    }

    // TodoTask subclass
    static class TodoTask extends Task {
        public TodoTask(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    // DeadlineTask subclass with LocalDateTime
    static class DeadlineTask extends Task {
        private LocalDateTime by;

        public DeadlineTask(String description, LocalDateTime by) {
            super(description);
            this.by = by;
        }

        public LocalDateTime getBy() {
            return by;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
            return "[D]" + super.toString() + " (by: " + this.by.format(formatter) + ")";
        }
    }

    // EventTask subclass with LocalDateTime
    static class EventTask extends Task {
        private LocalDateTime from;
        private LocalDateTime to;

        public EventTask(String description, LocalDateTime from, LocalDateTime to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        public LocalDateTime getFrom() {
            return from;
        }

        public LocalDateTime getTo() {
            return to;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
            return "[E]" + super.toString() + " (from: " + this.from.format(formatter) + " to: " + this.to.format(formatter) + ")";
        }
    }
}
