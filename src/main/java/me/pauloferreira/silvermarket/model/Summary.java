package me.pauloferreira.silvermarket.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Summary {
  private final Map<Order.Type, Map<Float, Float>> consolidatedOperations;

  private Summary() { throw new UnsupportedOperationException(); }

  private Summary(Map<Order.Type, Map<Float, Float>> consolidatedOperations) {
    this.consolidatedOperations = consolidatedOperations;
  }

  public static Summary of(Collection<Operation> operations) {
    Map<Order.Type, Map<Float, Float>> consolidatedOperations = new HashMap<Order.Type, Map<Float, Float>>() {{
      put(Order.Type.BUY, new HashMap<>());
      put(Order.Type.SELL, new HashMap<>());
    }};

    operations.forEach(operation -> {
      Order order = operation.getOrder();
      consolidatedOperations.get(order.getType()).put(
        order.getPrice(),
        operation.getType() == Operation.Type.REGISTER
          ? consolidatedOperations.get(order.getType()).getOrDefault(order.getPrice(), 0f) + order.getQuantity()
          : consolidatedOperations.get(order.getType()).getOrDefault(order.getPrice(), 0f) - order.getQuantity());
    });

    return new Summary(consolidatedOperations);
  }

  public List<Float> getPrices(Order.Type orderType) {
    return consolidatedOperations.get(orderType).keySet().stream()
      // SELL: sort price ascending; BUY: fort price descending
      .sorted((price1, price2) -> orderType == Order.Type.SELL ? price1.compareTo(price2) : price2.compareTo(price1))
      .collect(Collectors.toList());
  }

  public float getQuantity(Order.Type orderType, float price) {
    return consolidatedOperations.get(orderType).get(price);
  }

  @Override public String toString() {
    StringBuilder builder = new StringBuilder();

    List<Float> buyPrices = getPrices(Order.Type.BUY);

    builder.append("BUY\n---");
    buyPrices.forEach(price -> builder.append(String.format("\n%.2f kg for £%.0f", getQuantity(Order.Type.BUY, price), price)));

    List<Float> sellPrices = getPrices(Order.Type.SELL);

    builder.append("\n\nSELL\n---");
    sellPrices.forEach(price -> builder.append(String.format("\n%.2f kg for £%.0f", getQuantity(Order.Type.BUY, price), price)));

    return builder.toString();
  }
}
