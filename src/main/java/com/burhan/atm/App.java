package com.burhan.atm;

import com.burhan.atm.controller.AtmController;
import com.burhan.atm.model.Account;
import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    Jedis jedis = new Jedis("localhost", 6379);
    AtmController atmController = new AtmController(jedis);
    switch (args[0]) {
      case "login":
        Account result;
        try {
          result = atmController.login(args[1]);
          System.out.println("Hello, " + result.getUserName() + "!");
          System.out.println("Your balance is $" + result.getAmountBalance());
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
        break;
      case "logout":
        System.out.println("Goodbye " + atmController.logout());
        break;
      case "deposit":
        try {
          Double depo = Double.parseDouble(args[1]);
          System.out.println("Your balance is $" + atmController.deposit(depo));
        } catch (Exception e) {
          e.printStackTrace();
          System.out.println(e.getMessage());
        }
        break;
      case "transfer":
        try {
          Account acct = new Account();
          acct.setAmountBalance(Double.parseDouble(args[2]));
          acct.setUserName(args[1]);
          acct = atmController.transfer(acct);
          System.out.println("Transferred $" + acct.getTransferredBalance()+" to "+args[1]);
          System.out.println("Your balance is $" + acct.getAmountBalance());
          if(acct.getDebt() > 0.0){
            System.out.println("Owed $" + acct.getDebt()+ " to "+args[1]);
          }
        } catch (Exception e) {
          e.printStackTrace();  
          System.out.println(e.getMessage());
        }
        break;
      default:
        System.out.println("wrong arguments");
    }
  }
}
