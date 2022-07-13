package com.recipe2plate.api.entities;


import javax.persistence.*;

@Table(name = "categories")
@Entity
public class Category extends BaseEntity {

    @SequenceGenerator(
            sequenceName = "category_id_seq",
            name = "category_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_id_seq"
    )
    @Id
    private Long id;

    private String categoryName;
}
