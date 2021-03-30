package com.mainaak.demo.dao;

import com.mainaak.demo.entity.Constructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConstructorDao extends JpaRepository<Constructor, Integer> {

    public Constructor findTopByName(String name);
    public Constructor findTopByRanking(Integer ranking);
    public Constructor findConstructorByDriversCarNumber(Integer id);
    public Constructor findTopByDriversDriverCountryTemperature(String temperature);
}
