package com.example.demo.person;

import io.vavr.collection.List;

import org.jdbi.v3.freemarker.UseFreemarkerSqlLocator;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

@UseFreemarkerSqlLocator
public interface PersonDao {

    @SqlQuery
    @RegisterFieldMapper(Person.class)
    List<Person> getPersons();
}
