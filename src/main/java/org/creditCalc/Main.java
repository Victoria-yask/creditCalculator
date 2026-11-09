package org.creditCalc;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double principal = getValidPrincipal(scanner);
        int years = getValidYears(scanner);
        double annualInterestRate = getValidAnnualInterestRate(scanner);
        int paymentType = getValidPaymentType(scanner);

        boolean hasDiscount = askYesNo(scanner, "Будет первоначальный взнос? (да/нет)");
        double discount = 0;
        if (hasDiscount) {
            discount = getValidDiscount(scanner, principal);
        }

        ICalculator calculator;

        if (paymentType == 1) {
            if (hasDiscount) {
                AnnuityCalculatorWithDiscount calcWithDiscount = new AnnuityCalculatorWithDiscount();
                calcWithDiscount.setDiscount(discount);
                calculator = calcWithDiscount;
            } else {
                calculator = new AnnuityCalculator();
            }
        }else {
            calculator = new DifferentiatedCalculator();
        }

        calculator.setPrincipal(principal);
        calculator.setAnnualInterestRate(annualInterestRate);
        calculator.setYears(years);

        calculator.calculatePayments();

        printSchedule(calculator);
    }

    // проверка ввода суммы долга
    private static double getValidPrincipal(Scanner scanner) {
        final double MIN_PRINCIPAL = 10000.0;
        final double MAX_PRINCIPAL = 10000000.0;

        while (true) {
            System.out.println("Введите сумму долга:");

            if (scanner.hasNext("-?\\d+")) {
                String input = scanner.next();
                double principal = Double.parseDouble(input);

                if (principal < MIN_PRINCIPAL || principal > MAX_PRINCIPAL) {
                    System.out.println("Ошибка: Сумма долга должна быть от 10 000 руб. до 10 000 000 руб.");
                } else {
                    return principal;
                }
            } else {
                System.out.println("Ошибка: Вы ввели неверный формат. Сумма должна быть в целых рублях");
                scanner.next();
            }
        }
    }

    // проверка ввода срока кредита в годах
    private static int getValidYears(Scanner scanner) {
        final int MIN_YEARS = 1;
        final int MAX_YEARS = 30;

        while (true) {
            System.out.println("Введите срок кредита в годах:");

            if (scanner.hasNext("-?\\d+")) {
                String input = scanner.next();
                int years  = Integer.parseInt(input);

                if (years < MIN_YEARS || years > MAX_YEARS) {
                    System.out.println("Ошибка: Cрок кредита должен быть от 1 года до 30 лет");
                }  else {
                    return years;
                }
            } else {
                System.out.println("Ошибка: Вы ввели неверный формат. Срок должен быть в целых годах");
                scanner.next();
            }
        }
    }

    // проверка ввода процентной ставки
    private static double getValidAnnualInterestRate(Scanner scanner) {
        final double MIN_RATE= 1.0;
        final double MAX_RATE = 100.0;

        while (true) {
            System.out.println("Введите процентную ставку:");

            if (scanner.hasNext("-?\\d+([.,]\\d+)?")) {
                String input = scanner.next();

                double annualInterestRate = Double.parseDouble(input.replace(',', '.'));

                if (annualInterestRate < MIN_RATE || annualInterestRate > MAX_RATE) {
                    System.out.println("Ошибка: Процентная ставка должна быть от 1 до 100");
                } else {
                    return annualInterestRate;
                }
            } else {
                System.out.println("Ошибка: Вы ввели неверный формат.");
                scanner.next();
            }
        }
    }

    // проверка ввода вида платежа
    private static int getValidPaymentType(Scanner scanner) {
        final int ANNUITY_TYPE = 1;
        final int DIFFERENTIATED_TYPE = 2;

        while (true) {
            System.out.println("Выберите вид платежа (1 - аннуитетный, 2 - дифференцированный):");

            if (scanner.hasNext("\\d+")) {
                String input = scanner.next();
                int paymentType  = Integer.parseInt(input);

                if (paymentType != ANNUITY_TYPE && paymentType != DIFFERENTIATED_TYPE) {
                    System.out.println("Ошибка: вид платежа должен быть 1 - аннуитетный или 2 - дифференцированный");
                }  else {
                    return paymentType;
                }
            } else {
                System.out.println("Ошибка: Вы ввели неверный формат. Вид платежа должен быть 1 - аннуитетный или 2 - дифференцированный");
                scanner.next();
            }
        }
    }

    //спросить да или нет
    private static boolean askYesNo(Scanner scanner, String question) {
        while (true) {
            System.out.println(question);
            String answer = scanner.next().toLowerCase();

            if (answer.equals("да")) {
                return true;
            } else if (answer.equals("нет")) {
                return false;
            } else {
                System.out.println("Ошибка: введите 'да' или 'нет'.");
            }
        }
    }

    // проверка ввода первоначального взноса
    private static double getValidDiscount(Scanner scanner, double totalPrincipal) {
        final int MIN_DISCOUNT = 1000;
        final int MAX_DISCOUNT = (int)(totalPrincipal * 0.9);

        while (true) {
                System.out.println("Введите сумму первоначального взноса:");

                if (scanner.hasNext("-?\\d+")) {
                    String input = scanner.next();
                    double discount = Double.parseDouble(input);

                    if (discount < MIN_DISCOUNT || discount > MAX_DISCOUNT) {
                        System.out.println("\"Ошибка: Взнос должен быть от " + MIN_DISCOUNT + "руб. до " + MAX_DISCOUNT + " руб.");
                    } else {
                        return discount;
                    }
                } else {
                    System.out.println("Ошибка: Вы ввели неверный формат. Сумма должна быть в целых рублях");
                    scanner.next();
                }
            }
        }


    //расчет графика платежей
    private static void printSchedule(ICalculator calculator) {
        System.out.println("График платежей:");
        for (Payment payment : calculator.getPaymentsSchedule()) {
            System.out.printf("Месяц: %d, Платеж по основному долгу: %.2f, Процентный платеж: %.2f, Общий платеж: %.2f%n",
                    payment.getMonth(), payment.getPrincipalPayment(), payment.getInterestPayment(), payment.getTotalPayment());
        }

            System.out.printf("Общая сумма выплат: %.2f%n", calculator.getTotalPayment());
            System.out.printf("Общая сумма процентов: %.2f%n", calculator.getTotalInterest());
            System.out.printf("Общая сумма переплаты: %.2f%n", calculator.getTotalOverPayment());
    }
}
