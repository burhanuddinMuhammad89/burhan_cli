package com.burhan.atm.model;

public class Account {

  private Long id;

  private String userName;

  private Double amountBalance;

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
}
