package org.creditCalc;

import java.util.List;

public interface ICalculator {
    void setPrincipal(double principal);
    void setAnnualInterestRate(double annualInterestRate);
    void setYears(int years);
    void calculatePayments();
    double getTotalPayment();
    double getTotalInterest();
    double getTotalOverPayment();
    List<Payment> getPaymentsSchedule();

}
