package com.emilie.Lib7.Repositories;

import com.emilie.Lib7.Models.Dtos.LoanDto;
import com.emilie.Lib7.Models.Entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findById(Long id);

    Loan save (Loan loan);



    void deleteById(Long id);


    Optional<Loan> findByCopyId(Long id);
}
