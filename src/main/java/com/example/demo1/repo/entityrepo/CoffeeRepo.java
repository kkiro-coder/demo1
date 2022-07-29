package com.example.demo1.repo.entityrepo;

import com.example.demo1.entity.T_COFFEE;
import com.example.demo1.repo.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CoffeeRepo extends BaseRepository<T_COFFEE, Integer>, JpaRepository<T_COFFEE, Integer>, JpaSpecificationExecutor<T_COFFEE> {
    @Query(nativeQuery = true, value = "SELECT tc.id, tc.nameen, tc.namezh, tc.price FROM t_coffee AS tc")
    List<Map<String, Object>> getlm2();
}
