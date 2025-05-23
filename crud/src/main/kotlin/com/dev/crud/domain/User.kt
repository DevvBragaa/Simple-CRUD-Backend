package com.dev.crud.domain

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
    @UpdateTimestamp
    @Column(name = "updatedAt")
    var updatedAt: LocalDateTime?,

    @CreationTimestamp
    @Column(name = "createdAt")
    var createdAt: LocalDateTime,


    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var role: List<UserRole> = mutableListOf()

) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.role
            .map {
                SimpleGrantedAuthority(it.role.roleName)
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