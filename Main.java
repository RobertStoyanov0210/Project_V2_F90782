import classes.*;

import java.util.Scanner;

public class Main {
  public static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    Shop shop1 = new Shop("SHOP1");
    // creating the shop
    Cashier cashier1 = new Cashier("AAA", shop1);
    Cashier cashier2 = new Cashier("BBB", shop1);
    Cashier cashier3 = new Cashier("CCC", shop1);
    Cashier cashier4 = new Cashier("DDD", shop1);

    Till till1 = new Till(cashier1);
    Till till2 = new Till(cashier2);
    Till till3 = new Till(cashier3);
    Till till4 = new Till(cashier4);

    shop1.addCashiers(cashier1);
    shop1.addCashiers(cashier2);
    shop1.addCashiers(cashier3);
    shop1.addCashiers(cashier4);

    shop1.addTill(till1);
    shop1.addTill(till2);
    shop1.addTill(till3);
    shop1.addTill(till4);
    // Main menu
    int inputInt;
    do {
      mainMenu();
      inputInt = sc.nextInt();
      switch (inputInt) {
        case 1:
          System.out.println(shop1.getInfoShop());
          break;
        case 2:
          addProductsMenu();
          System.out.println("What do you want: ");
          inputInt = sc.nextInt();
          System.out.println("How much: ");
          switch (inputInt) {
            case 1:
              inputInt = sc.nextInt();
              shop1.addGoods("meat", inputInt);
              break;
            case 2:
              inputInt = sc.nextInt();
              shop1.addGoods("vegetable", inputInt);
              break;
            case 3:
              inputInt = sc.nextInt();
              shop1.addGoods("fruit", inputInt);
              break;
            case 4:
              inputInt = sc.nextInt();
              // goods can not be negative value
              while (inputInt <= 0) {
                System.out.println("Please enter positive integer!");
                inputInt = sc.nextInt();

              }
              shop1.addGoods("meat", inputInt);
              shop1.addGoods("vegetable", inputInt);
              shop1.addGoods("fruit", inputInt);
              break;
          }
          break;
        case 3:
          System.out.println("TOTAL EARNINGS: " + shop1.getTotalEarnings());
          break;
        case 4:
          shop1.addCustomers(10);
          try {
            Thread.currentThread().sleep(250);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          break;
        case 9:
          for (int i = 0; i < 30; i++)
            System.out.println();
          break;
        default:
          System.out.println("Goodbye!");
          inputInt = 0;
          break;
      }
    } while (inputInt != 0);

    sc.close();

  }

  // shows the main menu
  public static void mainMenu() {
    System.out.println("-------\n|SHOP1|\n-------");
    System.out.println("1. Show information about SHOP1");
    System.out.println("2. Add products");
    System.out.println("3. Total earnings");
    System.out.println("4. Add 10 customers");
    System.out.println("9. Clear terminal");
    System.out.println("0. Exit");
  }

  // shows all possible products
  public static void addProductsMenu() {
    System.out.println("Products:");
    System.out.println("1. Meat");
    System.out.println("2. Vegetables");
    System.out.println("3. Fruits");
    System.out.println("4. All products");
  }
}