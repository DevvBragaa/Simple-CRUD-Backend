package com.dev.crud.mapper.formMapper

import com.dev.crud.domain.User
import com.dev.crud.dto.UserDto
import com.dev.crud.mapper.Mapper

class UserFormMapper : Mapper<UserDto, User> {
    override fun map(t: UserDto): User {
        return User(
            name = t.name,
            phone = t.phone,
            email = t.email,
            password = t.password,
            address = t.address
        )
    }
}