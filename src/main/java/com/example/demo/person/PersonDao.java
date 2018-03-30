package com.example.demo.person;

import io.vavr.collection.List;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.stringtemplate4.UseStringTemplateSqlLocator;

@UseStringTemplateSqlLocator
public interface PersonDao {

    @SqlQuery
    @RegisterFieldMapper(Person.class)
    List<Person> getPersons();
}
