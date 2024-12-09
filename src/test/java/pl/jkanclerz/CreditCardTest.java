package pl.jkanclerz;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {

    private CreditCard card;

    @BeforeEach
    void setUp() {
        card = new CreditCard(UUID.randomUUID());
    }

    @Test
    void cannotReassignLimit() {
        card.assignLimit(amount(100));
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> card.assignLimit(amount(50)));
        assertNotNull(exception);
    }

    private BigDecimal amount(int i) {
        return BigDecimal.valueOf(i);
    }

    @Test
    void canAssignLimit() {
        card.assignLimit(amount(50));
        assertEquals(amount(50), card.availableLimit());
    }

    @Test
    void canWithdraw() {
        card.assignLimit(amount(100));
        card.withdraw(amount(50));
        assertEquals(amount(50), card.availableLimit());
    }

    @Test
    void cannotWithdrawWhenNotEnoughMoney() {
        card.assignLimit(amount(100));
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> card.withdraw(amount(101)));
        assertNotNull(exception);
    }

    @Test
    void cannotWithdrawWhenTooManyWithdrawalsInCycle() {
        card.assignLimit(amount(100));
        for (int i = 0; i < 45; i++) {
            card.withdraw(amount(1));
        }
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> card.withdraw(amount(1)));
        assertNotNull(exception);
    }

    @Test
    void canRepay() {
        card.assignLimit(amount(100));
        card.withdraw(amount(10));
        card.repay(amount(5));
        assertEquals(amount(95), card.availableLimit());
    }

    @Test
    void canWithdrawInNextCycle() {
        card.assignLimit(amount(100));
        for (int i = 0; i < 45; i++) {
            card.withdraw(amount(1));
        }
        card.closeCycle();
        card.withdraw(amount(1));
        assertEquals(amount(54), card.availableLimit());
    }
}
