package dsi.esprit.tn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userDTO {

    private Long id;

    @NotBlank
    @Size(max = 30)
    private String username;
    @NotBlank
    @Size(max = 30)
    private String firstName;
    @NotBlank
    @Size(max = 30)
    private String lastName;

    private Boolean sexe;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Temporal(TemporalType.DATE)
    private Date date;
    @NotBlank
    @Size(max = 120)
    private String password;


    public userDTO (Long id, String username,String firstName,String lastName, String email, Boolean sexe,String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.sexe = sexe;
    }
}
