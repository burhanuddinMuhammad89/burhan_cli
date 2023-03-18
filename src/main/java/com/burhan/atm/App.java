package com.burhan.atm;

import java.util.logging.Logger;

import com.burhan.atm.controller.AtmController;
import com.burhan.atm.model.Account;
import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */
public class App {

  private static final String YOUR_BALANCE_IS = "Your balance is $";

  public static void main(String[] args) {
    Jedis jedis = new Jedis("localhost", 6379);
    AtmController atmController = new AtmController(jedis);
    switch (args[0]) {
      case "login":
        Account result;
        try {
          result = atmController.login(args[1]);
          Logger.getLogger("Hello, " + result.getUserName() + "!");
          Logger.getLogger(YOUR_BALANCE_IS + result.getAmountBalance());
        } catch (Exception e) {
          Logger.getLogger(e.getMessage());
        }
        break;
      case "logout":
      Logger.getLogger("Goodbye " + atmController.logout());
        break;
      case "deposit":
        try {
          Double depo = Double.parseDouble(args[1]);
          Logger.getLogger(YOUR_BALANCE_IS + atmController.deposit(depo));
        } catch (Exception e) {
          e.printStackTrace();
          Logger.getLogger(e.getMessage());
        }
        break;
      case "transfer":
        try {
          Account acct = new Account();
          acct.setAmountBalance(Double.parseDouble(args[2]));
          acct.setUserName(args[1]);
          acct = atmController.transfer(acct);
          Logger.getLogger("Transferred $" + acct.getTransferredBalance()+" to "+args[1]);
          Logger.getLogger(YOUR_BALANCE_IS + acct.getAmountBalance());
          if(acct.getDebt() > 0.0){
            Logger.getLogger("Owed $" + acct.getDebt()+ " to "+args[1]);
          }
        } catch (Exception e) {
          e.printStackTrace();  
          Logger.getLogger(e.getMessage());
        }
        break;
      default:
        Logger.getLogger("wrong arguments");
    }
  }
}
