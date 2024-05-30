/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinebankingsystem;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author celil
 */   
         interface CoinGuess {
             String tossCoin();
             void guessTossedCoin();
}


     
public class Customer extends User implements CoinGuess{

    Scanner scanner = new Scanner(System.in);
    
    public Customer(String name,String email,String password) {
        super(name,email,password);

    }

    public void transferMoney() {
        System.out.println("Please enter the unique id of the person you want to send money and the amount of the money you want to send.");
        int reciverId = scanner.nextInt();
        double amount = scanner.nextDouble();
        
        DatabaseManager.transferMoney(reciverId, amount);
      
    }
    
    
    public void login() {

        DatabaseManager.login(email, password);

    }

    public void logout() {

        DatabaseManager.logOut();
    }

    
    @Override
    public void withdrawMoney() {
        
        double amount = 0;
        System.out.println("please enter the amount of money you want to draw");
        
        amount = scanner.nextDouble();
        if (LoginManager.isLoggedIn()) {
            balance = DatabaseManager.getUserBalance(LoginManager.getLoggedInUserId());
            if (balance >= amount) {
                balance -= amount;
                DatabaseManager.updateUserInfo(LoginManager.getLoggedInUserId(), balance);

            } else {
                System.out.println("YOU DON'T HAVE ENOUGH MONEY");

            }

        } else {
            System.out.println("you are not logged in");
        }

    }

    @Override
    public void depositMoney() {
       
       double amount;
        amount = 1.0;
        System.out.println("please enter the amount of money you want to deposit");
        amount = scanner.nextDouble();
        if (amount > 0) {
            if (LoginManager.isLoggedIn()) {
                balance = DatabaseManager.getUserBalance(LoginManager.getLoggedInUserId());
                balance += amount;
                DatabaseManager.updateUserInfo(LoginManager.getLoggedInUserId(), balance);

            } else {
                System.out.println("you need to login to deposit money");
            }
        } else {

            System.out.println("You can't deposit negative money");
        }
    }

    @Override
    public void guessTossedCoin() {
       int betAmount= 50;
        
         Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Coin Toss Game!");
        
        System.out.println("Enter your guess (Heads or Tails):");
        String guess = scanner.nextLine().trim().toLowerCase(); // Convert input to lowercase for case-insensitive comparison
        String result = tossCoin();

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
