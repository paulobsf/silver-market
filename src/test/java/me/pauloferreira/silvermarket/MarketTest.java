package me.pauloferreira.silvermarket;

import me.pauloferreira.silvermarket.model.Order;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class MarketTest {
  private Market market;

  @Before public void setup() {
    market = new Market();
  }

  @Test public void registerOrder() throws Exception {
    market.registerOrder(new Order(UUID.randomUUID(), 2.5f, 300f, Order.Type.BUY));
  }

  @Test public void cancelOrder() throws Exception {
    market.cancelOrder(new Order(UUID.randomUUID(), 2.5f, 300f, Order.Type.BUY));
  }

}
