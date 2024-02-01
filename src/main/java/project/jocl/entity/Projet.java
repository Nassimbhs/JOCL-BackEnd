package project.jocl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private Date debut;
    private Date fin;
    private Date dateValidation;
    private String status;
    private String lieu;
    private Double budget;
    private String categorie;
    private Date dateCreation;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<User> userList = new ArrayList<>();

}