package org.openbanking.payment;

import org.openbanking.account.Account;
import java.math.BigDecimal;

public class PaymentImpl implements Payment {
    @Override
    public void makePayment(Account from, Account to, BigDecimal amount) {
        from.withdraw(amount);
        to.deposit(amount);
    }

    private static final class CreditScore {
        private final int score;

        private CreditScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }
}