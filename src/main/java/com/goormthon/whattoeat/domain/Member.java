// domain/Member.java
package com.goormthon.whattoeat.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="members", uniqueConstraints=@UniqueConstraint(columnNames = "email"))
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String passwordHash;  // BCrypt 해시 저장

    @Column(nullable=false)
    private String role = "USER";

    public Member(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }
}