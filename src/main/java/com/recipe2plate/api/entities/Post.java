package com.recipe2plate.api.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "posts")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Post extends BaseEntity {

    @SequenceGenerator(
            name = "post_id_seq",
            sequenceName = "post_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "post_id_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Id
    private Long id;

    private String description;

    @ManyToOne
    private Recipe referencedRecipe;

    @ManyToOne
    private AppUser postPublisher;
}
