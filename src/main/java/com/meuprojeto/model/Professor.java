package com.meuprojeto.model;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Professor extends Person {
    private BigDecimal salary;

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
