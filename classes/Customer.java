package classes;

public class Customer {
  private int ID;
  private static int counter = 1;
  private int meat;
  private int vegetables;
  private int fruits;
  private Till till;

  public Customer(int meat, int vegetables, int fruits, Till till) {
    this.ID = counter;
    counter++;
    this.meat = meat;
    this.vegetables = vegetables;
    this.fruits = fruits;
    this.till = till;
  }

  public int getID() {
    return this.ID;
  }

  public int getMeat() {
    return this.meat;
  }

  public int getVegetables() {
    return this.vegetables;
  }

  public int getFruits() {
    return this.fruits;
  }

  public Till getTill() {
    return this.till;
  }

  @Override
  public String toString() {
    return "Customer {" + " ID='" + getID() + "'" + ", meat='" + getMeat() + "'" + ", vegetables='" + getVegetables()
        + "'" + ", fruits='" + getFruits() + "'" + "}";
  }

}