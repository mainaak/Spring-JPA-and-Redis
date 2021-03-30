package com.mainaak.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {

    private Integer carNumber;
    private String firstName;
    private String lastName;
    private Map<String, Object> constructor;

}
