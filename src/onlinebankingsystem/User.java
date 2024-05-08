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
public  abstract class User {
protected String name;
protected String email;
protected String password;
protected double balance;
protected int uniqId;

    public User(String name,String email,String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUniqId() {
        return uniqId;
    }

    public void setUniqId(int uniqId) {
        this.uniqId = uniqId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public   abstract void withdrawMoney();
        
    

    public abstract void depositMoney();
    
    
   //since creating an account is same for all users this is not an abstract method 
   public void createAccount() {

        DatabaseManager.addNewUser(name, email, password);
    }
   public void closeAccount(int uniq_id) {
       Scanner scanner = new Scanner(System.in);
        System.out.println("Warning: Please use uniq_id for close the account and transfer or withdraw the money in your account before closing it.");
        System.out.print("Do you want to continue closing the account? (y/n): ");

        String userInput = scanner.nextLine().trim().toLowerCase();

        switch (userInput) {
            case "y" -> DatabaseManager.closeAccount(uniq_id);
            case "n" -> System.out.println("Account closure process cancelled.");
            default -> System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }

        scanner.close();

    }
       

}
