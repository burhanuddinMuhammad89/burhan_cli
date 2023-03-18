package com.burhan.atm.controller;

import com.burhan.atm.model.Account;
import com.burhan.atm.service.AtmService;
import com.burhan.atm.service.impl.AtmServiceImpl;

import redis.clients.jedis.Jedis;

public class AtmController {

  Jedis jedis = new Jedis();

  public AtmController(Jedis jedis) {
    this.jedis = jedis;
  }

  AtmService atmService = new AtmServiceImpl(jedis);

  public Account login(String userName) throws Exception {
    return atmService.login(userName);
  }

  public String logout() {
    return atmService.logout();
  }

  public String deposit(Double depo) throws Exception {
    return atmService.deposit(depo);
  }

  public Account transfer(Account acct) throws Exception {
    return atmService.transfer(acct);
  }
}
