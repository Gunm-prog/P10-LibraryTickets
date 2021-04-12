package com.emilie.Lib7.Repositories;

import com.emilie.Lib7.Exceptions.LoanAlreadyExistsException;
import com.emilie.Lib7.Exceptions.LoanNotFoundException;
import com.emilie.Lib7.Models.Dtos.LoanDto;
import com.emilie.Lib7.Models.Entities.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository {

    Optional<Loan> findById(Long id);

    Loan save (Loan loan);

    Loan update(Loan loan);

    void deleteById(Long id);

}
