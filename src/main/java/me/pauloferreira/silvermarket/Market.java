package me.pauloferreira.silvermarket;

import me.pauloferreira.silvermarket.model.Operation;
import me.pauloferreira.silvermarket.model.Order;
import me.pauloferreira.silvermarket.model.Summary;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Market {
  private final Queue<Operation> operations;

  public Market() {
    operations = new ConcurrentLinkedQueue<>();
  }

  public void register(Order order) {
    operations.add(new Operation(Operation.Type.REGISTER, order));
  }

  public void cancel(Order order) {
    operations.add(new Operation(Operation.Type.CANCEL, order));
  }

  public Summary getSummary() {
    return Summary.of(operations);
  }
}
