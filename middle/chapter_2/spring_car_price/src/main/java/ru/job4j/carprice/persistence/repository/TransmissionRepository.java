package ru.job4j.carprice.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carprice.model.Transmission;

@Repository
public interface TransmissionRepository extends CrudRepository<Transmission, Long> {
}
