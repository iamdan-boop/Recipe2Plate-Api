package com.recipe2plate.api.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Table(name = "verification_otpsG")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationOtp extends BaseEntity {
    @SequenceGenerator(
            sequenceName = "verification_id_seq",
            name = "verification_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "verification_id_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    private Integer otpCode;

    private LocalDateTime expiresAt;

    @OneToOne
    private AppUser appUser;
}
