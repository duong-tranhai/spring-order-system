package nashtech.training.ordersystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "system_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    public User(String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public long getId(){return id;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
}
