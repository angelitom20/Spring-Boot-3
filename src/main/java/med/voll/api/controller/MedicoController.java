package med.voll.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.DatosActualizarMedico;
import med.voll.api.medico.DatosListadoMedico;
import med.voll.api.medico.DatosRegistroMedico;
import med.voll.api.medico.DatosRespuestaMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;

	@PostMapping
	public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentBuilder) {
		System.out.println("El request llego");
		//System.out.println(datosRegistroMedico);
		//medicoRepository.save(new Medico(datosRegistroMedico));
		Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
		DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
				medico.getTelefono(),medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(),
						null, null, null, null));
		URI url = uriComponentBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuestaMedico);
	}
	
	@GetMapping
	public ResponseEntity<Page<DatosListadoMedico>> ListadoMedico(@PageableDefault(size= 2) Pageable paginacion){//el pageable llega del frontend
		
		//return medicoRepository.findAll(paginacion).map(DatosListadoMedico:: new );
		//return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico:: new );
		return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico:: new ));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
		Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
		medico.actualizarDatos(datosActualizarMedico);
		return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(),
				medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(), null, null, null, null)));
	}
	
	@DeleteMapping("/{id}")//entre corchetes el nombre de la variable
	@Transactional
	public ResponseEntity eliminarMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		//medicoRepository.delete(medico); //borra los datos hasta en la base de datos
		medico.desactivarMedico();//desactiva al medico	
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")//entre corchetes el nombre de la variable
	//@Transactional //no es necesario ya que no alteramos nada
	public ResponseEntity retornaDatosMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(),
				medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(), null, null, null, null));
		return ResponseEntity.ok(datosMedico);
	}
	
}
