package com.example.demo.car;

import io.vavr.collection.List;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.stringtemplate4.UseStringTemplateSqlLocator;

@UseStringTemplateSqlLocator
public interface CarDao {

    @SqlQuery
    @RegisterFieldMapper(Car.class)
    List<Car> getCars(@Bind("test") Long test);
}
