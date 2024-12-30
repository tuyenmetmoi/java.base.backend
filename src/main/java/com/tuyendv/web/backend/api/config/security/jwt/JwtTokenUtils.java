package com.tuyendv.web.backend.api.config.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tuyendv.web.backend.api.common.ApiStatus;
import com.tuyendv.web.backend.api.config.security.handler.CustomException;
import com.tuyendv.web.backend.api.dto.system.loginJwt.AuthenticationResponse;
import com.tuyendv.web.backend.api.entity.system.loginJwt.JwtUser;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenUtils {

    @NonFinal
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @NonFinal
    @Value ("${jwt.valid-duration}")
    private long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    private long REFRESHABLE_DURATION;

    private String buildScope(JwtUser user) {
        StringJoiner stringJoiner = new StringJoiner(","); //create string split with ","

        //get role and set in stringJoiner
        //example
//        if (!CollectionUtils.isEmpty(user.getRoles()))
//            user.getRoles().forEach(role -> {
//                stringJoiner.add("ROLE_" + role.getName());
//                if (!CollectionUtils.isEmpty(role.getPermissions()))
//                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
//            });

        return stringJoiner.toString();
    }

    public String generateToken(JwtUser user) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getStaffNo()) //set user id
                    .issuer("tuyendv") //token origin
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plusSeconds(VALID_DURATION)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("scope", buildScope(user)) //custom claim
                    .build();

            //set claim to payload
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());

            //create jwt token
            JWSObject jwsObject = new JWSObject(header, payload);
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token for user id: {} ", user.getStaffNo(), e);
            throw new RuntimeException(e);
        }
    }

    protected SignedJWT verifyToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

            boolean verified = signedJWT.verify(verifier);

            if (!(verified && expirationDate.after(new Date()))) {
                throw new CustomException(ApiStatus.UNAUTHENTICATED);
            }

            //check token exist in black list -> UNAUTHENTICATED

            return signedJWT;
        } catch (ParseException e) {
            log.error("Token is not correct format: {}", token, e);
            throw new CustomException(ApiStatus.INCORRECT_FORMAT);
        } catch (JOSEException e) {
            log.error("Cannot verifier token: {}", token, e);
            throw new CustomException(ApiStatus.BAD_REQUEST_VERIFY_TOKEN);
        }
    }

    protected AuthenticationResponse refreshToken(String token) {
        try {
            SignedJWT signedJWT = verifyToken(token);

            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

            //save token in black list

            //check exist user by userId in token
            JwtUser user = new JwtUser();

            AuthenticationResponse response = new AuthenticationResponse();
            response.setToken(generateToken(user));
            response.setExpiryTime(new Date());

            return response;
        } catch (ParseException e) {
            log.error("Cannot refresh token: {}", token, e);
            throw new CustomException(ApiStatus.BAD_REQUEST_REFRESH_TOKEN);
        }
    }

}
