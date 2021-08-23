/*package com.emilie.Lib7.Services.impl;

import org.springframework.stereotype.Service;

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

}*/
