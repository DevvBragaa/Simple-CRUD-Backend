package com.dev.crud.mapper.viewMapper

import com.dev.crud.domain.User
import com.dev.crud.dto.user.UserDTO
import com.dev.crud.dto.user.ViewUserDTO
import com.dev.crud.mapper.Mapper
import org.springframework.stereotype.Component

@Component
class UserViewMapper : Mapper<User, UserDTO> {
    override fun map(t: User): UserDTO {
        return UserDTO(
            name = t.name,
            phone = t.phone,
            email = t.email,
            password = t.password,
            address = t.address,
            roles = t.roles
        )
    }

    fun mapViewUser(t: User): ViewUserDTO {
        return ViewUserDTO(
            id = t.id!!,
            email = t.email,
            phone = t.phone,
            address = t.address,
            roles = t.roles
        )
    }

    override fun mapList(t: List<User>): List<UserDTO> {
        return t.map { map(it) }
    }
}