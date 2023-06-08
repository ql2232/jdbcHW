package com.example.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.util.*;

public class DataSourceAssignment {

    @Bean
    public DataSource mysqlDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/jdbc_assignment");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    public static void createPizza(int id, String name, double price, JdbcTemplate jdbcTemplate){      //create pizza
        String sql = "INSERT INTO Pizza (id, name, price) VALUES (?, ?, ?)";
        try{
            jdbcTemplate.update(sql, id, name, price);
            System.out.printf("New Pizza Created with ID=%d, name=%s, price=%.2f \n", id, name, price);
        }catch (DuplicateKeyException e){
            System.out.println("ID  already existed! Pizza cannot be created");
        }

    }

    public static void readAllPizza(JdbcTemplate jdbcTemplate){
        String sql = "SELECT * FROM Pizza";
        List<Map<String, Object>> pizzas = jdbcTemplate.queryForList(sql);
        System.out.println("Q2. Pizza available:");
        for (Map<String, Object> pizza : pizzas){
            System.out.println(pizza.get("name"));
        }
    }


    public static void main(String[] args){
        ApplicationContext ctx = SpringApplication.run(DataSourceAssignment.class, args);

        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);

        DataSourceAssignment.createPizza(96,"heat pizza", 100.0, jdbcTemplate);
        DataSourceAssignment.readAllPizza(jdbcTemplate);

//        System.out.println(tables.get(0).get("name"));
//        String sql = "SELECT * FROM Pizza";
//        List<Pizza> pizzaList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pizza.class));
//        System.out.println(pizzaList.get(1).name);
//        tables.forEach(table -> System.out.println(table));
    }
}
