package com.example.Market.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    @Column( name = "firstname")
    private  String firstname;
    @Column(name = "lastname")
    private  String  lastname ;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email ;
    @Column(name = "password")
    private String password ;
    @Column(name = "phoneNum")
    private int phoneNum ;
    @Enumerated(EnumType.STRING)
    private Role role ;


    @OneToMany(mappedBy = "user")
    private List<Commande> commandes;

    // all the user's details
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password ;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true ;
    }
}
