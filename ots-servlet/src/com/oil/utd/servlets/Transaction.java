package com.oil.utd.servlets;

public class Transaction {
    private int id;
    private double qty;
    private int buyORsell;
    private int commissionType;
    private double transCost;
    private double commissionRate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getQuantity() {
		return qty;
	}
	public void setQuantity(double quantity) {
		this.qty = quantity;
	}
	
	public int getBuyORSell() {
		return buyORsell;
	}
	public void setBuyORSell(int buy_sell) {
		this.buyORsell = buy_sell;
	}
	public double getTransCost() {
		return transCost;
	}
	public void setTransCost(double cost_transation) {
		this.transCost = cost_transation;
	}
	public int getCommissionType() {
		return commissionType;
	}
	public void setCommissionType(int comtype) {
		this.commissionType = comtype;
	}
	public double getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}
}
