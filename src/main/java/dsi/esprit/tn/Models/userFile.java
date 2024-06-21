package dsi.esprit.tn.Models;

import lombok.*;

import javax.persistence.*;
import javax.persistence.TemporalType;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class userFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fileId;

    String fileName;

    String filePath;
    @Temporal(TemporalType.TIMESTAMP)
    Date uploadDate;
    @ManyToOne
    User user;
}
