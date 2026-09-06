package ru.nikita.lab2.service.exception;

public class InvalidAmountException extends RuntimeException {
  public InvalidAmountException() {
    super("Amount must be positive");
  }
}