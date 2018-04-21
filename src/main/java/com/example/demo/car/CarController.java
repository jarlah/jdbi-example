package com.example.demo.car;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("car")
public class CarController {

    private final CarDao carDao;

    @Autowired
    public CarController(Jdbi jdbi) {
        this.carDao = jdbi.onDemand(CarDao.class);
    }

    @GetMapping
    public List<Car> getCars() {
        return carDao.getCars(1L).toJavaList();
    }
}
