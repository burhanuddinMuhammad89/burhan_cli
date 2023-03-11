package com.burhan.atm.controller;

import com.burhan.atm.Service.AtmService;
import com.burhan.atm.Service.impl.AtmServiceImpl;
import com.burhan.atm.model.Account;
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
}
