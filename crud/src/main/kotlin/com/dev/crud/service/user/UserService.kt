package com.dev.crud.service.user

import com.dev.crud.dto.UserDto

interface UserService {


    fun addUser(userDto: UserDto) : Long
    fun putuser(userDto: UserDto)
    fun deleteUser(id:Long)
}