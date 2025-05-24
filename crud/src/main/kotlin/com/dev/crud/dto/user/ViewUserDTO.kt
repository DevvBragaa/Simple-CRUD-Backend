package com.dev.crud.dto.user

import com.dev.crud.domain.Address
import com.dev.crud.domain.UserRole

data class ViewUserDTO (
    val id:Long,
    val email:String,
    val phone: String,
    val address: Address,
    val roles: List<UserRole>
) {
}