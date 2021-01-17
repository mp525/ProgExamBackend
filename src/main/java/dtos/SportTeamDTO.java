/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.SportTeam;

/**
 *
 * @author Mathias
 */
public class SportTeamDTO {
    private int id;
    private double pricePerYear;
    private String teamName;
    private int minAge;
    private int maxAge;
    private String sportName;

    public SportTeamDTO(double pricePerYear, String teamName, int minAge, int maxAge, String sportName) {
        this.pricePerYear = pricePerYear;
        this.teamName = teamName;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.sportName = sportName;
    }
    
    public SportTeamDTO(int id, double pricePerYear, String teamName, int minAge, int maxAge, String sportName) {
        this.pricePerYear = pricePerYear;
        this.teamName = teamName;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.sportName = sportName;
        this.id = id;
    }
    
    public SportTeamDTO(SportTeam team) {
        this.pricePerYear = team.getPricePerYear();
        this.teamName = team.getTeamName();
        this.minAge = team.getMinAge();
        this.maxAge = team.getMaxAge();
        this.sportName = team.getSport().getName();
        this.id = team.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public double getPricePerYear() {
        return pricePerYear;
    }

    public void setPricePerYear(double pricePerYear) {
        this.pricePerYear = pricePerYear;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    @Override
    public String toString() {
        return "SportTeamDTO{" + "pricePerYear=" + pricePerYear + ", teamName=" + teamName + ", minAge=" + minAge + ", maxAge=" + maxAge + ", sportName=" + sportName + '}';
    }
    
    
    
}
