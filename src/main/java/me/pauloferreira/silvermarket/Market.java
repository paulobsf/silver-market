package me.pauloferreira.silvermarket;

import me.pauloferreira.silvermarket.exception.InvalidOperationException;
import me.pauloferreira.silvermarket.model.Operation;
import me.pauloferreira.silvermarket.model.Order;
import me.pauloferreira.silvermarket.model.Summary;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class Market {
  private final Queue<Operation> operations;

  public Market() {
    operations = new ConcurrentLinkedQueue<>();
  }

  public void register(Order order) {
    operations.add(new Operation(Operation.Type.REGISTER, order));
  }

  public void cancel(Order order) throws InvalidOperationException {
    validateCancelOperation(order);
    operations.add(new Operation(Operation.Type.CANCEL, order));
  }

  private void validateCancelOperation(Order order) throws InvalidOperationException {
    Set<Operation> matchingOrders = operations.stream().filter(operation -> operation.getOrder().equals(order)).collect(Collectors.toSet());
    long registerOperations = matchingOrders.stream().filter(operation -> operation.getType() == Operation.Type.REGISTER).count();
    long cancelOperations = matchingOrders.size() - registerOperations;

    if (registerOperations == cancelOperations) {
      throw new InvalidOperationException(String.format("Cannot cancel. Found %d matching register and %d cancel operations", registerOperations, cancelOperations));
    }
  }

  public Summary getSummary() {
    return Summary.of(operations);
  }
}
