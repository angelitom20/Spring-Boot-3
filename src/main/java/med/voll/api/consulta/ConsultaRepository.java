package med.voll.api.consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
	
	
}
