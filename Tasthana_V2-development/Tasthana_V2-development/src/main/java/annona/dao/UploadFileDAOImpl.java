package annona.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.UploadFile;


@Repository
public class UploadFileDAOImpl implements UploadFileDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Method for entity manager
	 * 
	 * @return
	 */
	public EntityManager entityManager() {
		EntityManager em = new UploadFileDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	
	@Transactional
	public void saveFile(UploadFile uploadFile){
		em.persist(uploadFile);
	}
	
	public List<UploadFile> getAllFile(){
		
		Query q= em.createQuery("Select o from UploadFile as o");
		return q.getResultList();
		
	}


}
