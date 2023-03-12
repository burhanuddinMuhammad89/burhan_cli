package com.burhan.atm.Service;

import com.burhan.atm.model.Account;

public interface AtmService {
  public String test();

  public Account login(String userName) throws Exception;

  public String logout();

  public String deposit(Double depo) throws Exception;

  public Account transfer(Account acct) throws Exception;

  public String balance(Account acct);
}
