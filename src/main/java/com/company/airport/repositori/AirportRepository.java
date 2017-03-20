package com.company.airport.repositori;

import com.company.airport.model.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Антон on 09.03.2017.
 */
@Repository
public interface AirportRepository extends CrudRepository<Airport, Integer> {
}
