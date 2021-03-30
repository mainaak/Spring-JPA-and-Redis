package com.mainaak.demo.dao;

import com.mainaak.demo.entity.DriverCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverCountryDao extends JpaRepository<DriverCountry, String> {

}
