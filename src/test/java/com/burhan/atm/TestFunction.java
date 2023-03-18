package com.burhan.atm;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.burhan.atm.controller.AtmController;
import com.burhan.atm.model.Account;

import redis.clients.jedis.Jedis;

public class TestFunction {
    Account result = new Account();
    Jedis jedis = new Jedis("localhost", 6379);
    AtmController atmController = new AtmController(jedis);
    @Test  
    public void testAtm() throws Exception{  
        result.setUserName("alice");
        result.setAmountBalance(20.0);
        assertTrue(atmController.login("bob") instanceof Account);  
        assertTrue(atmController.deposit(20.0) instanceof String);  
        assertTrue(atmController.transfer(result) instanceof Account);
        assertTrue(atmController.logout() instanceof String);
    }  
}
