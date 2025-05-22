package com.dev.crud.mapper.formMapper

import com.dev.crud.domain.User
import com.dev.crud.dto.UserDto
import com.dev.crud.mapper.Mapper
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class UserFormMapper : Mapper<UserDto, User> {
    override fun map(t: UserDto): User {
        return User(
            name = t.name,
            phone = t.phone,
            email = t.email,
            passCode = t.password,
            address = t.address,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            role = t.role
        )
    }
}