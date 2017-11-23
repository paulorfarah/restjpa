/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.dsw.projetorestjpa.dao;

import br.udesc.ceavi.dsw.projetorestjpa.models.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * REST Web Service
 *
 * @author farah
 */
@Path("usuarios")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Transactional
public class UsuariosResource {

    @Context
    private UriInfo uriInfo;
    
    @Inject
    private Logger logger;
    
    @Inject
    private KeyGenerator keyGenerator;
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response autenticarUsuario(@FormParam("login") String login,
                                      @FormParam("senha") String senha){ 
        try{
            autenticar(login, senha);
            String token = gerarToken(login);
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        }catch(Exception e){
            return Response.status(UNAUTHORIZED).build();
        }            
    }
    
    private void autenticar(String login, String senha) throws Exception {
        //ler usuario e senha do bd com JPA
        if(!(login.equals("admin") && senha.equals("123456")))
            throw new SecurityException("Usuario ou senha inválidos!");
    }
    
    private String gerarToken(String login){
        Key key = keyGenerator.generateKey();
        Date expirar = Date.from(LocalDateTime.now().plusMinutes(15L).atZone(ZoneId.systemDefault()).toInstant());
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirar)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.info("gerado token para chave " + key + ": " + jwtToken);
        return jwtToken;
    }

    @GET
    @Path("/{idUsuario")
    public Response getUsuario(@PathParam("idUsuario") String id) {
        //buscar usuario do bd com JPA
        Usuario usuario = new Usuario();
        return Response.ok(usuario).build();
    }

    @POST
    public Response create(Usuario usuario){
        Dao dao = new Dao();
        dao.salvar(usuario);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(usuario.getId().toString()).build()).build();
    }
   
    /* falta implementar os métodos para listar todos os usuarios, put e delete */
}
