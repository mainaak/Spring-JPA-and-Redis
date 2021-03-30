package com.mainaak.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "constructors", uniqueConstraints = {
        @UniqueConstraint(name = "constructor_constraint", columnNames = "constructor_name"),
        @UniqueConstraint(name = "constructor_ranking", columnNames = "constructor_rank")
})
public class Constructor {

    @Id
    @SequenceGenerator(name = "incremental", sequenceName = "incremental", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "incremental")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "constructor_name", nullable = false)
    private String name;

    @Column(name = "constructor_rank")
    private Integer ranking;

    @Column(name = "team_principal", columnDefinition = "TEXT")
    private String teamPrincipal;

    @OneToMany(mappedBy = "constructor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "constructor_ref")
    private List<Driver> drivers;
}
