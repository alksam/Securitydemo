package org.example.Entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.Entity.Role;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString
@Table(name = "users")
@Setter
@NoArgsConstructor
public class User {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    @Basic(optional = false)
    @Column(name = "username", nullable = false, unique = true)
    private String Username;
    @Basic(optional = false)
    @Column(name = "password", nullable = false)
    private String Password;
    @JoinTable(name = "user_Role" , joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "username")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "name")})


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Role> roles = new HashSet<>();


    public  boolean verifyPassword(String pw){
        return BCrypt.checkpw(pw, this.Password);
    }

    public User(String username, Set<Role> roleEntities){
        this.Username = username;
        this.roles = roles;
    }
    public User(String username, String password){
        this.Username = username;
        String salt = BCrypt.gensalt(12);
        this.Password = BCrypt.hashpw(password, salt);

    }


   public void addRole(Role role){
       roles.add(role);
       role.getUsers().add(this);
   }

    public void removeRole(Role role){
         roles.remove(role);
         role.getUsers().remove(this);
    }

    public Set<String> getRoleNames(){
        if (roles.isEmpty()){
            return null;
        }
        Set<String> roleNames = new HashSet<>();
        roles.forEach(role
                -> roleNames.add(role. getRoleName()));
        return roleNames;
    }
}
