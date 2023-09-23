package med.voll.api.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.infra.ValidacionDeIntegridad;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteRepository;

@Service
public class AgendaDeConsultaService {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private ConsultaRepository consultaRepository;
	
	public void agendar(DatosAgendarConsulta datos) {
		
		if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
			throw new ValidacionDeIntegridad("id de paciente no encotrado");
		}
		
		if(datos.idMedico()!= null && medicoRepository.existsById(datos.idMedico())) {
			throw new ValidacionDeIntegridad("Este id no fue encontrado");
		}
		
		var paciente = pacienteRepository.findById(datos.idPaciente()).get();
		
		var medico = seleccionarMedico(datos);
		
		var consulta = new Consulta(null,medico,paciente, datos.fecha());
		
		consultaRepository.save(consulta);
	}

	private Medico seleccionarMedico(DatosAgendarConsulta datos) {
		if(datos.idMedico()!=null) {
			return medicoRepository.getReferenceById(datos.idMedico());
		}
		if(datos.especialidad()==null) {
			throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad");
		}
		// TODO Auto-generated method stub
		return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
	}
	
}
