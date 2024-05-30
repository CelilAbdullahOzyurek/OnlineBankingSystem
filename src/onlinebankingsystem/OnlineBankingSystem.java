/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package onlinebankingsystem;

/**
 *
 * @author celil
 */
public class OnlineBankingSystem {

    public static void main(String[] args) {

DatabaseManager.getConnection();
      
      Customer customer = new Customer( "ali", "example2@example.com", "example" );
      //uses customers info to login to account 
         customer.login();
        //customer.closeAccount(0);
        //customer.createAccount();
        //customer.depositMoney();
        //customer.guessTossedCoin();
        //customer.transferMoney();
        //customer.withdrawMoney();
        //customer.getBalance();
        //customer.getEmail();
      
       //customer.depositMoney();
      
       
          
    //  user this for admin methods   
   Admin admin = new Admin("Admin","admin@example.com" ,"adminpassword" );  
     
   //admin.closeAccount(0);
   //admin.createAccount();
   //admin.depositMoney();
   //admin.guessTossedCoin();
   //admin.viewAllUsers();
   //admin.viewUserBalance("example2@example.com");
   //admin.withdrawMoney();
   //admin.getPassword();
  
  //System.out.println(admin.Birthday());
    
    
        
        

       
        
    
        
        
        

  
    }

}
