package com.jaimayal.learningmanager.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * An Entity class representing a resource.
 * Matches the values that a tuple can have in the Postgres table "resources".
 */
@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "resources")
@TypeDef(
        name = "postgres_enum",
        typeClass = PostgresEnum.class
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
    @Type(type = "postgres_enum")
    private ResourceType type;

    @Column(name = "added_at")
    private LocalDate addedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status_type")
    @Type(type = "postgres_enum")
    private ResourceStatus status;

    @Column(name = "finished_at")
    private LocalDate finishedAt;
}
