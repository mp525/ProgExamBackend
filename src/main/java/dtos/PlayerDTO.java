/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Player;
import entities.SportTeam;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mathias
 */
public class PlayerDTO {
    
    private String name;
    private String email;
    private int phone;
    private int age;
    private String teamName;
    private List<SportTeamDTO> teams;

    public PlayerDTO(String name, String email, int phone, int age, String teamName) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.teamName = teamName;
    }

    public PlayerDTO(Player player) {
        this.name = player.getName();
        this.email = player.getEmail();
        this.phone = player.getPhone();
        this.age = player.getAge();
        this.teams = new ArrayList();
        for (SportTeam team : player.getTeams()) {
            this.teams.add(new SportTeamDTO(team));
        }
        
    }

//    public List<SportTeam> getTeams() {
//        return teams;
//    }
//
//    public void setTeams(List<SportTeam> teams) {
//        this.teams = teams;
//    }
    
    

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "PlayerDTO{" + "name=" + name + ", email=" + email + ", phone=" + phone + ", age=" + age + '}';
    }

}
