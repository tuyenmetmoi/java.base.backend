package com.tuyendv.web.backend.api.repository.system.loginJwt;

import com.tuyendv.web.backend.api.entity.system.loginJwt.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtUserRepository extends JpaRepository<JwtUser, Integer> {

    Optional<JwtUser> findByUserNameAndUseYn(String userName, String useYn);

    Optional<JwtUser> findByEmailAndUseYn(String email, String useYn);

}
