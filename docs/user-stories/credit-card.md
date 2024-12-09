## User Stories

### **User Story 1: Assigning a Credit Limit**
**As a** user,  
**I want** to assign a credit limit to my credit card,  
**So that** I can define how much I can spend with it.

**Acceptance Criteria:**
1. A user can assign a limit to the credit card if no limit has been assigned yet.
2. Attempting to assign a second limit throws an `IllegalStateException`.

---

### **User Story 2: Viewing Available Credit**
**As a** user,  
**I want** to see my available credit limit,  
**So that** I can track my remaining funds.

**Acceptance Criteria:**
1. After assigning a limit, the available limit should match the assigned amount.
2. The available limit decreases when a withdrawal is made.
3. The available limit increases when a repayment is made.

---

### **User Story 3: Making Withdrawals**
**As a** user,  
**I want** to withdraw an amount from my credit card,  
**So that** I can use my credit for purchases.

**Acceptance Criteria:**
1. A withdrawal is allowed if the available limit is sufficient for the amount.
2. After a withdrawal, the available limit decreases by the withdrawn amount.
3. Attempting to withdraw more than the available limit throws an `IllegalStateException`.

---

### **User Story 4: Enforcing Withdrawal Limits in a Cycle**
**As a** user,  
**I want** the system to limit the number of withdrawals per billing cycle,  
**So that** I cannot exceed the allowable transactions in a cycle.

**Acceptance Criteria:**
1. A user can make up to 45 withdrawals in a billing cycle.
2. Attempting to make a 46th withdrawal in the same billing cycle throws an `IllegalStateException`.
3. At the start of a new billing cycle, the withdrawal counter resets.

---

### **User Story 5: Repaying Credit**
**As a** user,  
**I want** to repay the amount I’ve withdrawn,  
**So that** my available credit increases.

**Acceptance Criteria:**
1. Repaying an amount increases the available limit by the repaid amount.
2. The available limit should not exceed the originally assigned credit limit.

---

### **User Story 6: Using Credit in a New Billing Cycle**
**As a** user,  
**I want** to continue using my credit card after the billing cycle closes,  
**So that** I can make withdrawals without hitting the previous cycle’s limits.

**Acceptance Criteria:**
1. At the end of a billing cycle, the withdrawal count resets.
2. A user can make withdrawals in the new cycle as long as they stay within the credit limit.

---
