package onlinebankingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String url = "jdbc:sqlite:database.db";
    private static Connection connection = null;

    public static void transferMoney(int receiverId, double amount) {

        if (!LoginManager.isLoggedIn()) {
            System.out.println("Please login before transferring money.");
            return;
        }
        
        if (amount <= 0) {
        System.out.println("Invalid transfer amount. Transfer failed.");
        return;
    }

        int senderId = LoginManager.getLoggedInUserId();

        double senderBalance = DatabaseManager.getUserBalance(senderId);
        if (senderBalance < amount) {
            System.out.println("Insufficient funds. Transfer failed.");
            return;
        }

        String senderSql = "UPDATE Users SET balance = balance - ? WHERE uniq_Id = ?";
        String receiverSql = "UPDATE Users SET balance = balance + ? WHERE uniq_Id = ?";

        try (
                Connection connection = DriverManager.getConnection(url); PreparedStatement senderStatement = connection.prepareStatement(senderSql); PreparedStatement receiverStatement = connection.prepareStatement(receiverSql)) {

            senderStatement.setDouble(1, amount);
            senderStatement.setInt(2, senderId);
            int senderRowsUpdated = senderStatement.executeUpdate();

            receiverStatement.setDouble(1, amount);
            receiverStatement.setInt(2, receiverId);
            int receiverRowsUpdated = receiverStatement.executeUpdate();

            if (senderRowsUpdated > 0 && receiverRowsUpdated > 0) {
                System.out.println("Money transfer successful.");
            } else {
                System.out.println("Money transfer failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    // Chechhk user exiest!
    private static boolean checkUserExists(String email, String password) {
        String sql = "SELECT COUNT(*) AS count FROM Users WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; 
            }
        } catch (SQLException e) {

            System.out.println("SQL Error: " + e.getMessage());

        }
        return false;
    }

    // print user list
    public static void printUserList(ArrayList<Customer> userList) {
        System.out.println("User List:");
        System.out.println("----------------------------------");
        for (Customer user : userList) {
            System.out.println("Name: " + user.getName());
            System.out.println("Email " + user.getEmail());
            System.out.println("Balance: " + user.getBalance());
            System.out.println("Uniq_id: " + user.getUniqId());
            System.out.println("----------------------------------");
        }
    }

    // Get all user info in ArrayList
    public static ArrayList<Customer> getAllUsersInfo() {
        ArrayList<Customer> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection connection = DriverManager.getConnection(url); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                double balance = resultSet.getDouble("balance");
                int uniq_Id = resultSet.getInt("uniq_Id");

                Customer user = new Customer(name, email, password);
                user.setBalance(balance);
                user.setUniqId(uniq_Id);

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return users;
    }

    // Add new User 
    public static void addNewUser(String userName, String email, String password) {
        if (connection != null) {

            boolean userExists = false;
            userExists = checkUserExists(email, password);

            if (userExists) {
                System.out.println("A user with this email and password already exists. Please choose another email or password.");
                return;
            }

            String sql = "INSERT INTO Users (name , email , password) VALUES (? , ? , ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userName);
                statement.setString(2, email);
                statement.setString(3, password);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("New user added: " + userName + " " + "Welcome to our bank " + userName);

                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int uniqId = generatedKeys.getInt(1);
                        System.out.println("Your uniq_id is " + uniqId + " " + "plese save and take note this id for later processing.  ");

                        System.out.println();
                        String userInfo = getUserInfo(uniqId);
                        System.out.println(userInfo);

                    }

                }
            } catch (SQLException e) {
                System.out.println("SQL Eror: " + e.getMessage());
            } finally {
                closeConnection();
            }
        } else {
            System.out.println("Connection Eror.");
        }
    }

    // Login  Check
    public static boolean login(String email, String password) {
        getConnection();
        if (connection != null) {
            String sql = "SELECT uniq_Id FROM Users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int userId = resultSet.getInt("uniq_Id");
                    if (LoginManager.login(userId)) {
                        System.out.println("Login Success");
                        return true;
                    } else {
                        System.out.println("Session creation failed.");
                        return false;
                    }
                } else {
                    System.out.println("Username or password incorrect");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
            } finally {

            }
        }
        return false;
    }

    // Close Account
    public static void closeAccount(int uniq_id) {
        getConnection();
        if (connection != null) {

            double balance = getUserBalance(uniq_id);

            if (balance != 0) {
                System.out.println("Your balance is " + balance + ". You can't close your account.");
                System.out.println("Please transfer or withdraw your money.");
                return;
            }

            String sql = "DELETE FROM  Users WHERE  uniq_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, uniq_id);

                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {

                    System.out.println("Your bank account closed!");

                } else {

                    System.out.println("This user not found!");

                }

            } catch (SQLException e) {

                System.out.println("SQL Eror" + e.getMessage());

            }

        }

    }

    //LogOut process
    public static void logOut() {
        LoginManager.logout();
        closeConnection();
    }

    //update user email and password
    public static void updateUserInfo(int uniq_Id, String newEmail, String newPassword) {
        if (LoginManager.isLoggedIn() && LoginManager.getLoggedInUserId() == uniq_Id) {
            getConnection();
            if (connection != null) {
                String sql = "UPDATE Users SET email = ?, password = ?, balance = ? WHERE uniq_Id = ?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, newEmail);
                    statement.setString(2, newPassword);
                    statement.setInt(3, uniq_Id);

                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("User information updated");
                    } else {
                        System.out.println("An error occurred while updating user information. Please try again later");
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Error: " + e.getMessage());
                }
            }
        } else {
            System.out.println("You can't update user information without login or unauthorized access.");
        }
    }

    //Overloaded method from updateUserInfo
    public static void updateUserInfo(int uniq_Id, double balance) {
        if (LoginManager.isLoggedIn() && LoginManager.getLoggedInUserId() == uniq_Id) {
            getConnection();
            if (connection != null) {
                String sql = "UPDATE Users SET balance = ? WHERE uniq_Id = ? ";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setDouble(1, balance);
                    statement.setInt(2, uniq_Id);

                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("User information updated");
                    } else {
                        System.out.println("An error occurred while updating user information. Please try again later");
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Error: " + e.getMessage());
                }
            }
        } else {
            System.out.println("You can't update user information without login or unauthorized access.");
        }
    }

    // Get connection
    public static void getConnection() {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Database Connection Eror: " + e.getMessage());
        }
    }

    //  Close connection
    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("");
            }
        } catch (SQLException e) {
            System.out.println("Database Closing Eror: " + e.getMessage());
        }
    }

    // show user info 
    public static String getUserInfo(int uniqId) {
        String userInfo = "";
        if (connection != null) {
            String sql = "SELECT * FROM Users WHERE uniq_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, uniqId);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    double balance = resultSet.getDouble("balance");
                    int userId = resultSet.getInt("uniq_id");

                    userInfo += "User Info:\n";
                    userInfo += "Name: " + name + "\n";
                    userInfo += "Email: " + email + "\n";
                    userInfo += "Balance: " + balance + "\n";
                    userInfo += "Uniq_id: " + userId + "\n";
                }
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
            }
        } else {
            System.out.println("Connection Error.");
        }
        return userInfo;

    }

    //show user  balance info 
    public static double getUserBalance(int uniq_id) {
        double balance = 0;
        if (connection != null) {
            String sql = "SELECT balance FROM users WHERE uniq_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, uniq_id);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {

                    balance = resultSet.getDouble("balance");
                    return balance;

                }
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
            }

        } else {
            System.out.println("Connection Error.");
        }

        return balance;

    }

}
