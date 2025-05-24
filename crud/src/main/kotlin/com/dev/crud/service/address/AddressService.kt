package com.dev.crud.service.address

import com.dev.crud.dto.AddresDTO
import com.dev.crud.dto.user.UserDTO

interface AddressService {


    fun addAddress(addresDto: AddresDTO)
    fun putAddress(userDto: UserDTO)
    fun deleteAddress(id:Long)
}