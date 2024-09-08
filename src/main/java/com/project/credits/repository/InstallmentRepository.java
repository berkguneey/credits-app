package com.project.credits.repository;

import com.project.credits.entity.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {

    @Query("SELECT i FROM Installment i WHERE i.dueDate < :today")
    List<Installment> findOverdueInstallments(@Param("today") LocalDate today);

    Optional<Installment> findByInstallmentId(Integer installmentId);

}
