package com.burhan.atm.model;

public class Account {

  private Long id;

  private String userName;

  private Double amountBalance;

  private Double debt;

  private Double transferredBalance;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Double getAmountBalance() {
    return this.amountBalance;
  }

  public void setAmountBalance(Double amountBalance) {
    this.amountBalance = amountBalance;
  }

  public Double getDebt() {
    return this.debt;
  }

  public void setDebt(Double debt) {
    this.debt = debt;
  }

  public Double getTransferredBalance() {
    return this.transferredBalance;
  }

  public void setTransferredBalance(Double transferredBalance) {
    this.transferredBalance = transferredBalance;
  }
}
