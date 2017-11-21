///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.udesc.ceavi.dsw.projetorestjpa.services;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.StringTokenizer;
//import javax.annotation.security.DenyAll;
//import javax.annotation.security.PermitAll;
//import javax.annotation.security.RolesAllowed;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.container.ResourceInfo;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.Provider;
//import java.lang.reflect.Method;
//import org.glassfish.jersey.internal.util.Base64;
//
///**
// *
// * @author farah
// */
//@Provider
//public class FiltroAutenticacao implements ContainerRequestFilter {
//    
//    private ResourceInfo resourceInfo;
//     
//    private static final String AUTHORIZATION_PROPERTY = "Authorization";
//    private static final String AUTHENTICATION_SCHEME = "Basic";
//    private static final Response ACCESS_DENIED =  Response.status(Response.Status.UNAUTHORIZED).entity("Acesso Negado!").build();
//    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).entity("Acesso Negado!").build();
//
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) {
//        Method method = resourceInfo.getResourceMethod();
//        //Acesso permitido para todos
//        if( ! method.isAnnotationPresent(PermitAll.class))
//        {
//            //Accesso negado para todos
//            if(method.isAnnotationPresent(DenyAll.class))
//            {
//                requestContext.abortWith(ACCESS_FORBIDDEN);
//                return;
//            }
//              
//            //Ler headers da requisição
//            final MultivaluedMap<String, String> headers =
// requestContext.getHeaders();
//              
//            //Gravar cabeçalho de autorização
//            final List<String> authorization =
// headers.get(AUTHORIZATION_PROPERTY);
//              
//            //Negar acesso se não houver autorização
//            if(authorization == null || authorization.isEmpty())
//            {
//                requestContext.abortWith(ACCESS_DENIED);
//                return;
//            }
//              
//            //Ler nome de usuário e senha
//            final String encodedUserPassword = 
//authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
//              
//
//            //Decodificar nome de usuário e senha
//            String usernameAndPassword = new 
//String(Base64.decode(encodedUserPassword.getBytes()));;
//  
//            //Separar nome de usuário e senha
//            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
//            final String username = tokenizer.nextToken();
//            final String password = tokenizer.nextToken();
//              
//            //Imprimir nome de usuário e senha para checar
//            System.out.println(username);
//            System.out.println(password);
//              
//            //verificar acesso do usuário
//            if(method.isAnnotationPresent(RolesAllowed.class))
//            {
//                RolesAllowed rolesAnnotation = 
//method.getAnnotation(RolesAllowed.class);
//                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
//                  
//                //Checar se usuário é válido
//                if( ! isUserAllowed(username, password, rolesSet))
//                {
//                    requestContext.abortWith(ACCESS_DENIED);
//                    return;
//                }
//            }
//        }
//
//    }
//    
//    private boolean isUserAllowed(final String username, final String 
//    password, final Set<String> rolesSet)
//    {
//        boolean isAllowed = false;
//
//        //1. Comparar senha do usuário com a salva no banco de dados
//        //Se forem iguais, definir permissões, senao negar acesso
//
//        //Acessar banco de dados aqui para checar
//        //String userRole = userMgr.getUserRole(username);
//
//        if(username.equals("username") && password.equals("password"))
//        {
//            String userRole = "ADMIN";
//
//            //2. Verificar permissões de acesso
//            if(rolesSet.contains(userRole))
//            {
//                isAllowed = true;
//            }
//        }
//        return isAllowed;
//    }
//}
//
//   
