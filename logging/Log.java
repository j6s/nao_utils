package de.dhbw.wwi13b.shared.logging;

/**
 * Simple logger inspired by the android Logger.
 * To improve clarity, the single character methods were replaced by
 * fully named ones
 *
 * @link https://developer.android.com/reference/android/util/Log.html
 * @author Johannes Hertenstein
 */
public class Log {

    /**
     * Log level that is to be used for very verbose messages that would
     * otherwise pollute the console
     */
    public final static int VERBOSE = 0;

    /**
     * Log level that is to be used for debugging messages
     */
    public final static int DEBUG = 1;

    /**
     * Log level that is to be used for general infos
     */
    public final static int INFO = 2;

    /**
     * Log level that is to be used for warnings
     */
    public final static int WARN = 3;

    /**
     * Log level that is to be used for errors
     */
    public final static int ERROR = 4;

    /**
     * Log level that is to be used for fatal errors that are
     * non-recoverable
     */
    public final static int FATAL = 5;

    /**
     * Current log level. Everything with a lower log level will not be
     * printed to the console
     */
    protected static int LEVEL = INFO;

    public static void setLogLevel(int level) {
        Log.LEVEL = level;
    }

    /**
     * Returns the logging prefix for a given log level
     * @param level
     * @return
     */
    protected static String getPrefix(int level) {
        switch (level) {
            case FATAL:
                return "!!!!! [FATAL]";
            case ERROR:
                return "!!! [ERROR]";
            case WARN:
                return "[WARN]";
            case DEBUG:
                return "[debug]";
            case INFO:
                return "[info]";
            case VERBOSE:
            default:
                return "";
        }
    }

    /**
     * Sends a logging message for the given log level
     * @param level
     * @param tag
     * @param message
     */
    public static void log(int level, String tag, String message) {
        if (level < LEVEL) {
            return;
        }

        String logMessage = System.currentTimeMillis() + " " + getPrefix(level) + " [" + tag + "] " + message;
        switch(level) {
            case ERROR:
            case FATAL:
                System.err.println(logMessage);
                break;
            default:
                System.out.println(logMessage);
                break;
        }
    }

    /**
     * Sends a log message with the log level {@link Log#VERBOSE}
     * @param tag
     * @param message
     */
    public static void verbose(String tag, String message) {
        log(VERBOSE, tag, message);
    }

    /**
     * Sends a log message with the log level {@link Log#DEBUG}
     * @param tag
     * @param message
     */
    public static void debug(String tag, String message) {
        log(DEBUG, tag, message);
    }

    /**
     * Sends a log message with the log level {@link Log#INFO}
     * @param tag
     * @param message
     */
    public static void info(String tag, String message) {
        log(INFO, tag, message);
    }

    /**
     * Sends a log message with the log level {@link Log#WARN}
     * @param tag
     * @param message
     */
    public static void warn(String tag, String message) {
        log(WARN, tag, message);
    }

    /**
     * Sends a log message with the log level {@link Log#ERROR}
     * @param tag
     * @param message
     */
    public static void error(String tag, String message) {
        log(ERROR, tag, message);
    }

    /**
     * Sends a log message with the log level {@link Log#FATAL}
     * @param tag
     * @param message
     */
    public static void fatal(String tag, String message) {
        log(FATAL, tag, message);
    }

}
