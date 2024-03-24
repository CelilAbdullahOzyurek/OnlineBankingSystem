/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinebankingsystem;

// Login control
public class LoginManager {
  private static int loggedInUserId = -1; 

    public static boolean login(int userId) {
        if (userId > 0) {
            loggedInUserId = userId;
            return true;
        } else {
            return false;
        }
    }

    public static boolean logout() {
        loggedInUserId = -1; // 
        return true;
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    public static boolean isLoggedIn() {
        return loggedInUserId > 0;
    }
}
    
    
    
    
    
    
    
    
    
    

