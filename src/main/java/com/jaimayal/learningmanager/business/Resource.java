package com.jaimayal.learningmanager.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * An Entity class representing a resource.
 * Matches the values that a tuple can have in the Postgres table "resources".
 */
@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "resources")
@TypeDef(
        name = "resource_enum",
        typeClass = ResourceTypePostgresEnum.class
)
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "resource_type")
    @Type(type = "resource_enum")
    private ResourceType type;

    @Column(name = "added_at")
    private LocalDate addedAt;

    @Column(name = "finished")
    private boolean finished;

    @Column(name = "finished_at")
    private LocalDate finishedAt;
}
