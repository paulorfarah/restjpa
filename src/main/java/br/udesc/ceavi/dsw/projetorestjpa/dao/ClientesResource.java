/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.dsw.projetorestjpa.dao;

import br.udesc.ceavi.dsw.projetorestjpa.models.Cliente;
import br.udesc.ceavi.dsw.projetorestjpa.services.JWTAutenticarTokenInterface;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author farah
 */
@Path("clientes")
public class ClientesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ClientesResource
     */
    public ClientesResource() {
    }
   
    @GET  
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Cliente getClientes() {
        System.out.println("get clientes..." );
        Dao d = new Dao();
        return (Cliente)d.ler(Cliente.class, 1);
    }
    
    @GET  
    @JWTAutenticarTokenInterface
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idCliente}")
    public Cliente getCliente(@PathParam("idCliente") String id) {
        System.out.println("get cliente..." + id);
        Dao d = new Dao();
        return (Cliente)d.ler(Cliente.class, Integer.parseInt(id));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Cliente addCliente(Cliente cliente){
        System.out.println("POST Cliente: " + cliente);
        Dao d = new Dao();
        d.salvar(cliente);
        return cliente;
    }
    
}
