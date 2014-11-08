package com.figo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by figo on 14/11/4.
 */
@Entity
@Table(name = "ss_plan")
public class Plan extends IdEntity {

    private String name;
    private String days;
    private String budget;
    private String content;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
