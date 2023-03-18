package com.burhan.atm.service;

import com.burhan.atm.Exception.MyCustomException;
import com.burhan.atm.model.Account;

public interface AtmService {
  public String test();

  public Account login(String userName) throws MyCustomException;

  public String logout();

  public String deposit(Double depo) throws MyCustomException;

  public Account transfer(Account acct) throws MyCustomException;

  public String balance(Account acct);
}
