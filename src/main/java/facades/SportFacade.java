/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.SportDTO;
import dtos.SportTeamDTO;
import entities.Sport;
import entities.SportTeam;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * @author Mathias
 */
public class SportFacade {

    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    private static SportFacade instance;

    public static SportFacade getSportFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SportFacade();
        }
        return instance;
    }

    public SportDTO addSport(SportDTO dto) {
        EntityManager em = emf.createEntityManager();

        Sport sport = new Sport(dto);

        em.getTransaction().begin();
        em.persist(sport);
        em.getTransaction().commit();

        return dto;
    }

    public SportTeamDTO addSportTeam(SportTeamDTO dto) {
        EntityManager em = emf.createEntityManager();

        try {
            SportTeam team = new SportTeam(dto);
            TypedQuery<Sport> query = em.createQuery("Select s from Sport s where s.name = :name", Sport.class);
            query.setParameter("name", dto.getSportName());
            List<Sport> list = query.getResultList();
            Sport sport = list.get(0);
            sport.addTeam(team);
            team.setSport(sport);

            em.getTransaction().begin();
            em.persist(team);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return dto;
    }

    public List<SportDTO> allSports() {
        EntityManager em = emf.createEntityManager();
        List<SportDTO> retList = new ArrayList();

        TypedQuery<Sport> query = em.createQuery("Select s from Sport s", Sport.class);
        List<Sport> list = query.getResultList();

        for (Sport sport : list) {
            retList.add(new SportDTO(sport));
        }

        return retList;
    }

    public List<SportTeamDTO> allSportTeams() {
        EntityManager em = emf.createEntityManager();
        List<SportTeamDTO> retList = new ArrayList();

        TypedQuery<SportTeam> query = em.createQuery("Select st from SportTeam st", SportTeam.class);
        List<SportTeam> list = query.getResultList();

        for (SportTeam team : list) {
            retList.add(new SportTeamDTO(team));
        }

        return retList;
    }

    public SportTeamDTO editSportTeam(SportTeamDTO dto) {
        EntityManager em = emf.createEntityManager();
        SportTeam team = em.find(SportTeam.class, dto.getId());
        try {
            
            TypedQuery<Sport> query = em.createQuery("Select s from Sport s where s.name = :name", Sport.class);
            query.setParameter("name", dto.getSportName());
            List<Sport> list = query.getResultList();
            Sport sport = list.get(0);
            team.getSport().removeTeam(team);
            sport.addTeam(team);
            
            em.getTransaction().begin();
            team.setSport(sport);
            team.setTeamName(dto.getTeamName());
            team.setMinAge(dto.getMinAge());
            team.setMaxAge(dto.getMaxAge());
            team.setPricePerYear(dto.getPricePerYear());
            
            em.merge(team);
            em.getTransaction().commit();
            
            SportTeamDTO retDTO = new SportTeamDTO(team);
            return retDTO;
        } finally {
            em.close();
        }
    }
    
     public void deleteSportTeam(int nr) {

        EntityManager em = emf.createEntityManager();
        SportTeam team = em.find(SportTeam.class, nr);

        try {
            em.getTransaction().begin();
            em.remove(team);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
