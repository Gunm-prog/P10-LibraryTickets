package com.emilie.Lib7.Controllers;

import com.emilie.Lib7.Exceptions.LoanNotFoundException;
import com.emilie.Lib7.Models.Dtos.LoanDto;
import com.emilie.Lib7.Services.contract.LoanService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService){
        this.loanService=loanService;
    }

    @ApiOperation( value="Retrieve loan by id, if registered in database" )
    @GetMapping("/{id}")
    public LoanDto getById(@PathVariable(value="id") Long id) throws LoanNotFoundException{
        return this.loanService.findById(id);
    }

    @ApiOperation( value="Retrieve loan list from database" )
    @GetMapping("/loanList")
    public List<LoanDto> findAll(){
        return this.loanService.findAll();
    }

    @ApiOperation( value="Create loan and save it in database" )
    @PostMapping("/newLoan")
    public ResponseEntity<String> save(@RequestBody LoanDto loanDto)throws LoanNotFoundException{
        loanService.save(loanDto);
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    @ApiOperation( value="extend loan, if registered in database" )
    @PutMapping("/updateLoan")
    public ResponseEntity<LoanDto>update(@RequestBody LoanDto loanDto){
        LoanDto loanDto1 = loanService.update( loanDto );
        return new ResponseEntity<LoanDto>(loanDto1, HttpStatus.OK);
    }

    @PutMapping("/extendLoan")
    public ResponseEntity<LoanDto>extendLoan(@RequestBody  LoanDto loanDto){
        LoanDto loanDto1 = loanService.extendLoan( loanDto );
        return new ResponseEntity<LoanDto>(loanDto1, HttpStatus.OK  );
    }

    @ApiOperation( value="delete loan from database by id" )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>deleteById(@PathVariable(value="id") Long id) throws LoanNotFoundException{
        if (loanService.deleteById( id )){
            return ResponseEntity.status( HttpStatus.OK ).build();
        }else {
            return ResponseEntity.status( 500 ).build();
        }
    }
}
