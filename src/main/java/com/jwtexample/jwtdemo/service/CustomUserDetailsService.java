package com.jwtexample.jwtdemo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    //este metodo realiza la validacion del usuario existente
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if(userName.equals("Estrella")){ //aqui se puede hacer un llamado a la BD postgres con ayuda del repositorio y validar
            return new User("Estrella", "secret", new ArrayList<>()); //esto lo devuelve la bd
        }else{
            throw new UsernameNotFoundException("Usuario no existe");
        }
    }
}
