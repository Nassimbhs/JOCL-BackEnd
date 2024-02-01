package project.jocl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.jocl.entity.Projet;
import project.jocl.service.ProjetService;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetController {

    @Autowired
    private ProjetService projetService;

    @PostMapping("/addProject")
    public void Addproject(@RequestBody Projet projet){
        projetService.addProject(projet);
    }

    @GetMapping("/getProjects")
    public List<Projet> getProjects(){
        return projetService.getprojects();
    }

    @PutMapping("/updateProject/{id}/{newStatus}")
    public void updateProject(@PathVariable Long id, @PathVariable String newStatus){
        projetService.updateProject(id,newStatus);
    }

    @GetMapping("/accepter")
    public List<Projet> getProjetByStatusValider(){
        return projetService.getProjetByStatusValider();
    }

    @GetMapping("/enattente")
    public List<Projet> getProjetByStatusEnAttente(){
        return projetService.getProjetByStatusEnAttente();
    }

    @GetMapping("/rejeter")
    public List<Projet> getProjetByStatusRejeter(){
        return projetService.getProjetByStatusRejeter();
    }

    @GetMapping("/{id}")
    public Projet getProjetById(@PathVariable Long id) {
        return projetService.getById(id).orElse(null);
    }

}