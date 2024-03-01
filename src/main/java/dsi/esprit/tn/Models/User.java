package dsi.esprit.tn.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//  List<Reclamation> reclamations;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  private Club club;
////  @ManyToMany(fetch = FetchType.LAZY)
////  @JoinTable(name = "user_clubs_events",
////          joinColumns = @JoinColumn(name = "user_id"),
////          inverseJoinColumns = @JoinColumn(name = "club_id"))
////  private Set<Club> clubs = new HashSet<>();
//
//  @ManyToMany(fetch = FetchType.LAZY)
//  @JoinTable(name = "user_events",
//          joinColumns = @JoinColumn(name = "user_id"),
//          inverseJoinColumns = @JoinColumn(name = "event_id"))
//  private Set<Event> events = new HashSet<>();

  public User(String username,String firstName,String lastName, String email, Boolean sexe,String password) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.sexe = sexe;
  }
}

