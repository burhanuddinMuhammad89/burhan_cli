package com.burhan.atm.service.impl;

import java.util.logging.Logger;

import com.burhan.atm.Utils;
import com.burhan.atm.Exception.MyCustomException;
import com.burhan.atm.model.Account;
import com.burhan.atm.service.AtmService;

import redis.clients.jedis.Jedis;

public class AtmServiceImpl implements AtmService {

  private static final String BALANCE2 = "balance";
  private static final String LOGIN = "login";
  Jedis jedis = new Jedis();

  public AtmServiceImpl(Jedis jedis) {
    this.jedis = jedis;
  }

  @Override
  public String test() {
    jedis.set("user", "alice");
    String user = jedis.get("user");
    return "Hello " + user;
  }

  @Override
  public Account login(String userName) throws MyCustomException {
    Account acct = new Account();
    String usr = jedis.get(LOGIN);
    String balance = jedis.get(BALANCE2 + userName);
    if (usr == null) {
      jedis.set(LOGIN, userName);
    } else {
      if (!usr.equalsIgnoreCase(userName)) {
        throw new MyCustomException("Can't Login because other user already login");
      }
    }

    if (balance == null) {
      jedis.set(BALANCE2 + userName, "0.0");
    }
    acct.setUserName(jedis.get(LOGIN));
    acct.setAmountBalance(Double.parseDouble(jedis.get(BALANCE2 + userName)));
    return acct;
  }

  @Override
  public String balance(Account acct) {
    throw new UnsupportedOperationException("Unimplemented method 'balance'");
  }

  @Override
  public String logout() {
    String usr = jedis.get(LOGIN);
    jedis.del(LOGIN);
    return usr;
  }

  @Override
  public String deposit(Double depo) throws MyCustomException {
    String usr = jedis.get(LOGIN);
    String balance = jedis.get(BALANCE2 + usr);
    String debtStr = jedis.get("debt" + usr);
    Logger.getLogger("debt :"+ debtStr);
    if (usr == null) {
      throw new MyCustomException("Please login first");
    }

    if (debtStr != null) {
      Logger.getLogger(debtStr);  
      String usrDebt = debtStr.split(",")[1];
      Double debt = Double.parseDouble(debtStr.split(",")[0]);
      Double remainingDepo = depo - debt;
      String usrDebtBalance = jedis.get(BALANCE2 + usrDebt);

      Double totalAmount = debt + Double.parseDouble(usrDebtBalance);
      jedis.set(BALANCE2 + usrDebt, String.valueOf(totalAmount));
      depo = remainingDepo;

      if (remainingDepo < 0.0) {
        Utils.setDebtUser(jedis, remainingDepo, usr, usrDebt);
        depo = 0.0;
      }else{
        Utils.setDebtUser(jedis, remainingDepo, usr, usrDebt);
      }

      Logger.getLogger("depo is :"+ depo);
    }
    Logger.getLogger("balance exisitn is :"+ Double.parseDouble(balance));
    String currentBalance = String.valueOf(depo + Double.parseDouble(balance));
    jedis.set(BALANCE2 + usr, currentBalance);
    return currentBalance;
  }

  @Override
  public Account transfer(Account acct) throws MyCustomException {
    String usr = jedis.get(LOGIN);
    String balanceOtherUsr = jedis.get(BALANCE2 + acct.getUserName());
    String balanceCurrentUsr = jedis.get(BALANCE2 + usr);

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
    jedis.set(BALANCE2 + acct.getUserName(), String.valueOf(balanceOtherUser));
    //set balance for login user
    jedis.set(BALANCE2 + usr, String.valueOf(balanceCurrUsr));

    acct.setAmountBalance(balanceCurrUsr);
    acct.setDebt(debt);
    acct.setTransferredBalance(transFerredAmount);
    return acct;
  }
}
