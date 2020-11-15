package com.example.demo.repository;

import com.example.demo.model.CoronaData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoronaDataRepository extends MongoRepository<CoronaData, String> {
    List<CoronaData> findByIl(String il);
}
