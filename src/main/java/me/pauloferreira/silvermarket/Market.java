package me.pauloferreira.silvermarket;

import me.pauloferreira.silvermarket.model.Operation;
import me.pauloferreira.silvermarket.model.Order;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Market {
  private final Queue<Operation> operations;

  public Market() {
    operations = new ConcurrentLinkedQueue<>();
  }

  public void registerOrder(Order order) {
    operations.add(new Operation(order, Operation.Type.REGISTER));
  }

  public void cancelOrder(Order order) {
    operations.add(new Operation(order, Operation.Type.CANCEL));
  }
}
