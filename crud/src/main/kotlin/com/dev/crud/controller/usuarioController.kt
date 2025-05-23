package com.dev.crud.controller

import com.dev.crud.dto.UserDto
import com.dev.crud.service.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class usuarioController(
    private val service: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @RequestBody userDto: UserDto
    ): Long {
        return try {
            service.addUser(userDto = userDto)
        } catch (e: Exception) {
            throw e
        }
    }
}