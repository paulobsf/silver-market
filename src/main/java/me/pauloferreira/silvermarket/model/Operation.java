package me.pauloferreira.silvermarket.model;

import java.time.Instant;

public class Operation {
  private final Order order;
  private final Type type;
  private final Instant timestamp;  //  Not requested but important for audit trail

  public enum Type { REGISTER, CANCEL; }

  public Operation(Type type, Order order) {
    this.order = order;
    this.type = type;
    this.timestamp = Instant.now();
  }

  public Order getOrder() {
    return order;
  }

  public Type getType() {
    return type;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Operation operation = (Operation) o;

    if (!order.equals(operation.order))
      return false;
    return type == operation.type;

  }

  @Override public int hashCode() {
    int result = order.hashCode();
    result = 31 * result + type.hashCode();
    return result;
  }
}
