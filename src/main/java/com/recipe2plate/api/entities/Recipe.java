package com.recipe2plate.api.entities;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "recipes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Recipe extends BaseEntity {

    @SequenceGenerator(
            name = "recipe_id_seq",
            sequenceName = "recipe_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "recipe_id_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    private String recipeName;

    private String description;

    private String previewImageUrl;

    private String previewVideoUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipes_categories",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    private Set<Category> categories;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipes_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredients_id")
    )
    private Set<Ingredient> ingredients;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private Set<Instruction> instructions;

    @OneToOne
    private AppUser publisher;
}
