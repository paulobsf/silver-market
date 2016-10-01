package me.pauloferreira.silvermarket.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Summary {
  private final Map<Float, Float> buyConsolidatedOperations;
  private final Map<Float, Float> sellConsolidatedOperations;

  private Summary() { throw new UnsupportedOperationException(); }

  private Summary(Map<Float, Float> buyConsolidatedOperations, Map<Float, Float> sellConsolidatedOperations) {
    this.buyConsolidatedOperations = buyConsolidatedOperations;
    this.sellConsolidatedOperations = sellConsolidatedOperations;
  }

  public Map<Float, Float> getOperations(Order.Type orderType) {
    return orderType == Order.Type.BUY ? buyConsolidatedOperations : sellConsolidatedOperations;
  }

  public static Summary of(Collection<Operation> operations) {
    Map<Float, Float> buyConsolidatedOperations = new HashMap<>();
    Map<Float, Float> sellConsolidatedOperations = new HashMap<>();

    operations.forEach(operation -> {
      Order order = operation.getOrder();
      switch (order.getType()) {
        case BUY:
          buyConsolidatedOperations.put(
            order.getPrice(),
            operation.getType() == Operation.Type.REGISTER
              ? buyConsolidatedOperations.getOrDefault(order.getPrice(), 0f) + order.getQuantity()
              : buyConsolidatedOperations.getOrDefault(order.getPrice(), 0f) - order.getQuantity()
          );
          break;
        case SELL:
          sellConsolidatedOperations.put(
            order.getPrice(),
            operation.getType() == Operation.Type.REGISTER
              ? sellConsolidatedOperations.getOrDefault(order.getPrice(), 0f) + order.getQuantity()
              : sellConsolidatedOperations.getOrDefault(order.getPrice(), 0f) - order.getQuantity()
          );
          break;
      }
    });

    return new Summary(buyConsolidatedOperations, sellConsolidatedOperations);
  }
}
