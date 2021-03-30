package com.mainaak.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "driver_origin")
public class DriverCountry {

    @Id
    private String country;

    @Column(name = "weather")
    private String temperature;

    @OneToMany(mappedBy = "driverCountry")
    @JsonManagedReference(value = "dc_reference")
    private List<Driver> drivers;

}
