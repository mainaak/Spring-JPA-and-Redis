package com.mainaak.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainaak.demo.configuration.RedisConfig;
import com.mainaak.demo.dao.DriversDao;
import com.mainaak.demo.entity.Driver;
import com.mainaak.demo.exceptions.UserErrorException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class DriverService {

    private final DriversDao driversDao;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisConfig redisConfig;
    private ObjectMapper om = new ObjectMapper();

    @Autowired
    public DriverService(DriversDao driversDao, RedisTemplate redisTemplate, RedisConfig redisConfig) {
        this.driversDao = driversDao;
        this.redisTemplate = redisTemplate;
        this.redisConfig = redisConfig;
    }

    @SneakyThrows
//    @Cacheable(cacheNames = "driver", key = "'car_number'+#carNumber")
    public Driver getByCarNumber(Integer carNumber) {
        Thread.sleep(1000);
        String d = redisTemplate.opsForValue().get(redisConfig.getPrefix() + "DRIVER" + carNumber);
        if (d != null)
            return om.readValue(d, Driver.class);

        Optional<Driver> driver = driversDao.findById(carNumber);
        if (!driver.isPresent())
            throw new UserErrorException("Driver with car number " + carNumber + " was not found.");

        redisTemplate.opsForValue().set(redisConfig.getPrefix() + "DRIVER" + carNumber, om.writeValueAsString(driver.get()));
        return driver.get();
    }

//    @CachePut(cacheNames = "driver", key = "'car_number'+#driver.carNumber")
    public Driver save(Driver driver){
        driversDao.save(driver);
        return driversDao.findById(driver.getCarNumber()).get();
    }

    @Transactional
//    @CacheEvict(cacheNames = "driver", key = "'car_number'+#carNumber")
    public void removeDriver(Integer carNumber){
        driversDao.deleteDriverByCarNumber(carNumber);
    }
}
