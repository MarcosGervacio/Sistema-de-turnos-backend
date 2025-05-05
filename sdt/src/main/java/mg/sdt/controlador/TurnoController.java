package mg.sdt.controlador;


import lombok.RequiredArgsConstructor;
import mg.sdt.modelo.ReservarTurnoRequest;
import mg.sdt.modelo.Turno;
import mg.sdt.servicio.TurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/turno")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    // endpoints de ADMINISTRADORES
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<Turno> crearTurno(@RequestBody Turno turno) {
        return ResponseEntity.ok(turnoService.crearTurno(turno));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Turno eliminado correctamente.");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/rechazar/{id}")
    public ResponseEntity<String> rechazarTurno(@PathVariable Integer id) {
        turnoService.rechazarTurno(id);
        return ResponseEntity.ok("Turno rechazado correctamente.");
    }

    // endpoints de USUARIOS

    @PostMapping("/reservar/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Turno> reservarTurno(
            @PathVariable Integer id,
            @RequestBody ReservarTurnoRequest request,
            Principal principal) {

        Turno reservado = turnoService.reservarTurno(
                id,
                principal.getName(),
                request.getDomicilio(),
                request.getServicioId()
        );
        return ResponseEntity.ok(reservado);
    }


    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/disponibles")
    public ResponseEntity<List<Turno>> obtenerTurnosDisponibles() {
        return ResponseEntity.ok(turnoService.obtenerTurnosDisponibles());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/reservados")
    public ResponseEntity<List<Turno>> obtenerTurnosReservados() {
        return ResponseEntity.ok(turnoService.obtenerTurnosReservados());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/cancelados")
    public ResponseEntity<List<Turno>> obtenerTurnosCancelados() {
        return ResponseEntity.ok(turnoService.obtenerTurnosCancelados());
    }

}
