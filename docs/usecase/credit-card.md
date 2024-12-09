## Use Cases

### **Use Case 1: Assigning a Credit Limit**
**Actor:** User  
**Preconditions:**
- The credit card must exist.
- No credit limit has been previously assigned.

**Basic Flow:**
1. The user assigns a credit limit to the card.
2. The system validates that no limit has been assigned yet.
3. The system updates the card with the new limit.
4. The system logs an audit entry with the following details:
    - **Entity:** Credit Card
    - **Operation:** Assign Limit
    - **Timestamp:** When the operation occurred
    - **Details:** Amount assigned

**Alternate Flow:**
- If a limit has already been assigned, the system throws an `IllegalStateException`.

---

### **Use Case 2: Viewing Available Credit**
**Actor:** User  
**Preconditions:**
- The credit card must exist.
- A credit limit has been assigned.

**Basic Flow:**
1. The user requests to view the available credit limit.
2. The system calculates the available limit as the total limit minus the used limit.
3. The system displays the available limit.
4. The system logs an audit entry with the following details:
    - **Entity:** Credit Card
    - **Operation:** View Available Credit
    - **Timestamp:** When the operation occurred

---

### **Use Case 3: Making Withdrawals**
**Actor:** User  
**Preconditions:**
- The credit card must exist.
- A credit limit has been assigned.
- The available limit is sufficient for the withdrawal amount.
- The number of withdrawals in the current cycle does not exceed the allowed maximum.

**Basic Flow:**
1. The user requests to withdraw an amount.
2. The system validates that the withdrawal amount does not exceed the available limit.
3. The system validates that the number of withdrawals does not exceed the allowed maximum for the cycle.
4. The system deducts the withdrawal amount from the available limit.
5. The system increments the withdrawal counter for the cycle.
6. The system logs an audit entry with the following details:
    - **Entity:** Credit Card
    - **Operation:** Withdraw
    - **Timestamp:** When the operation occurred
    - **Details:** Amount withdrawn

**Alternate Flow 1:**
- If the withdrawal amount exceeds the available limit, the system throws an `IllegalStateException`.

**Alternate Flow 2:**
- If the withdrawal count exceeds the allowed maximum for the cycle, the system throws an `IllegalStateException`.

---

### **Use Case 4: Repaying Credit**
**Actor:** User  
**Preconditions:**
- The credit card must exist.
- A credit limit has been assigned.
- There is an outstanding balance on the card.

**Basic Flow:**
1. The user requests to repay an amount.
2. The system adds the repaid amount to the available limit.
3. The system logs an audit entry with the following details:
    - **Entity:** Credit Card
    - **Operation:** Repay
    - **Timestamp:** When the operation occurred
    - **Details:** Amount repaid

---

### **Use Case 5: Enforcing Withdrawal Limits in a Cycle**
**Actor:** User  
**Preconditions:**
- The credit card must exist.
- A credit limit has been assigned.
- The number of withdrawals in the current cycle does not exceed the allowed maximum.

**Basic Flow:**
1. The user requests to withdraw an amount.
2. The system validates that the withdrawal amount does not exceed the available limit.
3. The system validates that the withdrawal count does not exceed the allowed maximum for the cycle.
4. The system logs an audit entry for each withdrawal (see Use Case 3).

**Alternate Flow:**
- If the withdrawal count exceeds the allowed maximum for the cycle, the system throws an `IllegalStateException`.

---

### **Use Case 6: Closing the Billing Cycle**
**Actor:** System  
**Preconditions:**
- The credit card must exist.

**Basic Flow:**
1. The system closes the billing cycle for the card.
2. The system resets the withdrawal counter to 0.
3. The system logs an audit entry with the following details:
    - **Entity:** Credit Card
    - **Operation:** Close Billing Cycle
    - **Timestamp:** When the operation occurred

---

### **Use Case 7: Using Credit in a New Billing Cycle**
**Actor:** User  
**Preconditions:**
- The credit card must exist.
- A new billing cycle has started.
- The user has not exceeded the withdrawal limits for the new cycle.

**Basic Flow:**
1. The user requests to withdraw an amount in the new cycle.
2. The system processes the withdrawal according to Use Case 3.
3. The system logs an audit entry for the withdrawal (see Use Case 3).

---
