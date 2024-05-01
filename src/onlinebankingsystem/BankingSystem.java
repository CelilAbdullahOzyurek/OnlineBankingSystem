/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinebankingsystem;

/**
 *
 * @author celil
 */
public class BankingSystem {

    public static  void drawMoney(double amount) {
        double balance = 0;

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

    public static void depositMoney(double amount) {
        double balance;

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

}
