package com.dev.crud.mapper.formMapper

import com.dev.crud.domain.User
import com.dev.crud.dto.user.UserDTO
import com.dev.crud.mapper.Mapper
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class UserFormMapper : Mapper<UserDTO, User> {
    override fun map(t: UserDTO): User {
        return User(
            name = t.name,
            phone = t.phone,
            email = t.email,
            passCode = t.password,
            address = t.address,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            roles = t.roles ?: mutableListOf()
        )
    }


    override fun mapList(t: List<UserDTO>): List<User> {
        return t.map { map(it) }
    }
}