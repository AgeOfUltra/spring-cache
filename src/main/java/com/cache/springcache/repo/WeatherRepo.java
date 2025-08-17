package com.cache.springcache.repo;

import com.cache.springcache.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepo extends JpaRepository<Weather,Long> {

    Optional<Weather> getWeatherByCity(String city);
}
