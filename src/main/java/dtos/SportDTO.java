/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Sport;

/**
 *
 * @author Mathias
 */
public class SportDTO {
    private String name;
    private String description;

    public SportDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
     public SportDTO(Sport sport) {
        this.name = sport.getName();
        this.description = sport.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SportDTO{" + "name=" + name + ", description=" + description + '}';
    }
    
    
    
}
