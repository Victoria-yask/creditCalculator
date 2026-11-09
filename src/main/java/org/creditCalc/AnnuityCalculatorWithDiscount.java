package org.creditCalc;

import java.util.ArrayList;
import java.util.List;

public class AnnuityCalculatorWithDiscount implements IDiscount{
    private double principal;
    private double annualInterestRate;
    private int years;
    private double discount;
    private List<Payment> payments;

    public double getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    @Override
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    @Override
    public void setYears(int years) {
        this.years = years;
    }

    @Override
    public void calculatePayments() {
        double principalWithDiscount = principal-discount;
        double monthlyRate = annualInterestRate / 12 / 100;
        double monthlyPayment = principalWithDiscount * (monthlyRate * Math.pow(1 + monthlyRate, years * 12))
                / (Math.pow(1 + monthlyRate, years * 12) - 1);
        payments = new ArrayList<>();

        for (int month = 1; month <= years * 12; month++){
            double interestPayment = principalWithDiscount * monthlyRate;
            double principalPayment = monthlyPayment - interestPayment;
            principalWithDiscount -=principalPayment;

            payments.add(new Payment(month, principalPayment, interestPayment));
        }
    }

    @Override
    public double getTotalPayment() {
        return payments.stream().mapToDouble(Payment::getTotalPayment).sum();
    }

    @Override
    public double getTotalInterest() {
        return payments.stream().mapToDouble(Payment::getInterestPayment).sum();
    }

    @Override
    public double getTotalOverPayment() {
        return getTotalInterest();
    }

    @Override
    public List<Payment> getPaymentsSchedule() {
        return payments;
    }
}
