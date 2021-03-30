package com.mainaak.demo.dao;

import com.mainaak.demo.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriversDao extends JpaRepository<Driver, Integer> {

    public void deleteDriverByCarNumber(Integer carNumber);
}
