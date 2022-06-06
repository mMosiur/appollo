package org.umcs.appollo.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;
    public RoleEntity(){}

    public RoleEntity(String name, String description, Set<UserEntity> users){
        this.name = name;
        this.description = description;
        this.users = users;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}