package med.voll.api.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.direccion.Direccion;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String email;
	private String documento;
	private Boolean activo;

}
