package com.burhan.atm.Service.impl;

import com.burhan.atm.Service.AtmService;
import com.burhan.atm.Utils;
import com.burhan.atm.model.Account;
import redis.clients.jedis.Jedis;

public class AtmServiceImpl implements AtmService {

  Jedis jedis = new Jedis();

  public AtmServiceImpl(Jedis jedis) {
    this.jedis = jedis;
  }

  Account acct = new Account();

  @Override
  public String test() {
    // TODO Auto-generated method stub
    jedis.set("user", "alice");
    String user = jedis.get("user");
    return "Hello " + user;
  }

  @Override
  public Account login(String userName) throws Exception {
    Account acct = new Account();
    String usr = jedis.get("login");
    String balance = jedis.get("balance" + userName);
    if (usr == null) {
      jedis.set("login", userName);
    } else {
      if (!usr.equalsIgnoreCase(userName)) {
        throw new Exception("Can't Login because other user already login");
      }
    }

    if (balance == null) {
      jedis.set("balance" + userName, "0.0");
    }
    acct.setUserName(jedis.get("login"));
    acct.setAmountBalance(Double.parseDouble(jedis.get("balance" + userName)));
    return acct;
  }

  @Override
  public String balance(Account acct) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'balance'");
  }

  @Override
  public String logout() {
    String usr = jedis.get("login");
    jedis.del("login");
    return usr;
  }

  @Override
  public String deposit(Double depo) throws Exception {
    String usr = jedis.get("login");
    String balance = jedis.get("balance" + usr);
    String debtStr = jedis.get("debt" + usr);
    System.out.println("debt :"+ debtStr);
    if (usr == null) {
      throw new Exception("Please login first");
    }

    if (debtStr != null) {
      System.out.println(debtStr);  
      String usrDebt = debtStr.split(",")[1];
      Double debt = Double.parseDouble(debtStr.split(",")[0]);
      Double remainingDepo = depo - debt;
      String usrDebtBalance = jedis.get("balance" + usrDebt);

      Double totalAmount = debt + Double.parseDouble(usrDebtBalance);
      jedis.set("balance" + usrDebt, String.valueOf(totalAmount));
      depo = remainingDepo;

      if (remainingDepo < 0.0) {
        Utils.setDebtUser(jedis, remainingDepo, usr, usrDebt);
        depo = 0.0;
      }else{
        Utils.setDebtUser(jedis, remainingDepo, usr, usrDebt);
      }

      System.out.println("depo is :"+ depo);
    }
    System.out.println("balance exisitn is :"+ Double.parseDouble(balance));
    String currentBalance = String.valueOf(depo + Double.parseDouble(balance));
    jedis.set("balance" + usr, currentBalance);
    return currentBalance;
  }

  @Override
  public Account transfer(Account acct) throws Exception {
    String usr = jedis.get("login");
    String balanceOtherUsr = jedis.get("balance" + acct.getUserName());
    String balanceCurrentUsr = jedis.get("balance" + usr);

    Double balanceCurrUsr =
      Double.parseDouble(balanceCurrentUsr) - acct.getAmountBalance();
    Double debt = 0.0;
    Double transFerredAmount = acct.getAmountBalance();
    if (balanceCurrUsr < 0.0) {
      debt = Utils.setDebtUser(jedis, balanceCurrUsr, usr, acct.getUserName());
      transFerredAmount = Double.parseDouble(balanceCurrentUsr);
      balanceCurrUsr = 0.0;
    }

    Double balanceOtherUser =
      transFerredAmount +
      Double.parseDouble(balanceOtherUsr == null ? "0.0" : balanceOtherUsr);
    //set balance for other user
    jedis.set("balance" + acct.getUserName(), String.valueOf(balanceOtherUser));
    //set balance for login user
    jedis.set("balance" + usr, String.valueOf(balanceCurrUsr));

    acct.setAmountBalance(balanceCurrUsr);
    acct.setDebt(debt);
    acct.setTransferredBalance(transFerredAmount);
    return acct;
  }
}
