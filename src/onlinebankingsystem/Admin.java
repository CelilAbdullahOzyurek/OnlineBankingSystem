/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinebankingsystem;

import java.util.ArrayList;

/**
 *
 * @author celil
 */
public class Admin {

    protected String name;
    protected String email;
    protected String password;

    public Admin(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    //view spesific user balance
    public double viewUserBalance(String email) {
      
        return DatabaseManager.getUserBalance(1);
        
    }
    
    
    //view all user 
    public  void viewAllUsers(){
    
     ArrayList<User> userList = DatabaseManager.getAllUsersInfo();   
     DatabaseManager.printUserList(userList);
     
         
    }
   
}
