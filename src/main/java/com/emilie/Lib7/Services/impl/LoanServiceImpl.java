package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.LoanAlreadyExistsException;
import com.emilie.Lib7.Exceptions.LoanNotFoundException;
import com.emilie.Lib7.Models.Dtos.LoanDto;
import com.emilie.Lib7.Models.Entities.Loan;
import com.emilie.Lib7.Repositories.LoanRepository;
import com.emilie.Lib7.Services.contract.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;


    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository){
        this.loanRepository = loanRepository;
    }


    @Override
    public List<LoanDto> findAll() {
        return null;
    }



    @Override
    public LoanDto findById(Long id) throws LoanNotFoundException {
        Optional<Loan> optionalLoan = loanRepository.findById( id );
        if (!optionalLoan.isPresent()){
            throw new LoanNotFoundException("Loan not found");
        }
        Loan loan = optionalLoan.get();
        return loanToLoanDto(loan);
    }


    @Override
    public LoanDto save(LoanDto loanDto) throws LoanAlreadyExistsException {
        Optional<Loan> optionalLoan = loanRepository.findById( loanDto.getId() );
        if (optionalLoan.isPresent()){
            throw new LoanAlreadyExistsException( "loan already exists" );
        }
        Loan loan = loanDtoToLoan( loanDto );
        loan = loanRepository.save( loan );
        return loanDto;
    }

    @Override
    public LoanDto update(LoanDto loanDto) {
        Optional<Loan> optionalLoan = loanRepository.findById( loanDto.getId() );
        Loan loan = loanDtoToLoan( loanDto );
        loan = loanRepository.save( loan );
        return loanToLoanDto( loan );
    }


    @Override
    public boolean deleteById(Long id) throws LoanNotFoundException {
        Optional<Loan> optionalLoan = loanRepository.findById( id );
        if (!optionalLoan.isPresent()){
            throw new LoanNotFoundException( "loan not found" );
        }
        try {
            loanRepository.deleteById( id );
        } catch (Exception e){
            return false;
        }
        return true;
    }


    @Override
    public LoanDto extendLoan(Long id, LoanDto loanDto) throws LoanNotFoundException {
        Optional<Loan> optionalLoan = loanRepository.findById( loanDto.getId() );
        if (!optionalLoan.isPresent()){
            throw new LoanNotFoundException( "loan not found" );
        }
        Loan loan = loanDtoToLoan( loanDto );
        loan = loanRepository.save( loan );
        return loanDto;
    }



    private LoanDto loanToLoanDto(Loan loan) {
        LoanDto loanDto = new LoanDto();
        loanDto.setId( loan.getId() );
        loanDto.setLoanStartDate(loan.getLoanStartDate());
        loanDto.setLoanEndDate( loan.getLoanEndDate() );
        loanDto.setExtended( loan.isExtended() );
        loanDto.setLoanStatus( loan.isLoanStatus() );
        return loanDto;
    }

    private Loan loanDtoToLoan(LoanDto loanDto){
        Loan loan = new Loan();
        loan.setId( loanDto.getId());
        loan.setLoanStartDate( loanDto.getLoanStartDate() );
        loan.setLoanEndDate( loanDto.getLoanEndDate() );
        loan.setExtended( loanDto.isExtended() );
        loan.setLoanStatus( loanDto.isLoanStatus() );
        return loan;

    }


}
