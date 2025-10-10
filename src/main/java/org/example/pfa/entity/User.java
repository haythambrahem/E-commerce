package org.example.pfa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.REMOVE)
    private List<Role> roleList;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.REMOVE)
    private List<Order> orderList;




}


