package project.jocl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.jocl.entity.Projet;
import project.jocl.repository.ProjetRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    public void addProject(Projet projet){
        projet.setDateCreation(new Date());
        projet.setStatus("en attente de validation");
        projetRepository.save(projet);
    }

    public List<Projet> getprojects(){
        return projetRepository.findAll();
    }

    public void updateProject(Long id, String newStatus){
        projetRepository.findById(id).ifPresentOrElse(
                p -> {
                    p.setStatus(newStatus);
                    projetRepository.save(p);
                },()-> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Projet n'existe pas !");
                }
        );
    }

    public List<Projet> getProjetByStatusValider() {
        return projetRepository.findByStatus("Accepter");
    }

    public List<Projet> getProjetByStatusRejeter() {
        return projetRepository.findByStatus("Rejeter");
    }

    public List<Projet> getProjetByStatusEnAttente() {
        return projetRepository.findByStatus("En attente");
    }

    public Optional<Projet> getById(Long projectId) {
        return projetRepository.findById(projectId);
    }

}