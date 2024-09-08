package com.project.credits.enums;

public enum InstallmentStatusEnum {
    /**
     * The installment payment is pending.
     * The payment is expected but has not yet been made.
     */
    PENDING,

    /**
     * The installment has been fully paid.
     * All required payments for this installment have been completed.
     */
    PAID,

    /**
     * The installment has been partially paid.
     * A portion of the installment has been paid, but the full amount is not yet settled.
     */
    PARTIALLY_PAID,

    /**
     * The installment is overdue.
     * The payment is late, and the due date has passed without receipt of payment.
     */
    OVERDUE
}
