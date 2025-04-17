package main.exception;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());

    // ���� � ����� ����
    private static final String LOG_FILE_PATH = "errors.log";

    // ����������� ���� ������������� ��� �������� �������
    static {
        try {
            // ������� FileHandler ��� ������ ����� � ����
            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true); // true - ��� ���������� � ����
            fileHandler.setFormatter(new SimpleFormatter());

            // ��������� FileHandler � ������
            LOGGER.addHandler(fileHandler);

            // ������������� ������� �����������
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("������ ��� ��������� �������: " + e.getMessage());
        }
    }

    // ����� ��� ��������� SQLException (������ ������ � ����� ������)
    public static void handleSQLException(SQLException e) {
        String message = String.format("SQL ������: %s", e.getMessage());
        logError(message, e);
    }

    // ����� ��� ��������� ����������� ������
    public static void handleUnexpectedException(Exception e) {
        String message = String.format("����������� ������: %s", e.getMessage());
        logError(message, e);
    }

    // ����� ��� ��������� ������, ��������� � ����������� ������
    public static void handleDataNotFoundException(String entityName) {
        String message = String.format("������ �� ������� ��� ��������: %s", entityName);
        logError(message, null);
    }

    // ����� ��� ��������� ������, ��������� � ������-�������
    public static void handleBusinessLogicException(String message) {
        logError(message, null);
    }

    // ����� ��� ��������� ������ � ���-����
    public static void handleWebException(Throwable throwable) {
        logError("������ ���-�����������: " + throwable.getMessage(), throwable);
    }

    // �������� ������ � ����
    private static void logError(String message, Throwable throwable) {
        // �������� ������� ���� � �����
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // ��������� ���� � ����� � ���������
        String logMessage = String.format("[%s] %s", timestamp, message);

        // �������� ������ � ����
        if (throwable != null) {
            LOGGER.log(Level.SEVERE, logMessage, throwable);
        } else {
            LOGGER.log(Level.SEVERE, logMessage);
        }
    }
}
