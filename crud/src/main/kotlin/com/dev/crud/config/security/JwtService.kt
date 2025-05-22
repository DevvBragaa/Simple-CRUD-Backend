package com.dev.crud.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${secret.key}") private val secretKey: String,
    @Value("\${secret.expiration}") private val expiration: Long
) {

    fun extractUsername(token: String): String? {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(
        token: String,
        claimsResolver: (Claims) -> T
    ): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun generateTokenBuilder(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return buildToken(extraClaims, userDetails, expiration)
    }

    private fun buildToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails,
        expiration: Long
    ): String {
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), Jwts.SIG.HS256)
            .compact()
    }


    fun generateToken(userDetails: UserDetails): String {
        return generateTokenBuilder(HashMap(), userDetails)
    }

    fun getExpirationTime(): Long {
        return expiration
    }


    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username.equals(userDetails.username) && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }


    private fun getSignInKey(): SecretKey {
        val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}