/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package onlinebankingsystem;

import java.util.Scanner;

/**
 *
 * @author celil
 */
public class User extends Admin {

    Scanner scanner = new Scanner(System.in);
    private double balance;
    private int uniq_Id;

    public User(String name, String email, String password) {
        super(name, email, password);

    }

    public void transferMoney() {
        System.out.println("Please enter the unique id of the person you want to send money and the amount of the money you want to send.");
        int reciverId = scanner.nextInt();
        double amount = scanner.nextDouble();
        
        DatabaseManager.transferMoney(reciverId, amount);
        

    }

    public void depositMoney() {
        System.out.println("Please enter the amount of money you want to deposit");

        double userInput = scanner.nextDouble();

        BankingSystem.depositMoney(userInput);

    }

    public void withdrawMoney() {
        System.out.println("Please enter the amount of money you want to withdraw");

        double userInput = scanner.nextDouble();

        BankingSystem.drawMoney(userInput);

    }

    public void createAccount() {

        DatabaseManager.addNewUser(name, email, password);
    }

    public void login() {

        DatabaseManager.login(email, password);

    }

    public void logout() {

        DatabaseManager.logOut();
    }

    public void closeAccount(int uniq_id) {

        System.out.println("Warning: Please use uniq_id for close the account and transfer or withdraw the money in your account before closing it.");
        System.out.print("Do you want to continue closing the account? (y/n): ");

        String userInput = scanner.nextLine().trim().toLowerCase();

        if (userInput.equals("y")) {
            DatabaseManager.closeAccount(uniq_id);
        } else if (userInput.equals("n")) {
            System.out.println("Account closure process cancelled.");
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }

        scanner.close();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUniqId() {
        return uniq_Id;
    }

    public void setUniqId(int uniq_Id) {
        this.uniq_Id = uniq_Id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
