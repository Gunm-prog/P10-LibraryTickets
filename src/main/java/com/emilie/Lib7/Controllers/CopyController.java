
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
   /* @ApiOperation( value="Retrieve a book by its title, if it is registered in database" )
    @GetMapping("/title")
    public ResponseEntity<BookDto> findByTitle(@RequestParam String title) throws BookNotFoundException, UnsupportedEncodingException {
        BookDto bookDto = bookService.findByTitle( title );
        *//*bookDto.setTitle( title );*//*
        return new ResponseEntity<BookDto>(bookDto,HttpStatus.OK);
    }*/

    @ApiOperation( value= "Retrieve books which are registered in database" )
    @GetMapping("/search")
    public ResponseEntity<List<CopyDto>> searchCopies(@RequestParam(value="libraryId", required=false) Long libraryId,
                                                      @RequestParam(value = "title", required = false) String title,
                                                      @RequestParam(value="isbn", required=false) String isbn,
                                                      @RequestParam(value="firstName", required=false) String firstName,
                                                      @RequestParam(value="lastName", required=false) String lastName){

        List<CopyDto> copyDtos = copyService.searchCopies(libraryId,title, isbn, firstName, lastName  );
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

