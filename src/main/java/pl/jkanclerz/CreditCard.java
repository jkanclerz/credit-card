package pl.jkanclerz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreditCard {

    private final UUID uuid;
    private BigDecimal limit;
    private BigDecimal usedLimit = BigDecimal.ZERO;
    private int withdrawals;
    private List<DomainEvent> pendingEvents = new ArrayList<>();

    public CreditCard(UUID uuid) {
        this.uuid = uuid;
    }

    public void assignLimit(BigDecimal amount) {
        if (limitWasAlreadyAssigned()) {
            throw new IllegalStateException();
        }

        limitAssigned(amount);
    }

    private void limitAssigned(BigDecimal amount) {
        this.limit = amount;
    }

    private boolean limitWasAlreadyAssigned() {
        return limit != null;
    }

    public void withdraw(BigDecimal amount) {
        if (notEnoughMoneyToWithdraw(amount)) {
            throw new IllegalStateException();
        }

        if (tooManyWithdrawalsInCycle()) {
            throw new IllegalStateException();
        }

        cardWithdrawn(amount);
    }

    private void cardWithdrawn(BigDecimal amount) {
        this.usedLimit = this.usedLimit.add(amount);
        this.withdrawals++;
    }

    private boolean tooManyWithdrawalsInCycle() {
        return withdrawals >= 45;
    }

    private boolean notEnoughMoneyToWithdraw(BigDecimal amount) {
        return availableLimit().compareTo(amount) < 0;
    }

    void repay(BigDecimal amount) {
        this.usedLimit = this.usedLimit.subtract(amount);
    }


    public void closeCycle() {
        withdrawals = 0;
    }

    public BigDecimal availableLimit() {
        return limit.subtract(usedLimit);
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<DomainEvent> getPendingEvents() {
        return pendingEvents;
    }

    public void eventsFlushed() {
        pendingEvents.clear();
    }
}