package com.project.credits.repository;

import com.project.credits.entity.Credit;
import com.project.credits.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long>, JpaSpecificationExecutor<Credit> {

    List<Credit> findByUser(User user);

}
