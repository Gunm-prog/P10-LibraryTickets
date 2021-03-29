
package com.emilie.Lib7.Services.contract;



import com.emilie.Lib7.Models.Dtos.AddressDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.emilie.Lib7.Models.Entities.Library;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryService {


    List<LibraryDto> findAll();


    Library findById(Long id);


List<LibraryDto> findByAddress(AddressDto address);

}

