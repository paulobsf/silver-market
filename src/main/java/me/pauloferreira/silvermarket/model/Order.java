package me.pauloferreira.silvermarket.model;

import java.util.UUID;

public class Order {
  private final Type type;
  private final UUID userId;
  private final float quantity;  //  assuming always in Kg
  private final float price;     //  assuming always in GBP

  public enum Type { BUY, SELL }

  private Order(Type type, UUID userId, float quantity, float price) {
    this.userId = userId;
    this.quantity = quantity;
    this.price = price;
    this.type = type;
  }

  /*
   * Terse builder pattern implementation using lambdas and functional interfaces
   * Added to remove potential confusion over the order for quantity and price
   */
  public static BuilderSetQuantity build(Type type, UUID userId) {
    return qtt -> pr -> new Order(type, userId, qtt, pr);
  }

  public interface BuilderSetQuantity {
    BuilderSetPrice setQuantity(float quantity);
  }

  public interface BuilderSetPrice {
    Order setPrice(float price);
  }

  public Type getType() {
    return type;
  }

  public UUID getUserId() {
    return userId;
  }

  public float getQuantity() {
    return quantity;
  }

  public float getPrice() {
    return price;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Order order = (Order) o;

    if (Float.compare(order.quantity, quantity) != 0)
      return false;
    if (Float.compare(order.price, price) != 0)
      return false;
    if (!userId.equals(order.userId))
      return false;
    return type == order.type;

  }

  @Override public int hashCode() {
    int result = userId.hashCode();
    result = 31 * result + (quantity != +0.0f ? Float.floatToIntBits(quantity) : 0);
    result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
    result = 31 * result + type.hashCode();
    return result;
  }
}
