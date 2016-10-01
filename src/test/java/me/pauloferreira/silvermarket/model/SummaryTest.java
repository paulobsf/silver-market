package me.pauloferreira.silvermarket.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SummaryTest {
  private Summary summary;

  @Test public void buyOperations() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(new Order(UUID.randomUUID(), 1f, 10f, Order.Type.BUY), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 2f, 10f, Order.Type.BUY), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 3f, 10f, Order.Type.BUY), Operation.Type.REGISTER));

      add(new Operation(new Order(UUID.randomUUID(), 4f, 20f, Order.Type.BUY), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 5f, 20f, Order.Type.BUY), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 6f, 20f, Order.Type.BUY), Operation.Type.REGISTER));
    }};

    summary = Summary.of(operations);

    Map<Float, Float> consolidatedOperations = summary.getOperations(Order.Type.BUY);

    assertThat(consolidatedOperations.get(10f), is(equalTo(6f)));
    assertThat(consolidatedOperations.get(20f), is(equalTo(15f)));
  }

  @Test public void buyOperationsWithCancellations() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(new Order(UUID.randomUUID(), 1f, 10f, Order.Type.BUY), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 2f, 10f, Order.Type.BUY), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 3f, 10f, Order.Type.BUY), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 3f, 10f, Order.Type.BUY), Operation.Type.CANCEL));
    }};

    summary = Summary.of(operations);

    Map<Float, Float> consolidatedOperations = summary.getOperations(Order.Type.BUY);

    assertThat(consolidatedOperations.get(10f), is(equalTo(3f)));
  }

  @Test public void sellOperations() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(new Order(UUID.randomUUID(), 1f, 10f, Order.Type.SELL), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 2f, 10f, Order.Type.SELL), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 3f, 10f, Order.Type.SELL), Operation.Type.REGISTER));

      add(new Operation(new Order(UUID.randomUUID(), 4f, 20f, Order.Type.SELL), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 5f, 20f, Order.Type.SELL), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 6f, 20f, Order.Type.SELL), Operation.Type.REGISTER));
    }};

    summary = Summary.of(operations);

    Map<Float, Float> consolidatedOperations = summary.getOperations(Order.Type.SELL);

    assertThat(consolidatedOperations.get(10f), is(equalTo(6f)));
    assertThat(consolidatedOperations.get(20f), is(equalTo(15f)));
  }

  @Test public void sellOperationsWithCancellations() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(new Order(UUID.randomUUID(), 4f, 20f, Order.Type.SELL), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 5f, 20f, Order.Type.SELL), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 6f, 20f, Order.Type.SELL), Operation.Type.REGISTER));
      add(new Operation(new Order(UUID.randomUUID(), 6f, 20f, Order.Type.SELL), Operation.Type.CANCEL));
    }};

    summary = Summary.of(operations);

    Map<Float, Float> consolidatedOperations = summary.getOperations(Order.Type.SELL);

    assertThat(consolidatedOperations.get(20f), is(equalTo(9f)));
  }

}
