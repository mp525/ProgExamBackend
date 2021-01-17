/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PlayerDTO;
import dtos.SportDTO;
import dtos.SportTeamDTO;
import errorhandling.MissingInputException;
import facades.SportFacade;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Mathias
 */
@Path("sport")
public class SportResource {
    private final SportFacade facade = new SportFacade();
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SportResource
     */
    public SportResource() {
    }

    /**
     * Retrieves representation of an instance of rest.SportResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return "{sport}";
    }

    /**
     * PUT method for updating or creating an instance of SportResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allSports(){
        List<SportDTO> list = facade.allSports();
        return GSON.toJson(list);
    }
    
    @Path("all/teams")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allSportTeams(){
        List<SportTeamDTO> list = facade.allSportTeams();
        return GSON.toJson(list);
    }
    
    @Path("all/players")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allPlayers(){
        List<PlayerDTO> list = facade.allPlayers();
        return GSON.toJson(list);
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String addSport(String dto) throws MissingInputException{
        SportDTO toAdd = GSON.fromJson(dto, SportDTO.class);
        SportDTO retVal = facade.addSport(toAdd);
        return GSON.toJson(retVal);
    }
    
    @Path("team")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll()
    public String addTeam(String dto) throws MissingInputException{
        SportTeamDTO toAdd = GSON.fromJson(dto, SportTeamDTO.class);
        SportTeamDTO retVal = facade.addSportTeam(toAdd);
        return GSON.toJson(retVal);
    }
    
    @Path("team/addPlayer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String addToTeam(String dto) throws MissingInputException{
        PlayerDTO toAdd = GSON.fromJson(dto, PlayerDTO.class);
        facade.addPlayerToTeam(toAdd);
        return GSON.toJson("Succes!");
    }
    
    @Path("team")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String editSportTeam(String dto) throws MissingInputException{
        SportTeamDTO toEdit = GSON.fromJson(dto, SportTeamDTO.class);
       
        SportTeamDTO retVal = facade.editSportTeam(toEdit);
        return GSON.toJson(retVal);
    }
    
    @Path("team/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String deleteSportTeam(@PathParam("id") int id){
        facade.deleteSportTeam(id);
        String deleted = "Team was deleted!";
        return GSON.toJson(deleted);
    }
    
    
    @Path("player")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String addPlayer(String dto){
        PlayerDTO toAdd = GSON.fromJson(dto, PlayerDTO.class);
        PlayerDTO retVal = facade.addPlayer(toAdd);
        return GSON.toJson(retVal);
    }
   
}
