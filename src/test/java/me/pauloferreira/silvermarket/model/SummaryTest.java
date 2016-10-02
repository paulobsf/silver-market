package me.pauloferreira.silvermarket.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SummaryTest {
  private static final UUID USER_ID = UUID.randomUUID();

  private Summary summary;

  @Test
  public void buyOperations() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(1f).setPrice(10f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(2f).setPrice(10f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(3f).setPrice(10f)));

      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(4f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(5f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(6f).setPrice(20f)));
    }};

    summary = Summary.of(operations);

    List<Float> summaryPrices = summary.getPrices(Order.Type.BUY);

    assertThat(summaryPrices.contains(10f), is(true));
    assertThat(summary.getQuantity(Order.Type.BUY, 10f), is(equalTo(6f)));

    assertThat(summaryPrices.contains(20f), is(true));
    assertThat(summary.getQuantity(Order.Type.BUY, 20f), is(equalTo(15f)));
  }

  @Test
  public void buyOperationsSorted() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, UUID.randomUUID()).setQuantity(4f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, UUID.randomUUID()).setQuantity(4f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, UUID.randomUUID()).setQuantity(1f).setPrice(10f)));
    }};

    summary = Summary.of(operations);
    List<Float> summaryPrices = summary.getPrices(Order.Type.BUY);

    assertThat(summaryPrices, is(notNullValue()));
    assertThat(summaryPrices.size(), is(equalTo(2)));
    assertThat(summaryPrices.get(0), is(equalTo(20f)));
    assertThat(summaryPrices.get(1), is(equalTo(10f)));
  }

  @Test
  public void buyOperationsWithCancellations() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(1f).setPrice(10f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(2f).setPrice(10f)));

      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(3f).setPrice(10f)));
      add(new Operation(Operation.Type.CANCEL, Order.build(Order.Type.BUY, USER_ID).setQuantity(3f).setPrice(10f)));
    }};

    summary = Summary.of(operations);

    assertThat(summary.getQuantity(Order.Type.BUY, 10f), is(equalTo(3f)));
  }

  @Test
  public void sellOperations() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(1f).setPrice(10f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(2f).setPrice(10f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(3f).setPrice(10f)));

      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(4f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(5f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(6f).setPrice(20f)));
    }};

    summary = Summary.of(operations);

    List<Float> summaryPrices = summary.getPrices(Order.Type.SELL);

    assertThat(summaryPrices.contains(10f), is(true));
    assertThat(summary.getQuantity(Order.Type.SELL, 10f), is(equalTo(6f)));

    assertThat(summaryPrices.contains(20f), is(true));
    assertThat(summary.getQuantity(Order.Type.SELL, 20f), is(equalTo(15f)));
  }

  @Test
  public void sellOperationsSorted() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, UUID.randomUUID()).setQuantity(4f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, UUID.randomUUID()).setQuantity(4f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, UUID.randomUUID()).setQuantity(1f).setPrice(10f)));
    }};

    summary = Summary.of(operations);
    List<Float> summaryPrices = summary.getPrices(Order.Type.SELL);

    assertThat(summaryPrices, is(notNullValue()));
    assertThat(summaryPrices.size(), is(equalTo(2)));
    assertThat(summaryPrices.get(0), is(equalTo(10f)));
    assertThat(summaryPrices.get(1), is(equalTo(20f)));
  }

  @Test
  public void sellOperationsWithCancellations() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(4f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(5f).setPrice(20f)));

      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(6f).setPrice(20f)));
      add(new Operation(Operation.Type.CANCEL, Order.build(Order.Type.SELL, USER_ID).setQuantity(6f).setPrice(20f)));
    }};

    summary = Summary.of(operations);

    assertThat(summary.getQuantity(Order.Type.SELL, 20f), is(equalTo(9f)));
  }

  @Test
  public void mixedOperations() throws Exception {
    Collection<Operation> operations = new ArrayList<Operation>() {{
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(1f).setPrice(10f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(2f).setPrice(10f)));

      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(4f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(5f).setPrice(20f)));

      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.SELL, USER_ID).setQuantity(6f).setPrice(20f)));
      add(new Operation(Operation.Type.CANCEL, Order.build(Order.Type.SELL, USER_ID).setQuantity(6f).setPrice(20f)));

      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(1f).setPrice(10f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(2f).setPrice(10f)));

      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(4f).setPrice(20f)));
      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(5f).setPrice(20f)));

      add(new Operation(Operation.Type.REGISTER, Order.build(Order.Type.BUY, USER_ID).setQuantity(6f).setPrice(20f)));
      add(new Operation(Operation.Type.CANCEL, Order.build(Order.Type.BUY, USER_ID).setQuantity(6f).setPrice(20f)));
    }};

    String expectedOutput = "BUY\n"
      + "---\n"
      + "9.00 kg for £20\n"
      + "3.00 kg for £10\n"
      + "\n"
      + "SELL\n"
      + "---\n"
      + "3.00 kg for £10\n"
      + "9.00 kg for £20";

    summary = Summary.of(operations);

    assertThat(summary.toString(), is(equalTo(expectedOutput)));
  }

}
