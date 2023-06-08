package com.example.assignment;

import org.springframework.beans.factory.annotation.Autowired;
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
        List<Pizza> pizzaList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pizza.class));
        System.out.println("Q2. Pizza available:");
        for (Pizza pizza : pizzaList){
            System.out.println(pizza);
        }
    }

    public static void readPizza(int id, JdbcTemplate jdbcTemplate){
        String sql = "SELECT * FROM Pizza WHERE ID = " + id;
        List<Pizza> pizzaList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pizza.class));
        System.out.println("Q3. Current Selected pizza is : " + pizzaList.get(0));
    }

    public static void updatePizza(int id, String name, double price, JdbcTemplate jdbcTemplate){
        String sqlUpdate = "UPDATE Pizza SET name=?, price=? WHERE id=?";
        if (jdbcTemplate.update(sqlUpdate, name, price, id) != 0){
            System.out.println("Q4. update success! ");
        }else{
            System.out.println("Q4. Cannot update! ID does not exist");
        }
    }

    public static void deletePizza(int id, JdbcTemplate jdbcTemplate){
        String sqlDelete = "DELETE FROM Pizza WHERE id=" + id;
        if (jdbcTemplate.update(sqlDelete) != 0){
            System.out.println("Q5. Delete success! ");
        }else{
            System.out.println("Q5. Cannot delete! ID does not exist");
        }
    }

    public static void main(String[] args){
        ApplicationContext ctx = SpringApplication.run(DataSourceAssignment.class, args);

        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);

        DataSourceAssignment.createPizza(96,"heat pizza", 100.0, jdbcTemplate);
        DataSourceAssignment.readAllPizza(jdbcTemplate);
        DataSourceAssignment.readPizza(2,jdbcTemplate);
        DataSourceAssignment.updatePizza(100, "nude pizza", 111.11, jdbcTemplate);
        DataSourceAssignment.deletePizza(98, jdbcTemplate);

//        System.out.println(tables.get(0).get("name"));
//        String sql = "SELECT * FROM Pizza";
//        List<Pizza> pizzaList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pizza.class));
//        System.out.println(pizzaList.get(1).name);
//        tables.forEach(table -> System.out.println(table));
    }
}
