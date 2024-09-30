import javafx.application.Application;

/**
 * The Launcher class serves as an entry point for the JavaFX application.
 * It exists to circumvent potential classpath issues that can arise when
 * launching
 * a JavaFX application in certain environments.
 * 
 * In some cases, directly running a JavaFX `Application` subclass may cause
 * classpath problems, so this separate launcher class delegates the launch
 * to the `Application` class.
 */
public class Launcher {
    /**
     * The main method, which serves as the entry point for the Java application.
     * It launches the JavaFX application by calling `Application.launch` and
     * passing the `Main` class as the argument.
     * 
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application using the Main class as the Application
        // subclass.
        Application.launch(Main.class, args);
    }
}
