package br.udesc.ceavi.dsw.projetorestjpa.services;

import br.udesc.ceavi.dsw.projetorestjpa.util.KeyGenerator;
import br.udesc.ceavi.dsw.projetorestjpa.util.SimpleKeyGenerator;
import io.jsonwebtoken.Jwts; //jjwt
import java.io.IOException;
import java.security.Key;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@JWTAutenticarTokenInterface
@Priority(Priorities.AUTHENTICATION)
public class JWTAutenticarTokenFilter implements ContainerRequestFilter{
    
//    @Inject
//    private Logger logger;
    
//    @Inject
    private SimpleKeyGenerator keyGenerator;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //ler o header de autorização HTTP da requisicao
        String  autorizacaoHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//        logger.info("Header de Autorizacao: " + autorizacaoHeader);
        
        // validar cabecalho de autorizacao
        if (autorizacaoHeader == null || !autorizacaoHeader.startsWith("Bearer ")){
//            logger.severe("[ATENCAO ==>]: Cabecalho de autenticacao incorreto: " + autorizacaoHeader);
            throw new NotAuthorizedException("Erro: Cabeçalho de autorização não encontrado!");
        }
        
        String token = autorizacaoHeader.substring("Bearer".length()).trim();
        
        try {
            //validar o token
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
//            logger.info("Token válido: " + token);
        }catch(Exception e){
//            logger.severe("[ATENCAO ==>]: Token inválido: " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
            
            
}