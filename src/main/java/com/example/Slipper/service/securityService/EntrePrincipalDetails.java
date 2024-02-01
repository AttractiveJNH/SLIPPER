package com.example.Slipper.service.securityService;

import com.example.Slipper.entity.EntreEntity;
import com.example.Slipper.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class EntrePrincipalDetails implements UserDetails {

    private EntreEntity entre;

    public EntrePrincipalDetails(EntreEntity entre){
        this.entre = entre;
    }




    //권한 관련 작업을 하기 위한 role return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return entre.getRole();
            }
        });

        return collections;
    }

    @Override
    public String getPassword() {
        return entre.getEntreprePassword();
    }

    @Override
    public String getUsername() {
        return entre.getEntrepreId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
