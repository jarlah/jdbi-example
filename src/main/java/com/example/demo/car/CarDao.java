package com.example.demo.car;

import io.vavr.collection.List;

import org.jdbi.v3.freemarker.UseFreemarkerSqlLocator;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

@UseFreemarkerSqlLocator
public interface CarDao {

    @SqlQuery
    @RegisterFieldMapper(Car.class)
    List<Car> getCars(@Bind("test") Long test, @Define("someunknownstuff") String someunknownstuff);
}
