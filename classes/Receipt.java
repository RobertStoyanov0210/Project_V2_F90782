package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Receipt implements Serializable {
  private int ID;
  private static int counter = 0;
  private final Cashier cashier;
  private final LocalDate date;
  private final String time;
  private int meat;
  private int vegetables;
  private int fruits;

  public Receipt(Cashier cashier, int meat, int vegetables, int fruits) {
    this.ID = counter;
    counter++;
    this.cashier = cashier;
    this.date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalTime today = LocalTime.now();
    this.time = today.format(formatter);
    this.meat = meat;
    this.vegetables = vegetables;
    this.fruits = fruits;
  }
  public String printProducts() {
    String res = "";
    if (this.meat > 0)
      res += String.format("%-20s", "Meat") + "x" + meat + String.format("%10s", cashier.getWorkplace().getMEAT_PRICE())
          + "\n";
    if (vegetables > 0)
      res += String.format("%-20s", "Vegetables") + "x" + vegetables
          + String.format("%10s", cashier.getWorkplace().getVEGETABLE_PRICE()) + "\n";
    if (fruits > 0)
      res += String.format("%-20s", "Fruits") + "x" + fruits
          + String.format("%10s", cashier.getWorkplace().getFRUIT_PRICE()) + "\n";
    int meatPrice = this.cashier.getWorkplace().getMEAT_PRICE();
    int vegetablesPrice = this.cashier.getWorkplace().getVEGETABLE_PRICE();
    int fruitsPrice = this.cashier.getWorkplace().getFRUIT_PRICE();
    int total = meatPrice * this.meat + vegetablesPrice * this.vegetables + fruitsPrice * this.fruits;
    res += "Total:" + String.format("%26s", total);
    return res;
  }

  public void writeReceipt() {
      String fileName = "C:\\Users\\rstoi\\Desktop\\Receipts\\receipt_" + ID + ".txt";
      while(new File(fileName).exists()){
        fileName = "C:\\Users\\rstoi\\Desktop\\Receipts\\receipt_" + ++ID + ".txt";
      }

      try (FileWriter fw = new FileWriter(fileName, true)) {
        fw.write(this + printProducts());
        this.cashier.getWorkplace().addReceipts(this);
      } catch (FileNotFoundException e) {
        System.out.println("File not found" + e);
      } catch (IOException e) {
        System.out.println("IOException" + e);
      }
  }

  @Override
  public String toString() {
    return "Receipt #" + this.ID + "\n\nCashier: " + this.cashier.getName() + "\nDate: " + this.date + "\nTime: "
        + this.time + "\n\n" + String.format("%-20s", "Product") + String.format("%-10s", "Value") + "Price\n";
  }

}