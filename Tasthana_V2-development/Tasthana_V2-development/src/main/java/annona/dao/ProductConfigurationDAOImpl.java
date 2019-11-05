package annona.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.BankConfiguration;
import annona.domain.ProductConfiguration;
import annona.services.DateService;

@Repository
public class ProductConfigurationDAOImpl implements ProductConfigurationDAO {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public ProductConfiguration insertProductConfiguration(ProductConfiguration productConfiguration) {
		em.persist(productConfiguration);
		return productConfiguration;
	}

	@Transactional
	public ProductConfiguration updateProductConfiguration(ProductConfiguration productConfiguration) {
		em.merge(productConfiguration);
		em.flush();
		return productConfiguration;
	}

	@Transactional
	public ProductConfiguration findById(Long id) {
		if (id == null)
			return null;
		return em.find(ProductConfiguration.class, id);

	}
	@Transactional
	public ProductConfiguration findByProductCode(String productCode) {
		Query query = em.createQuery("SELECT o FROM ProductConfiguration o where o.productCode =:productCode");
		query.setParameter("productCode", productCode);
		List<ProductConfiguration> lst = query.getResultList();
		if (lst != null && lst.size() > 0)
			return (ProductConfiguration) lst.get(0);
		else
			return null;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<ProductConfiguration> findAll() {
		Query query = em.createQuery("SELECT o FROM ProductConfiguration o order by id ASC");
		List<ProductConfiguration> lst = query.getResultList();
		if (lst != null && lst.size() > 0)
			return (List<ProductConfiguration>) query.getResultList();
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProductConfiguration> getProductConfigurationList(String productType) {

		Query query = em.createQuery("SELECT o FROM ProductConfiguration o where o.productType =:productType");
		query.setParameter("productType", productType);

		List lst = query.getResultList();
		if (lst != null && lst.size() > 0)
			return (List<ProductConfiguration>) query.getResultList();
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<ProductConfiguration> getProductConfigurationListByProductTypeAndCitizen(String productType,
			String citizen) {

		Date loginDate = DateService.getCurrentDate();
		Query query = em.createQuery(
				"SELECT o FROM ProductConfiguration o where o.productType =:productType and o.citizen=:citizen and date(o.productStartDate)<=date(:loginDate) and date(o.productEndDate)>=date(:loginDate)");
		query.setParameter("productType", productType);
		query.setParameter("citizen", citizen);
		query.setParameter("loginDate", loginDate);

		List lst = query.getResultList();
		if (lst != null && lst.size() > 0)
			return (List<ProductConfiguration>) lst;
		else
			return null;
	}

	public ProductConfiguration getProductConfiguration(String productType, String citizen, String nriAccountType) {

		String qryString = "";

		if (citizen.equalsIgnoreCase("RI"))
			qryString = "SELECT o FROM ProductConfiguration o where o.productType =:productType and o.citizen=:citizen";
		else
			qryString = "SELECT o FROM ProductConfiguration o where o.productType =:productType and o.citizen=:citizen and o.nriAccountType=:nriAccountType";

		Query query = em.createQuery(qryString);
		query.setParameter("productType", productType);
		query.setParameter("citizen", citizen);
		if (citizen.equalsIgnoreCase("NRI"))
			query.setParameter("nriAccountType", nriAccountType);

		return (ProductConfiguration) query.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	public List<ProductConfiguration> getProductConfigurationListByInterestCalculationBasis(
			String interestCalculationBasis) {
		Query query = em.createQuery(
				"SELECT o FROM ProductConfiguration o where upper(o.interestCalculationBasis) =:interestCalculationBasis");
		query.setParameter("interestCalculationBasis", interestCalculationBasis.toUpperCase());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ProductConfiguration> getProductConfigurationListByInterestCompoundingBasis(
			String interestCompoundingBasis) {
		Query query = em.createQuery(
				"SELECT o FROM ProductConfiguration o where upper(o.interestCompoundingBasis) =:interestCompoundingBasis");
		query.setParameter("interestCompoundingBasis", interestCompoundingBasis.toUpperCase());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductConfiguration> getProductConfigurationListByProductTypeAndCitizenAndNRIAccountType(
			String productType, String citizen, String nriAccountType) {
		// TODO Auto-generated method stub
		Query query = em.createQuery(
				"SELECT o FROM ProductConfiguration o where o.productType =:productType and o.citizen=:citizen and o.nriAccountType=:nriAccountType");
		query.setParameter("productType", productType);
		query.setParameter("citizen", citizen);
		query.setParameter("nriAccountType", nriAccountType);
		List lst = query.getResultList();
		if (lst != null && lst.size() > 0)
			return (List<ProductConfiguration>) lst;
		else
			return null;
	}

	
}
