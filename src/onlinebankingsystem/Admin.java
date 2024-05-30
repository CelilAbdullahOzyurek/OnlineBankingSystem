/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinebankingsystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author celil
 */
public class Admin  extends User implements CoinGuess{

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

   @Override
    public void guessTossedCoin() {
       int betAmount= 100;
        String result="";
         Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Coin Toss Game!");
        
        System.out.println("Enter your guess (Heads or Tails):");
        String guess = scanner.nextLine().trim().toLowerCase(); // Convert input to lowercase for case-insensitive comparison
        result = tossCoin();
        if(guess=="heads" || result == "Tails"){
         result = "Heads";
        }
        
        
        System.out.println("Coin toss result: " + result);
        System.out.println(" your guess: "+ guess);
        
        

        if (guess.equals(result.toLowerCase())) {
            System.out.println("Congratulations! Your guess was correct! ");
            if (LoginManager.isLoggedIn()) {
                balance = DatabaseManager.getUserBalance(LoginManager.getLoggedInUserId());
                balance += betAmount;
                DatabaseManager.updateUserInfo(LoginManager.getLoggedInUserId(), balance);

            } else {
                System.out.println("you were not logged in to get the money");
            } 
    
        } else {
            System.out.println("Sorry! Your guess was incorrect!");
            
            if (LoginManager.isLoggedIn()) {
                balance = DatabaseManager.getUserBalance(LoginManager.getLoggedInUserId());
                balance -= betAmount;
                DatabaseManager.updateUserInfo(LoginManager.getLoggedInUserId(), balance);

            } else {
                System.out.println("you were not logged in and didn't lose any money");
            }
   
        }

        scanner.close();
    }
        
    @Override
    public String tossCoin() {
        Random random = new Random();
        int result = random.nextInt(2); // Generates either 0 or 1

        if (result == 0) {
            return "Heads";
        } else {
            return "Tails";
        }
    }

    
}
