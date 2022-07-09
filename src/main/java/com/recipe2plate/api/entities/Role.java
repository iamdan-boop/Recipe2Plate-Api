package com.recipe2plate.api.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "roles")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @SequenceGenerator(
            name = "role_id_seq",
            sequenceName = "role_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_id_seq"
    )
    @Id
    private Long id;

    private String roleName;
}
