package project.jocl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.jocl.entity.Projet;
import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet,Long> {
    List<Projet> findByStatus(String status);
}