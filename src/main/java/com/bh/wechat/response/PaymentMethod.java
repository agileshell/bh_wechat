package com.bh.wechat.response;

import java.io.Serializable;

public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 810699569353574302L;

    private int paymentMethodId;

    private String name;

    private float amount;

    private int baodou;

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getBaodou() {
        return baodou;
    }

    public void setBaodou(int baodou) {
        this.baodou = baodou;
    }

}
