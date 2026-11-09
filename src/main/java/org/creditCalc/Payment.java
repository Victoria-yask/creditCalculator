package org.creditCalc;

public class Payment {

    private final int month;
    private final double principalPayment;//основной долг
    private final double interestPayment;// проценты
    private final double totalPayment;// общий платеж (основной долг + проценты)


    public Payment(int month, double principalPayment, double interestPayment) {
        this.month = month;
        this.principalPayment = principalPayment;
        this.interestPayment = interestPayment;
        this.totalPayment = principalPayment + interestPayment;

    }

    public int getMonth() {
        return month;
    }

    public double getPrincipalPayment() {
        return principalPayment;
    }

    public double getInterestPayment() {
        return interestPayment;
    }

    public double getTotalPayment() {
        return totalPayment;
    }


}
