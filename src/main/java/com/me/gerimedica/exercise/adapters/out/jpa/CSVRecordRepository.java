package com.me.gerimedica.exercise.adapters.out.jpa;

import com.me.gerimedica.exercise.adapters.out.jpa.entities.CSVRecordEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CSVRecordRepository extends CrudRepository<CSVRecordEntity, UUID> {

    List<CSVRecordEntity> findAllByCodeIn(List<String> codes);

    Optional<CSVRecordEntity> findByCode(String code);
}
