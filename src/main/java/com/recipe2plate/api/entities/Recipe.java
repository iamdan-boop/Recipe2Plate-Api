package com.recipe2plate.api.entities;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "recipes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
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

    private String videoPreviewUrl;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipes_categories",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipes_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredients_id")
    )
    @ToString.Exclude
    private Set<Ingredient> ingredients = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @ToString.Exclude
    private Set<Instruction> instructions = new HashSet<>();

    @OneToOne
    private AppUser publisher;
}
