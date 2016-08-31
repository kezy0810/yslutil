package com.qkl.util.help.test;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class Student {  
    private int age;  
    @Length(max=1)
    @Pattern(regexp = "^[0-9]\\d*$|^([0-9]{0,1}(\\.[0-9][0-9]?))$|^([1-9][0-9]*(\\.[0-9][0-9]?))$")
    private String name;  
    public int getAge() {  
        return age;  
    }  
    public void setAge(int age) {  
        this.age = age;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }      
}  
