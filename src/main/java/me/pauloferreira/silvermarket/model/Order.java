package me.pauloferreira.silvermarket.model;

import java.util.UUID;

public class Order {
  private final UUID id;
  private final float quantity;  //  assuming always in KG
  private final float price;     //  assuming always in GBP
  private final Type type;

  public enum Type { BUY, Sell; }

  public Order(UUID id, float quantity, float price, Type type) {
    this.id = id;
    this.quantity = quantity;
    this.price = price;
    this.type = type;
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
    if (!id.equals(order.id))
      return false;
    return type == order.type;

  }

  @Override public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (quantity != +0.0f ? Float.floatToIntBits(quantity) : 0);
    result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
    result = 31 * result + type.hashCode();
    return result;
  }
}
