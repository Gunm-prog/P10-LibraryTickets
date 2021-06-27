package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.*;
import com.emilie.Lib7.Models.Dtos.LoanDto;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Models.Entities.Copy;
import com.emilie.Lib7.Models.Entities.Loan;
import com.emilie.Lib7.Models.Entities.User;
import com.emilie.Lib7.Repositories.CopyRepository;
import com.emilie.Lib7.Repositories.LoanRepository;
import com.emilie.Lib7.Repositories.UserRepository;
import com.emilie.Lib7.Services.contract.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final CopyRepository copyRepository;


    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository,
                           UserRepository userRepository,
                           CopyRepository copyRepository){

        this.loanRepository = loanRepository;
        this.userRepository=userRepository;
        this.copyRepository=copyRepository;
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
    public LoanDto save(LoanDto loanDto) throws
            UserNotFoundException,
            CopyNotFoundException,
            LoanAlreadyExistsException{


        Optional<User> optionalUser = userRepository.findById( loanDto.getUserDto().getUserId() );
        if (!optionalUser.isPresent()){
            throw new UserNotFoundException( "user not found" );
        }

        Optional<Copy> optionalCopy = copyRepository.findById( loanDto.getCopyDto().getId() );
        if (!optionalCopy.isPresent()){
            throw new CopyNotFoundException( "copy not found" );
        }

        Optional<Loan> optionalLoan = loanRepository.findByCopyId(loanDto.getCopyDto().getId());
        if (optionalLoan.isPresent()){
            throw new LoanAlreadyExistsException( "loan already exists" );
        }

        Loan loan = loanDtoToLoan( loanDto );
        loan.setUser( optionalUser.get() );
        loan.setCopy( optionalCopy.get() );
        loan.setLoanStartDate(makePeriodDate( 0 ));
        loan.setLoanEndDate( makePeriodDate( 30 ) );
        loan.setExtended( false );
        loan = loanRepository.save( loan );
        return loanToLoanDto( loan );
    }


    @Override
    public List<LoanDto> findLoansByUserId(Long userId) throws LoanNotFoundException{
        List<Loan> loans = loanRepository.findLoansByUserId(userId);
        if(loans.isEmpty()){
            throw new LoanNotFoundException( "Loan not found" );
        }
        List<LoanDto> loanDtos=new ArrayList<>();
        for (Loan loan : loans) {
            LoanDto loanDto=loanToLoanDto( loan );
            loanDtos.add( loanDto );
        }
        return loanDtos;
    }


    @Override
    public LoanDto update(LoanDto loanDto) throws LoanNotFoundException, UserNotFoundException, CopyNotFoundException {
        Optional<Loan> optionalLoan = loanRepository.findById( loanDto.getId());
        if (!optionalLoan.isPresent()){
            throw new LoanNotFoundException( "loan not found" );
        }

        Optional<User> optionalUser = userRepository.findById(loanDto.getUserDto().getUserId() );
        if (!optionalUser.isPresent()){
            throw new UserNotFoundException( "user not found" );
        }

        Optional<Copy> optionalCopy = copyRepository.findById(loanDto.getCopyDto().getId() );
        if (!optionalCopy.isPresent()){
            throw new CopyNotFoundException( "copy not found" );
        }

        Loan loan = loanDtoToLoan( loanDto );
        loan.setUser( optionalUser.get() );
        loan.setCopy(optionalCopy.get());
        loan = loanRepository.save( loan );
        return loanToLoanDto( loan );
    }

    @Override
    public LoanDto extendLoan(LoanDto loanDto) throws LoanNotFoundException, ImpossibleExtendLoanException{
        Optional<Loan> optionalLoan = loanRepository.findById( loanDto.getId() );
        if (!optionalLoan.isPresent()){
            throw new LoanNotFoundException( "loan not found" );
        }

        Loan loan = optionalLoan.get();
        if (loan.isExtended()){
            throw new ImpossibleExtendLoanException( "impossible extend of loan" );
        }

        Date date = makePeriodDate(30);
        loan.setLoanEndDate( date );
        loan.setExtended( true );

        loan = loanRepository.save( loan );
        return loanDto;
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






    private LoanDto loanToLoanDto(Loan loan) {
        LoanDto loanDto = new LoanDto();
        loanDto.setId( loan.getId() );
        loanDto.setLoanStartDate(loan.getLoanStartDate());
        loanDto.setLoanEndDate( loan.getLoanEndDate() );
        loanDto.setExtended( loan.isExtended() );

        User user = loan.getUser();
        UserDto userDto = new UserDto();
        userDto.setUserId( user.getId() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setUsername( user.getUsername() );
        userDto.setEmail( user.getEmail() );
        loanDto.setUserDto( userDto );

        return loanDto;
    }

    private Loan loanDtoToLoan(LoanDto loanDto){
        Loan loan = new Loan();
        loan.setId( loanDto.getId());
        loan.setLoanStartDate( loanDto.getLoanStartDate() );
        loan.setLoanEndDate( loanDto.getLoanEndDate() );
        loan.setExtended( loanDto.isExtended() );

        User user = new User();
        user.setId(loanDto.getUserDto().getUserId());
        user.setFirstName( loanDto.getUserDto().getFirstName() );
        user.setLastName( loanDto.getUserDto().getLastName() );
        user.setUsername( loanDto.getUserDto().getUsername() );
        user.setEmail( loanDto.getUserDto().getEmail() );
        loan.setUser( user );

        return loan;

    }

    private Date makePeriodDate(int numberOfDays){
        LocalDate localDate = LocalDate.now();
        localDate= localDate.plusDays( numberOfDays );
        Instant instant = localDate.atStartOfDay( ZoneId.systemDefault() ).toInstant();
        Date date = Date.from(instant);

        return date;
    }


}
