package com.example.DemoSpringBatch.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEST")
public class Test {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;
    private String address;
}
