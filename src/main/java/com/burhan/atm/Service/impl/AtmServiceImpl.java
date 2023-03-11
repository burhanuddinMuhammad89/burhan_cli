package com.burhan.atm.Service.impl;

import com.burhan.atm.Service.AtmService;
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
}
