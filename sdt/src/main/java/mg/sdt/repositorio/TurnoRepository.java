package mg.sdt.repositorio;

import mg.sdt.modelo.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    List<Turno> findByEstado(String estado);
    List<Turno> findByUserIdAndEstado(Integer userId, String estado);

}
