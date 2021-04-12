package com.emilie.Lib7.Services.contract;

import com.emilie.Lib7.Exceptions.CopyAlreadyExistException;
import com.emilie.Lib7.Exceptions.CopyNotFoundException;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Entities.Copy;

import java.util.List;

public interface CopyService {

    CopyDto findById(Long id) throws CopyNotFoundException;

    CopyDto save(CopyDto copyDto) throws CopyAlreadyExistException;

    CopyDto update(CopyDto copyDto);

    boolean deleteById(Long id) throws  CopyNotFoundException;

    List<CopyDto> findAll();

}
