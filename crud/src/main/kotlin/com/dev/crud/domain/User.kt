package com.dev.crud.domain

import com.dev.crud.util.EnumRole
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime


@Entity
@Table(name = "users")
@SequenceGenerator(name = "seq_user", initialValue = 1, allocationSize = 1)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "password")
    var passCode: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "phone")
    var phone: String,

    @Column(name = "email")
    var email: String,


    @OneToOne(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinColumn(name = "address_id")
    var address: Address,

    @CreationTimestamp
    @Column(name = "createdAt")
    var createdAt: LocalDateTime,

    @UpdateTimestamp
    @Column(name = "updatedAt")
    var updatedAt: LocalDateTime?,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: List<EnumRole>

) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.role
            .map {
                SimpleGrantedAuthority(it.name)
            }.toMutableList()
    }

    override fun getPassword(): String {
        return this.passCode
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonLocked(): Boolean {
        return super.isAccountNonLocked()
    }

    override fun isCredentialsNonExpired(): Boolean {
        return super.isCredentialsNonExpired()
    }

    override fun isEnabled(): Boolean {
        return true
    }


}