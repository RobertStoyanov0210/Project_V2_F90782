package classes;

import java.time.LocalDate;

public class Goods {
  private final String goodsName;
  private final int price;
  private LocalDate validity = LocalDate.now();

  public Goods(String goodsName, int price, int plusDays) {
    this.goodsName = goodsName;
    this.price = price;
    this.validity = this.validity.plusDays(plusDays);
  }

  public String getGoodsName() {
    return this.goodsName;
  }

  public int getPrice() {
    return this.price;
  }

  public LocalDate getValidity() {
    return this.validity;
  }

  @Override
  public String toString() {
    return "Goods [" + getGoodsName() + ", price: " + getPrice() + ", validity: " + getValidity() + "]";
  }

}