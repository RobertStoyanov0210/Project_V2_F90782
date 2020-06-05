package classes.Exceptions;

public class NotEnoughGoodsException extends Exception {
  public NotEnoughGoodsException(String errorMessage) {
    super(errorMessage);
  }
}