package com.mainaak.demo.controller;

import com.mainaak.demo.dao.ConstructorDao;
import com.mainaak.demo.dao.DriverCountryDao;
import com.mainaak.demo.dao.DriversDao;
import com.mainaak.demo.entity.Constructor;
import com.mainaak.demo.entity.Driver;
import com.mainaak.demo.entity.DriverCountry;
import com.mainaak.demo.exceptions.ServerErrorException;
import com.mainaak.demo.exceptions.UserErrorException;
import com.mainaak.demo.model.dto.ConstructorDto;
import com.mainaak.demo.service.DriverService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api")
public class CommonController {

    private final ConstructorDao constructorDao;
    private final DriverCountryDao driverCountryDao;
    private final DriverService driverService;

    @Autowired
    public CommonController(ConstructorDao constructorDao, DriverCountryDao driverCountryDao, DriverService driverService) {
        this.constructorDao = constructorDao;
        this.driverService = driverService;
        this.driverCountryDao = driverCountryDao;
    }

    @PostConstruct
//    @GetMapping
    private void populate() {

        Constructor ferrari = new Constructor(null, "Ferrari", 6, "Mattia Binotto", null);
        Constructor mercedes = new Constructor(null, "Mercedes AMG Petronas", 1, "Toto Wolff", null);
        Constructor redBull = new Constructor(null, "Red Bull Racing", 2, "Christian Horner", null);

        Driver ferrari1 = new Driver(16, "Charles", "LeClerc", ferrari, null);
        Driver ferrari2 = new Driver(55, "Carlos", "Sainz", ferrari, null);
        ferrari.setDrivers(Arrays.asList(ferrari1, ferrari2));

        Driver mercedes1 = new Driver(44, "Lewis", "Hamilton", mercedes, null);
        Driver mercedes2 = new Driver(77, "Valterri", "Bottas", mercedes, null);
        mercedes.setDrivers(Arrays.asList(mercedes1, mercedes2));

        Driver redBull1 = new Driver(33, "Max", "Verstappen", redBull, null);
        Driver redBull2 = new Driver(11, "Sergio", "Perez", redBull, null);
        redBull.setDrivers(Arrays.asList(redBull1, redBull2));

        List<Driver> drives = Arrays.asList(ferrari1, ferrari2, mercedes1, mercedes2, redBull1, redBull2);
        List<Constructor> constructors = Arrays.asList(ferrari, mercedes, redBull);

        constructors.forEach(constructorDao::save);
        drives.forEach(driverService::save);
    }

    @GetMapping("constructor/driver/{car_number}")
    private Constructor getConstructorByDriverCarNumber(@PathVariable Integer car_number) {
        return constructorDao.findConstructorByDriversCarNumber(car_number);
    }

    @GetMapping("driver/{car_number}")
    public Driver getDriverById(@PathVariable(name = "car_number") Integer number,
                                @RequestParam(name = "delete", required = false, defaultValue = "false") String del) {
        if (del.equalsIgnoreCase("true")) {
            driverService.removeDriver(number);
            return new Driver();
        }
        return driverService.getByCarNumber(number);
    }

    @PostMapping("driver/country/{country}")
    private void saveCountry(@PathVariable String country) {
        driverCountryDao.save(
                new DriverCountry(country, "Hot", null)
        );
    }

    @GetMapping("constructor/{temperature}")
    private Constructor getConstructorByDriverCountryByTemperature(@PathVariable String temperature) {
        return constructorDao.findTopByDriversDriverCountryTemperature(temperature);
    }

    @SneakyThrows
    @PostMapping("driver")
    private void saveDriver(@RequestBody Driver d) {
        if (d.getConstructor() == null || d.getConstructor().getName() == null)
            throw new UserErrorException("A constructor name has to be provided for saving a driver.");
        Constructor constructor = constructorDao.findTopByName(d.getConstructor().getName());
        if (constructor == null)
            throw new UserErrorException("Constructor with the name " + d.getConstructor().getName() + " was not found.");
        d.setConstructor(constructor);
        driverService.save(d);
    }

    @PostMapping("constructor")
    public ConstructorDto saveConstructor(@RequestBody Constructor c) {

        if (c.getRanking() > 10) {
            throw new UserErrorException("Ranking cannot be more than 10.");
        }

        c.setId(null);
        try {
            if (constructorDao.findTopByName(c.getName()) != null) {
                throw new ServerErrorException("Constructor already exists");
            } else if (constructorDao.findTopByRanking(c.getRanking()) != null) {
                throw new ServerErrorException("Ranking already exists");
            }

            constructorDao.save(c);
            c = constructorDao.findTopByName(c.getName());
            return new ConstructorDto(
                    c.getName(),
                    c.getRanking(),
                    c.getTeamPrincipal(),
                    c.getDrivers()
            );
        } catch (Exception e) {
            throw new ServerErrorException(e.getMessage());
        }
    }
}