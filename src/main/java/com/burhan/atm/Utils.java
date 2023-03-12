package com.burhan.atm;

import redis.clients.jedis.Jedis;

public class Utils {

  public static Double setDebtUser(
    Jedis jedis,
    Double amount,
    String usr,
    String usrDebt
  ) {
    Double debt = (amount >= 0.0) ? 0.0 : 0.0 - amount;
    if (debt > 0.0) {
      Double getRemainingDebt = Double.parseDouble(
        jedis.get("debt" + usr) != null ? jedis.get("debt" + usr).split(",")[0] : "0.0"
      );
      jedis.set("debt" + usr, String.valueOf(debt + getRemainingDebt) + "," + usrDebt);
    } else {
      jedis.del("debt" + usr);
    }

    return debt;
  }
}
