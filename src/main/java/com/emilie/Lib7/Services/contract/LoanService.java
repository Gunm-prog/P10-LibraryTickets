package com.emilie.Lib7.Services.contract;

import com.emilie.Lib7.Exceptions.LoanAlreadyExistsException;
import com.emilie.Lib7.Exceptions.LoanNotFoundException;
import com.emilie.Lib7.Models.Dtos.LoanDto;
import com.emilie.Lib7.Models.Dtos.UserDto;

import java.util.List;

public interface LoanService {

    LoanDto findById(Long id) throws LoanNotFoundException;


    LoanDto save(LoanDto loanDto) throws LoanAlreadyExistsException;

    LoanDto update(LoanDto loanDto);

    boolean deleteById(Long id) throws  LoanNotFoundException;

    List<LoanDto> findAll();


    LoanDto extendLoan(Long id, LoanDto loanDto) throws LoanNotFoundException;


}
