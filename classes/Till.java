package classes;

import java.util.ArrayList;

import classes.Exceptions.NotEnoughGoodsException;

public class Till {
  private Cashier cashier;
  private Shop shop;
  private ArrayList<Customer> customers;

  public Till(Cashier cashier) {
    this.cashier = cashier;
    this.shop = this.cashier.getWorkplace();
    this.customers = new ArrayList<>();
  }

  public Shop getShop() {
    return shop;
  }

  public Cashier getCashier() {
    return this.cashier;
  }

  public void addCustomerToTill(Customer c) {
    customers.add(c);
  }

  @Override
  public String toString() {
    return "Cashier: " + getCashier().getName();
  }

  public void startWork() {
    for (Customer c : customers)
      try {
        shop.sell(c);
        System.out.println("Customer " + c.getID() + " is now to cashier " + this.getCashier().getName());
        Receipt rec = new Receipt(cashier, c.getMeat(), c.getVegetables(), c.getFruits());
        rec.writeReceipt();
      } catch (NotEnoughGoodsException e) {
        System.out.println(e.getMessage());
      } finally {
      }
    this.customers.removeAll(customers);
  }
}