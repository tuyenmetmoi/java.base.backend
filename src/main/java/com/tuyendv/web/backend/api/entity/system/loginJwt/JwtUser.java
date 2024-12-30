package com.tuyendv.web.backend.api.entity.system.loginJwt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "V_STD_CDP_STAF")
public class JwtUser {

    @Id
    private String staffNo;

    private String korNm;


}
