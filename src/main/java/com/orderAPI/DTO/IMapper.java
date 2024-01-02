package com.orderAPI.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.orderAPI.entities.Address;
import com.orderAPI.entities.User;

@Mapper(componentModel = "spring")
public interface IMapper {
	IMapper mapper = Mappers.getMapper(IMapper.class);
	
	public UserResponseDTO convertEntityToDTO(User temp);
	public UserUpdateResponse updateResponse(User temp);
	public AddressResponseDTO addressResponse(Address temp);
	public AddressListResponseDTO getAllAddressesByIdResponse(User temp);
}
