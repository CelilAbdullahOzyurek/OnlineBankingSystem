/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinebankingsystem;

import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author celil
 */
public class Admin  extends User{

    public Admin(String name,String email,String password) {
        super(name,email,password);
    }
    Scanner scanner = new Scanner(System.in);
    //view spesific user balance
    public double viewUserBalance(String email) {
      
        return DatabaseManager.getUserBalance(1);
        
    }
    
    
    //view all user 
    public  void viewAllUsers(){
    
     ArrayList<Customer> userList = DatabaseManager.getAllUsersInfo();   
     DatabaseManager.printUserList(userList);
     
         
    }

    @Override
    public void withdrawMoney() {
        double amount = 0;
        System.out.println("please enter the amount of money you want to draw");
        amount = scanner.nextDouble();
        System.out.println();
        System.out.println("please enter the userId of the person you want to draw money");
        int giverId = scanner.nextInt();
        if (LoginManager.isLoggedIn()) {
            
            balance = DatabaseManager.getUserBalance(giverId);
            if (balance >= amount) {
                balance -= amount;
                
                DatabaseManager.updateUserInfo(giverId, balance);

            } else {
                System.out.println("user doesn't have enough money");

            }

        } else {
            System.out.println("you are not logged in");
        }


    }

    @Override
    public void depositMoney() {
      double amount = 0;
        System.out.println("please enter the amount of money you want to deposit");
       amount = scanner.nextDouble();
        System.out.println();
        System.out.println("please enter the userId of the person you want to deposit money");
       int getterId = scanner.nextInt();
        if (amount > 0) {
            if (LoginManager.isLoggedIn()) {
                balance = DatabaseManager.getUserBalance(getterId);
                balance += amount;
                DatabaseManager.updateUserInfo(getterId, balance);

            } else {
                System.out.println("you need to login to deposit money");
            }
        } else {

            System.out.println("You can't deposit negative money");
        }
    }

    
}
