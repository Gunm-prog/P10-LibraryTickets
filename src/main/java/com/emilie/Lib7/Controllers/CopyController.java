
package com.emilie.Lib7.Controllers;

import com.emilie.Lib7.Exceptions.CopyNotFoundException;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Services.contract.CopyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/copies")
public class CopyController {


    private final CopyService copyService;

    @Autowired
    public CopyController(CopyService copyService){
        this.copyService=copyService;
    }


    @GetMapping("/search")
    public ResponseEntity<List<CopyDto>> searchCopies(@RequestBody CopyDto copyDto){
        List<CopyDto> copyDtos = copyService.searchCopies( copyDto );
        return new ResponseEntity<List<CopyDto>>( copyDtos, HttpStatus.OK );
    }


    @ApiOperation( value="Retrieve copy by id, if registered in database" )
    @GetMapping("/{id}")
    public ResponseEntity<CopyDto> getById(@PathVariable(value="id") Long id) throws CopyNotFoundException{
        CopyDto copyDto = copyService.findById( id );
        return new ResponseEntity<CopyDto>(copyDto, HttpStatus.OK  );
    }

    @ApiOperation( value="Retrieve copy list from database" )
    @GetMapping("/copyList")
    public ResponseEntity<List<CopyDto>> findAll(){
        List<CopyDto> copyDtos = copyService.findAll();
        return new ResponseEntity<List<CopyDto>>(copyDtos, HttpStatus.OK  );
    }


    @ApiOperation( value="Create a copy and save it in database" )
    @PostMapping("/newCopy")
    public ResponseEntity<String> save(@RequestBody CopyDto copyDto) throws CopyNotFoundException{
        copyService.save(copyDto);
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    @ApiOperation( value="update copy saving modifications in database" )
    @PutMapping("/updateCopy")
    public ResponseEntity<CopyDto> update(@RequestBody CopyDto copyDto){
        CopyDto copyDto1 = copyService.update( copyDto );
        return new ResponseEntity<CopyDto>(copyDto1, HttpStatus.OK);
    }

    @ApiOperation( value="delete copy from database by id" )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value="id") Long id) throws CopyNotFoundException{
        if (copyService.deleteById( id )){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else {
            return ResponseEntity.status(500).build();
        }
    }

}

