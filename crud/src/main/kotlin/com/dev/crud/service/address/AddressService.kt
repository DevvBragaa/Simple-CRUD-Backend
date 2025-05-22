package com.dev.crud.service.address

import com.dev.crud.dto.AddresDTO
import com.dev.crud.dto.UserDto

interface AddressService {


    fun addAddress(addresDto: AddresDTO)
    fun putAddress(userDto: UserDto)
    fun deleteAddress(id:Long)
}