package classes;

import java.util.ArrayList;
import java.util.Random;

import classes.Exceptions.NotEnoughGoodsException;

public class Shop {
  private String name;
  private ArrayList<Goods> meat;
  private ArrayList<Goods> vegetables;
  private ArrayList<Goods> fruits;
  private ArrayList<Cashier> cashiers;
  private ArrayList<Receipt> receipts;
  private ArrayList<Till> tills;
  private ArrayList<Customer> customers;
  private int totalEarnings;

  private final int MEAT_PRICE = 4;
  private final int MEAT_VALIDITY = 10;

  private final int VEGETABLE_PRICE = 2;
  private final int VEGETABLE_VALIDITY = 5;

  private final int FRUIT_PRICE = 2;
  private final int FRUIT_VALIDITY = 5;

  public Shop(String name) {
    this.name = name;
    this.meat = new ArrayList<>();
    this.vegetables = new ArrayList<>();
    this.fruits = new ArrayList<>();
    this.cashiers = new ArrayList<>();
    this.receipts = new ArrayList<>();
    this.tills = new ArrayList<>();
    this.customers = new ArrayList<>();
    this.totalEarnings = 0;
  }

  // GETTERS
  public String getName() {
    return this.name;
  }

  public ArrayList<Goods> getMeat() {
    return this.meat;
  }

  public ArrayList<Goods> getVegetables() {
    return this.vegetables;
  }

  public ArrayList<Goods> getFruits() {
    return this.fruits;
  }

  public ArrayList<Cashier> getCashiers() {
    return this.cashiers;
  }

  public ArrayList<Receipt> getReceipts() {
    return this.receipts;
  }

  public ArrayList<Till> getTills() {
    return this.tills;
  }

  public ArrayList<Customer> getCustomers() {
    return this.customers;
  }

  public int getTotalEarnings() {
    return this.totalEarnings;
  }

  public int getMEAT_PRICE() {
    return this.MEAT_PRICE;
  }

  public int getMEAT_VALIDITY() {
    return this.MEAT_VALIDITY;
  }

  public int getVEGETABLE_PRICE() {
    return this.VEGETABLE_PRICE;
  }

  public int getVEGETABLE_VALIDITY() {
    return this.VEGETABLE_VALIDITY;
  }

  public int getFRUIT_PRICE() {
    return this.FRUIT_PRICE;
  }

  public int getFRUIT_VALIDITY() {
    return this.FRUIT_VALIDITY;
  }

  // SETTERS
  public void addReceipts(Receipt r) {
    this.receipts.add(r);
  }

  public void addCashiers(Cashier cashier) {
    this.cashiers.add(cashier);
  }

  public void addTill(Till till) {
    this.tills.add(till);
  }

  // adds goods to the shop
  public void addGoods(String good, int n) {
    if (good.equals("meat")) {
      Goods meat = new Goods("meat", MEAT_PRICE, MEAT_VALIDITY);
      for (int i = 0; i < n; i++) {
        this.meat.add(meat);
      }
      System.out.println("You just added " + n + " meat.");
    } else if (good.equals("vegetable") || good.equals("vegetables")) {
      Goods vegetable = new Goods("vegetable", VEGETABLE_PRICE, VEGETABLE_VALIDITY);
      for (int i = 0; i < n; i++) {
        this.vegetables.add(vegetable);
      }
      System.out.println("You just added " + n + " vegetables.");

    } else if (good.equals("fruit") || good.equals("fruits")) {
      Goods fruit = new Goods("fruit", FRUIT_PRICE, FRUIT_VALIDITY);
      for (int i = 0; i < n; i++) {
        this.fruits.add(fruit);
      }
      System.out.println("You just added " + n + " fruits.");
    }
  }

  // adds customers serve them
  public void addCustomers(int n) {
    Random random = new Random();
    for (int i = 0; i < n; i++) {
      Customer customer = new Customer(random.nextInt(4), random.nextInt(4), random.nextInt(4),
          tills.get(random.nextInt(tills.size())));
      customer.getTill().addCustomerToTill(customer);
      this.customers.add(customer);
    }
    // starting all tills as individual threads
    for (Till till : tills) {
      Runnable runnable = () -> till.startWork();
      Thread t = new Thread(runnable);

      System.out.println("Starting Thread " + till.getCashier());
      t.start();
    }

  }

  // prints only goods information
  public String printGoods() {
    String res = "";
    res += "  Meat: " + meat.size() + "\n  ";
    if (!this.meat.isEmpty()) {
      res += this.meat.get(0).toString() + "\n  ";
    }
    res += "Vegetables: " + vegetables.size() + "\n  ";
    if (!this.vegetables.isEmpty()) {
      res += this.vegetables.get(0).toString() + "\n  ";
    }
    res += "Fruits: " + fruits.size() + "\n  ";
    if (!this.fruits.isEmpty()) {
      res += this.fruits.get(0).toString() + "\n  ";
    }
    return res;
  }

