package com.emilie.Lib7.Services.impl;

import com.emilie.Lib7.Exceptions.AddressNotFoundException;
import com.emilie.Lib7.Models.Dtos.AddressDto;
import com.emilie.Lib7.Models.Dtos.LibraryDto;
import com.emilie.Lib7.Models.Dtos.UserDto;
import com.emilie.Lib7.Models.Entities.Address;
import com.emilie.Lib7.Models.Entities.Library;
import com.emilie.Lib7.Models.Entities.User;
import com.emilie.Lib7.Repositories.AddressRepository;
import com.emilie.Lib7.Repositories.LibraryRepository;
import com.emilie.Lib7.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddressServiceImpl {

//    private final AddressRepository addressRepository;
//
//
//    @Autowired
//    public AddressServiceImpl(AddressRepository addressRepository) {
//        this.addressRepository=addressRepository;
//
//    }
//
//    @Override
//    public List<AddressDto> findAll(){
//        List<Address> addresses = addressRepository.findAll();
//        List<AddressDto> addressDtos = new ArrayList<>();
//        for(Address address : addresses){
//            AddressDto addressDto = addressToAddressDto(address);
//            addressDtos.add( addressDto );
//        }
//        return addressDtos;
//    }
//
//
//
//
//    @Override
//    public AddressDto findById(Long addressId) throws AddressNotFoundException {
//        Optional<Address> optionalAddress = addressRepository.findById(addressId);
//        if (!optionalAddress.isPresent()){
//            throw  new AddressNotFoundException( "Address not found" );
//        }
//        Address address = optionalAddress.get();
//        return addressToAddressDto(address);
//    }
//
//    private AddressDto addressToAddressDto(Address address) {
//        AddressDto addressDto=new AddressDto();
//        addressDto.setNumber( address.getNumber() );
//        addressDto.setStreet( address.getStreet() );
//        addressDto.setZipCode( address.getZipCode() );
//        addressDto.setCity( address.getCity() );
//
//
//        return addressDto;
//
//    }
//
//    private Address addressDtoToAddress(AddressDto addressDto){
//        Address address = new Address();
//        address.setNumber( addressDto.getNumber() );
//        address.setStreet( addressDto.getStreet() );
//        address.setZipCode( addressDto.getZipCode() );
//        address.setCity( addressDto.getCity() );
//
//        return address;
//    }

}
