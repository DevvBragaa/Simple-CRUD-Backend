package com.dev.crud.dto.user

import com.dev.crud.domain.Address
import com.dev.crud.domain.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserDTO(
    var name: String,
    var phone: String,
    @Email
    var email: String,
    @NotBlank
    @NotNull
    var password: String,
    @NotNull
    var address: Address,
    var roles: MutableList<UserRole>? = mutableListOf()
) {

}