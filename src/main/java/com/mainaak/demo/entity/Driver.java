package com.mainaak.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "drivers")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @Column(name = "car_number", length = 2)
    private Integer carNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JsonProperty("constructor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference(value = "constructor_ref")
    private Constructor constructor;

    @JsonProperty("driverCountry")
    @ManyToOne
    @JsonBackReference(value = "dc_reference")
    private DriverCountry driverCountry;
}
