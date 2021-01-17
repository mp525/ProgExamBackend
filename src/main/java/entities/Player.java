/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dtos.PlayerDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author Mathias
 */
@Entity
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private int phone;
    private int age;
    
    
//@JoinTable(name = "memberInfo", joinColumns = {
//    @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
//    @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<SportTeam> teams;

    public Player() {
    }

    public Player(String name, String email, int phone, int age) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.teams = new ArrayList();
    }
    
    public Player(PlayerDTO dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.age = dto.getAge();
        this.teams = new ArrayList();
    }
    
    

    public List<SportTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<SportTeam> teams) {
        this.teams = teams;
    }

    public void addTeam(SportTeam team) {
        this.teams.add(team);
        
    }

    public void removeTeam(SportTeam team) {
        if (team != null) {
            this.teams.remove(team);
        }
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Player[ id=" + id + " ]";
    }

}
