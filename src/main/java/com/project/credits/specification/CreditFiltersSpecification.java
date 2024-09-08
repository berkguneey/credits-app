package com.project.credits.specification;

import com.project.credits.entity.Credit;
import com.project.credits.enums.CreditStatusEnum;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CreditFiltersSpecification implements Specification<Credit> {

    private final Long userId;
    private final CreditStatusEnum status;
    private final LocalDateTime startOfDay;
    private final LocalDateTime endOfDay;

    public CreditFiltersSpecification(Long userId, CreditStatusEnum status, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        this.userId = userId;
        this.status = status;
        this.startOfDay = startOfDay;
        this.endOfDay = endOfDay;
    }

    @Override
    public Predicate toPredicate(Root<Credit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (userId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("user").get("id"), userId));
        }

        if (status != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
        }

        if (startOfDay != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startOfDay));
        }

        if (endOfDay != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endOfDay));
        }

        return predicate;
    }
}
