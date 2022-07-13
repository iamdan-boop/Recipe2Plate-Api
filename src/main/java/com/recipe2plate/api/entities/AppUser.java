package com.recipe2plate.api.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Table(name = "app_users")
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppUser extends BaseEntity {

    @SequenceGenerator(
            name = "app_user_seq",
            sequenceName = "app_user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_user_seq"
    )
    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore
    private String password;

    @ManyToOne
    private Role role;
}
