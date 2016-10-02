package me.pauloferreira.silvermarket;

import me.pauloferreira.silvermarket.model.Order;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class MarketTest {
  private static final UUID USER_ID = UUID.randomUUID();

  private Market market;

  @Before public void setup() {
    market = new Market();
  }

  @Test public void register() throws Exception {
    market.register(Order.build(Order.Type.BUY, USER_ID).setQuantity(2.5f).setPrice(300f));
  }

  @Test public void cancel() throws Exception {
    market.cancel(Order.build(Order.Type.BUY, USER_ID).setQuantity(2.5f).setPrice(300f));
  }

}
