package com.mainaak.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mainaak.demo.entity.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstructorDto {

    @JsonProperty("Constructor Name")
    private String constructorName;
    @JsonProperty("Rank")
    private Integer rank;
    @JsonProperty("Team Principal")
    private String principal;
    @JsonIgnore
    @JsonProperty("Drivers")
    private List<Driver> drivers;

}
