package onlinebankingsystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseManager {
    private static final String url = "jdbc:sqlite:database.db";
    private static Connection connection = null;

   

   // Add new User 
    public static void addNewUser(String userName , String email , String password) {
        if (connection != null) {
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
                        System.out.println("Your uniq_id is " + uniqId + " " +  "plese save and take note this id for later processing.  ");  
                        
                        System.out.println();
                        String userInfo = getUserInfo(uniqId);
                        System.out.println(userInfo);
                        
                    }
                    
                }
            } catch (SQLException e) {
                System.out.println("SQL Eror: " + e.getMessage());
            } finally {
                closeConnection(); // close connection  
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
            LoginManager.logout();
            closeConnection();
        }
    }
    return false;
}

   //update user email and password 
 public static void updateUserInfo(int uniq_Id, String newEmail, String newPassword) {
    if (LoginManager.isLoggedIn() && LoginManager.getLoggedInUserId() == uniq_Id) {
        getConnection();
        if (connection != null) {
            String sql = "UPDATE Users SET email = ?, password = ? WHERE uniq_Id = ?";

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
            } finally {
                closeConnection();
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
                System.out.println("Database Connection Closed.");
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

