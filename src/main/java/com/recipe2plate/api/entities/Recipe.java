package com.recipe2plate.api.entities;


import lombok.*;

import javax.persistence.*;

@Table(name = "recipes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Recipe {

    @SequenceGenerator(
            name = "product_id_seq",
            sequenceName = "product_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "product_id_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    private String recipeName;

    private String description;
}
