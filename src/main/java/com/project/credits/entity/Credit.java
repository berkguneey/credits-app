package com.project.credits.entity;

import com.project.credits.enums.CreditStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "credits")
@Getter
@Setter
public class Credit extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal principalAmount;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private Long interestRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreditStatusEnum status;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Installment> installments = new ArrayList<>();

}
