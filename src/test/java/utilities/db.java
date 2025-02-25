package utilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
public class db {
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    static {
        // First, try to retrieve database connection details from environment variables
        URL = System.getenv("DB_URL");
        USERNAME = System.getenv("DB_USERNAME");
        PASSWORD = System.getenv("DB_PASSWORD");

        System.out.println("Environment Variables:");
        System.out.println("DB_URL: " + URL);
        System.out.println("DB_USERNAME: " + USERNAME);
        System.out.println("DB_PASSWORD: " + PASSWORD);

        if (URL != null && USERNAME != null && PASSWORD != null) {
            System.out.println("Credentials picked from environment variables");
        } else {
            // If environment variables are not set, fall back to the config.properties file
            try (FileInputStream input = new FileInputStream("config.properties")) {
                Properties properties = new Properties();
                properties.load(input);
                URL = properties.getProperty("db.url");
                USERNAME = properties.getProperty("db.username");
                PASSWORD = properties.getProperty("db.password");
                System.out.println("Credentials picked from config.properties file");
                System.out.println("db-url: " + URL);
                System.out.println("db_USERNAME: " + USERNAME);
                System.out.println("db_PASSWORD: " + PASSWORD);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // If neither environment variables nor properties file provide the details, throw an exception
        if (URL == null || USERNAME == null || PASSWORD == null) {
            throw new RuntimeException("Database connection details are not set in environment variables or config.properties file.");
        }
    }

    // Method to establish a connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Method to insert data into the database
    public static void insertUser(String username, String password) {
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            // Check if the user already exists
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                // User does not exist, insert the new user
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.executeUpdate();
                System.out.println("User inserted successfully!");
            } else {
                // User already exists, ignore the insertion
                System.out.println("User already exists, insertion ignored.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve credentials from the database should be static to be called from other classes or methods
    public static String[] getCredentials() {
        String query = "SELECT username, password FROM users LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                return new String[]{username, password};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve data from the database
    public static void getUsers() {
        String query = "SELECT * FROM users";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Password: " + resultSet.getString("password"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to remove duplicate users from the database
    public static void removeDuplicateUsers() {
        String deleteDuplicatesQuery = "DELETE FROM users WHERE id NOT IN (SELECT id FROM (SELECT MIN(id) as id FROM users GROUP BY username) as temp)";
        try (Connection connection = getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteDuplicatesQuery)) {
            int rowsDeleted = deleteStatement.executeUpdate();
            System.out.println("Number of duplicate users removed: " + rowsDeleted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to close the connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}