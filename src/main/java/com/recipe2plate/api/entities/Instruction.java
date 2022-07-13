package com.recipe2plate.api.entities;


import lombok.*;

import javax.persistence.*;

@Table(name = "instructions")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Instruction extends BaseEntity {
    @SequenceGenerator(
            sequenceName = "instruction_id_seq",
            name = "instruction_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "instruction_id_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    private String name;

    private String instruction;

    private String imageUrl;
}
