package dsi.esprit.tn.Payload.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;


public class SignupRequest {
  @Size(min = 3, max = 20)
  private String username;

  @Size(min = 3, max = 20)
  private String firstName;
  @Size(min = 3, max = 20)
  private String lastName;
  @Size(max = 50)
  @Email
  private String email;

  private Boolean sexe;
  private Set<String> role;

  @Size(min = 6, max = 40)
  private String password;

  public SignupRequest(String username, String firstName, String lastName, String email, Boolean sexe, String password) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.sexe = sexe;
    this.password = password;
  }
  public String getFirstName(){return this.firstName;}
  public String getLastName(){return this.lastName;}
  public void setFirtName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastname(String lastName) {
    this.lastName = lastName;
  }

  public Boolean getSexe(){return this.sexe;}

  public void setSexe(Boolean sexe){this.sexe = sexe;}
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRole() {
    return this.role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }
}
