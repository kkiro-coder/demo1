package com.example.demo1.repo;

import org.hibernate.SQLQuery;
import org.hibernate.loader.custom.sql.SQLCustomQuery;
import org.hibernate.loader.custom.sql.SQLQueryParser;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID>{

    private final EntityManager em;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    @Override
    public List<Map<String, Object>> getListMapObj(String sql) {
        List<Map<String, Object>> res = em.createNativeQuery(sql)
                .unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return res;
    }
}
