package barry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Task class represents a general task with a description and completion status.
 * This is an abstract base class for specific types of tasks, such as TodoTask, DeadlineTask, and EventTask.
 */
abstract class Task {
    protected boolean completed;
    protected String description;

    /**
     * Constructs a Task with the specified description.
     *
     * @param description The description of the task.
     */
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

    /**
     * Represents a Todo task that needs to be done, without any specific time constraints.
     */
    static class TodoTask extends Task {

        /**
         * Constructs a TodoTask with the specified description.
         *
         * @param description The description of the todo task.
         */
        public TodoTask(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    /**
     * Represents a Deadline task that has a specific due date and time.
     */
    static class DeadlineTask extends Task {
        private LocalDateTime by;

        /**
         * Constructs a DeadlineTask with the specified description and due date.
         *
         * @param description The description of the deadline task.
         * @param by The LocalDateTime by which the task is due.
         */
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

    /**
     * Represents an Event task that occurs at a specific time interval.
     */
    static class EventTask extends Task {
        private LocalDateTime from;
        private LocalDateTime to;

        /**
         * Constructs an EventTask with the specified description, start time, and end time.
         *
         * @param description The description of the event task.
         * @param from The start time of the event.
         * @param to The end time of the event.
         */
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
