package annona.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.EndUser;
import annona.domain.Organisation;

@Repository
public class OrganisationDAOImpl implements OrganisationDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public Organisation insertUniqueOrganisation(Organisation organisation) {

		Query query = em.createQuery("SELECT o FROM Organisation o where o.organisationName=:organisationName");
		query.setParameter("organisationName", organisation.getOrganisationName());

		if (!query.getResultList().isEmpty()) {
			return (Organisation) query.getResultList().get(0);
		} else {
			em.persist(organisation);
			return organisation;
		}
	}

	public List<Organisation> getAllList() {

		Query query = em.createQuery("SELECT o FROM Organisation o");
		List lst = query.getResultList();
		return (List<Organisation>)lst;

	}

	public Organisation getOrganizationByUsername(String username) {

		Query query = em.createQuery("SELECT o.organisationId,o.organisationName from Organisation as o "
				+ "inner join o.users as u where u.userName=:username ");

		query.setParameter("username", username);
		List<Object[]> organisation = query.getResultList();
		Organisation org = new Organisation();
		for (int i = 0; i < organisation.size(); i++) {

			org.setOrganisationId((Long) organisation.get(i)[0]);
			org.setOrganisationName((String) organisation.get(i)[1]);
		}
		return org;

	}

}
