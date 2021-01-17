/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dtos.SportDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Mathias
 */
@Entity
@NamedQuery(name = "Sport.deleteAllRows", query = "DELETE from Sport")
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    
    @OneToMany(mappedBy = "sport", cascade = CascadeType.PERSIST)
    private List<SportTeam> teams;

    public Sport(String name, String description) {
        this.name = name;
        this.description = description;
        this.teams = new ArrayList();
    }

    public Sport(SportDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.teams = new ArrayList();
    }

    
    
    
    

    public Sport() {
    }
    
    public List<SportTeam> getTeams() {
        return teams;
    }
    
    public void addTeam(SportTeam team) {
        this.teams.add(team);
        if(team != null){
            team.setSport(this);
        }
    }
    
        public void removeTeam(SportTeam team) {
        if(team != null){
            this.teams.remove(team);
        }
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
        if (!(object instanceof Sport)) {
            return false;
        }
        Sport other = (Sport) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Sport[ id=" + id + " ]";
    }
    
}