  // prints only cashiers information
  public String printCashiers() {
    String cashiersStr = "";
    for (int i = 0; i < cashiers.size(); i++) {
      cashiersStr += "  " + String.format("%-20s", cashiers.get(i).getName()) + cashiers.get(i).getID() + "\n";
    }
    return cashiersStr;
  }

  // calculates the minimum goods required
  private int calcMinGoodsNeeded(String type, int delivery) {
    if (type == "meat")
      return delivery - this.meat.size();
    else if (type == "vegetables")
      return delivery - this.vegetables.size();
    else if (type == "fruits")
      return delivery - this.fruits.size();
    else
      return 0;
  }

  // updates the goods in shop
  public void updateGoods(int wantedMeat, int wantedFruits, int wantedVegetables) {
    for (int i = 0; i < wantedMeat; i++) {
      this.meat.remove(meat.size() - 1);
    }
    for (int i = 0; i < wantedVegetables; i++) {
      this.vegetables.remove(vegetables.size() - 1);
    }
    for (int i = 0; i < wantedFruits; i++) {
      this.fruits.remove(fruits.size() - 1);
    }

    this.totalEarnings += wantedMeat * MEAT_PRICE;
    this.totalEarnings += wantedVegetables * VEGETABLE_PRICE;
    this.totalEarnings += wantedFruits * FRUIT_PRICE;

  }

  public synchronized void sell(Customer customer) throws NotEnoughGoodsException {
    boolean enoughMeat = this.meat.size() >= customer.getMeat();
    boolean enoughVegetables = this.vegetables.size() >= customer.getVegetables();
    boolean enougFruits = this.fruits.size() >= customer.getFruits();
    if (enoughMeat && enoughVegetables && enougFruits) {
      updateGoods(customer.getMeat(), customer.getFruits(), customer.getVegetables());
    } else {
      if (!enoughMeat && enougFruits & enoughVegetables) {
        throw new NotEnoughGoodsException(
            "Not enough meat. Please restock with minimum " + calcMinGoodsNeeded("meat", customer.getMeat()));
      } else if (enoughMeat && !enougFruits & enoughVegetables) {
        throw new NotEnoughGoodsException(
            "Not enough fruits. Please restock with minimum " + calcMinGoodsNeeded("fruits", customer.getFruits()));
      } else if (enoughMeat && enougFruits && !enoughVegetables) {
        throw new NotEnoughGoodsException("Not enough vegetables. Please restock with minimum "
            + calcMinGoodsNeeded("vegetables", customer.getVegetables()));
      } else if (!enoughMeat && !enougFruits && !enoughVegetables) {
        throw new NotEnoughGoodsException("Not enough meat, fruits and vegetables. Please restock with minimum "
            + calcMinGoodsNeeded("meat", customer.getMeat()) + " meat, "
            + calcMinGoodsNeeded("fruits", customer.getFruits()) + " fruits and "
            + calcMinGoodsNeeded("vegetables", customer.getVegetables()) + " vegetables.");
      } else if (!enoughMeat && !enougFruits && enoughVegetables) {
        throw new NotEnoughGoodsException(
            "Not enough meat and fruits. Please restock with minimum " + calcMinGoodsNeeded("meat", customer.getMeat())
                + " meat and " + calcMinGoodsNeeded("fruits", customer.getFruits()) + " fruits.");
      } else if (!enoughMeat && enougFruits && !enoughVegetables) {
        throw new NotEnoughGoodsException("Not enough meat and vegetables. Please restock with minimum "
            + calcMinGoodsNeeded("meat", customer.getMeat()) + " meat and "
            + calcMinGoodsNeeded("vegetables", customer.getVegetables()) + " vegetables.");
      } else if (enoughMeat && !enougFruits && !enoughVegetables) {
        throw new NotEnoughGoodsException("Not enough fruits and vegetables. Please restock with minimum "
            + calcMinGoodsNeeded("fruits", customer.getFruits()) + " fruits and "
            + calcMinGoodsNeeded("vegetables", customer.getVegetables()) + " vegetables.");
      }
    }

  }

  public String getInfoShop() {
    return name + "\n" + "Products:\n" + printGoods() + "\nCashiers:\n" + printCashiers() + "Receipts: "
        + getReceipts().size();
  }

  @Override
  public String toString() {
    return "Shop {" + " name='" + getName() + "'" + ", meat='" + getMeat().size() + "'" + ", vegetables='"
        + getVegetables().size() + "'" + ", fruits='" + getFruits().size() + "'" + ", cashiers='" + getCashiers().size()
        + "'" + ", receipts='" + getReceipts().size() + "'" + ", tills='" + getTills().size() + "'" + ", customers='"
        + getCustomers().size() + "'" + ", totalEarnings='" + getTotalEarnings() + "'" + "}";
  }

}