package com.tuyendv.web.backend.api.entity.system.loginJwt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "SYS_USER")
public class JwtUser implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer usId;

    private String userName;

    private String password;

    private String fullName;

    @Column (length = 15)
    private String phone;

    private String email;

    @Column (length = 1)
    private String gender;

    @Column (length = 1)
    private String useYn;

}
