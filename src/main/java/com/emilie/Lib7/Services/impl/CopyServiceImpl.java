package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.CopyAlreadyExistException;
import com.emilie.Lib7.Exceptions.CopyNotFoundException;
import com.emilie.Lib7.Models.Dtos.CopyDto;
import com.emilie.Lib7.Models.Entities.Copy;
import com.emilie.Lib7.Repositories.CopyRepository;
import com.emilie.Lib7.Services.contract.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CopyServiceImpl implements CopyService {

    private final CopyRepository copyRepository;


    @Autowired
    public CopyServiceImpl(CopyRepository copyRepository) {
        this.copyRepository=copyRepository;
    }

    @Override
    public List<CopyDto> findAll() {
        return null;
    }


    @Override
    public CopyDto findById(Long id) throws CopyNotFoundException {
        Optional<Copy> optionalCopy=copyRepository.findById( id );
        if (!optionalCopy.isPresent()) {
            throw new CopyNotFoundException( "copy not found" );
        }
        Copy copy=optionalCopy.get();
        return copyToCopyDto( copy );

    }



    @Override
    public CopyDto save(CopyDto copyDto) throws CopyAlreadyExistException {
        Optional<Copy> optionalCopy=copyRepository.findById( copyDto.getId() );
        if (optionalCopy.isPresent()) {
            throw new CopyAlreadyExistException( "Copy already exists" );
        }
        Copy copy=copyDtoToCopy( copyDto );
        copy=copyRepository.save( copy );
        return copyDto;
    }


    @Override
    public CopyDto update(CopyDto copyDto) {
        Optional<Copy> optionalCopy=copyRepository.findById( copyDto.getId() );
        Copy copy=copyDtoToCopy( copyDto );
        copy=copyRepository.save( copy );
        return copyToCopyDto( copy );
    }


    @Override
    public boolean deleteById(Long id) throws CopyNotFoundException {
        Optional<Copy> optionalCopy=copyRepository.findById( id );
        if (!optionalCopy.isPresent()) {
            throw new CopyNotFoundException( "Copy not found" );
        }
        try {
            copyRepository.deleteById( id );
        } catch (Exception e) {
            return false;
        }
        return true;

    }


    private CopyDto copyToCopyDto(Copy copy) {
        CopyDto copyDto=new CopyDto();
        copyDto.setId( copy.getId() );
        copyDto.setAvailable( copy.isAvailable() );
        copyDto.setBookDto( copy.getBookDto() );
        copyDto.setLibraryDto( copy.getLibraryDto() );
        return copyDto;
    }

    private Copy copyDtoToCopy(CopyDto copyDto) {
        Copy copy=new Copy();
        copy.setId( copyDto.getId() );
        copy.setAvailable( copyDto.isAvailable() );
        copy.setBookDto( copyDto.getBookDto() );
        copy.setLibraryDto( copyDto.getLibraryDto() );
        return copy;

    }


}