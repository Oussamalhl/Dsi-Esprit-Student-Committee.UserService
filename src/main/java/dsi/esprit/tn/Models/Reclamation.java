package dsi.esprit.tn.Models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reclamations")
public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @Temporal(TemporalType.DATE)
    private Date date;

    @NotBlank
    @Size(max = 50)
    private String type;
    @NotBlank
    @Size(max = 50)
    private String target;
    @NotBlank
    @Size(max = 120)
    private Boolean status;

    @NotBlank
    @Size(max = 500)
    private String description;


//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;

}
