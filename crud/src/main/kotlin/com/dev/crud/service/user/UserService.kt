package com.dev.crud.service.user

import com.dev.crud.dto.user.UserDTO
import com.dev.crud.dto.user.ViewUserDTO

interface UserService {
    fun addUser(userDto: UserDTO): ViewUserDTO
    fun addCommonUser(userDto: UserDTO): ViewUserDTO
    fun editUser(userDto: UserDTO, idUser:Long) : ViewUserDTO
    fun deleteUser(id: Long)
    fun getUser(id: Long): ViewUserDTO
    fun listUsers(): List<UserDTO>
}