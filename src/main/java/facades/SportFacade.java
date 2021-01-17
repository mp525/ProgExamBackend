/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PlayerDTO;
import dtos.SportDTO;
import dtos.SportTeamDTO;
import entities.Player;
import entities.Sport;
import entities.SportTeam;
import errorhandling.MissingInputException;
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

    public SportDTO addSport(SportDTO dto) throws MissingInputException {
        if(dto.getName().length() < 2 ||  dto.getName() == null ){
            throw new MissingInputException("Name of sport can't be below 2 characters!");
        }
        
        EntityManager em = emf.createEntityManager();

        Sport sport = new Sport(dto);

        em.getTransaction().begin();
        em.persist(sport);
        em.getTransaction().commit();

        return dto;
    }

    public SportTeamDTO addSportTeam(SportTeamDTO dto) throws MissingInputException {
        if(dto.getTeamName().length() < 1){
            throw new MissingInputException("Name of team can't be below 1 character!");
        }
        
        EntityManager em = emf.createEntityManager();

        try {
            SportTeam team = new SportTeam(dto);
            TypedQuery<Sport> query = em.createQuery("Select s from Sport s where s.name = :name", Sport.class);
            query.setParameter("name", dto.getSportName());
            List<Sport> list = query.getResultList();
            Sport sport;
            try{
            sport = list.get(0);
            }catch(ArrayIndexOutOfBoundsException e){
                throw new MissingInputException("Sport does not exist, or was not inputtet!");
            }
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

    public SportTeamDTO editSportTeam(SportTeamDTO dto) throws MissingInputException {
        if(dto.getTeamName().length() < 1 || dto.getTeamName() == null){
            throw new MissingInputException("Name of team can't be below 1 character!");
        }
        EntityManager em = emf.createEntityManager();
        SportTeam team = em.find(SportTeam.class, dto.getId());
        try {

            TypedQuery<Sport> query = em.createQuery("Select s from Sport s where s.name = :name", Sport.class);
            query.setParameter("name", dto.getSportName());
            List<Sport> list = query.getResultList();
            Sport sport;
             try{
            sport = list.get(0);
            }catch(ArrayIndexOutOfBoundsException e){
                throw new MissingInputException("Sport does not exist, or was not inputtet!");
            }
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

    public PlayerDTO addPlayer(PlayerDTO dto) {
        EntityManager em = emf.createEntityManager();
        Player player = new Player(dto);
        TypedQuery<SportTeam> query = em.createQuery("Select s from SportTeam s where s.teamName = :name", SportTeam.class);
            query.setParameter("name", dto.getTeamName());
            List<SportTeam> list = query.getResultList();
            SportTeam team = list.get(0);
            team.addPlayer(player);
            
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
            
            PlayerDTO retDTO = new PlayerDTO(player);
        
        return retDTO;
    }
    
    public List<PlayerDTO> allPlayers() {
        EntityManager em = emf.createEntityManager();
        List<PlayerDTO> retList = new ArrayList();

        TypedQuery<Player> query = em.createQuery("Select p from Player p", Player.class);
        List<Player> list = query.getResultList();

        for (Player p : list) {
            retList.add(new PlayerDTO(p));
        }

        return retList;
    }
    
    public void addPlayerToTeam(PlayerDTO dto){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createQuery("Select p from Player p where p.email = :email", Player.class);
            query.setParameter("email", dto.getEmail());
            List<Player> list = query.getResultList();
            Player p = list.get(0);
        
        TypedQuery<SportTeam> query2 = em.createQuery("Select s from SportTeam s where s.teamName = :name", SportTeam.class);
            query2.setParameter("name", dto.getTeamName());
            List<SportTeam> list2 = query2.getResultList();
            SportTeam team = list2.get(0);
            team.addPlayer(p);
            
            em.getTransaction().begin();
            em.merge(team);
            em.getTransaction().commit();
            
    }

}
