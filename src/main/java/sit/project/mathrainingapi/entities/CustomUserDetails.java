package sit.project.mathrainingapi.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Getter
@Setter
public class CustomUserDetails extends User implements UserDetails {
    private String id;
    private String email;
    private String role;

    public CustomUserDetails(String username, String id, String email, String password, String role, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public CustomUserDetails(String username, String email, String password, String role, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = email;
        this.role = role;
    }


}
