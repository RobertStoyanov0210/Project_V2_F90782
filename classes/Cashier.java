package classes;

public class Cashier {
  private int ID;
  private static int counter = 1000;
  private String name;
  private Shop workplace;

  public Cashier(String name, Shop workplace) {
    this.ID = counter;
    counter++;
    this.name = name;
    this.workplace = workplace;
  }

  public Shop getWorkplace() {
    return workplace;
  }

  public String getName() {
    return this.name;
  }

  public int getID() {
    return this.ID;
  }

  @Override
  public String toString() {
    return "Cashier {" + " ID='" + getID() + "'" + ", name='" + getName() + "'}";
  }

}