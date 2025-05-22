package com.dev.crud.dto

import com.dev.crud.domain.Address
import com.dev.crud.util.EnumRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserDto(
    var name: String,
    var phone: String,
    @Email
    var email: String,
    @NotBlank
    @NotNull
    var password: String,
    @NotNull
    var address: Address,
    var role: List<EnumRole>
) {


}