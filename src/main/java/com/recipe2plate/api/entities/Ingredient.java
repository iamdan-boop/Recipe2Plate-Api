package com.recipe2plate.api.entities;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "ingredients")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ingredient extends BaseEntity {
    @SequenceGenerator(
            sequenceName = "ingredient_id_seq",
            name = "ingredient_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "ingredient_id_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    private String ingredientName;


    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private List<Recipe> recipes;
}
