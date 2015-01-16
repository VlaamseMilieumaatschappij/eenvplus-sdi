package be.vmm.eenvplus.sdi.model.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import be.vmm.eenvplus.sdi.model.RioolObject;
import be.vmm.eenvplus.sdi.model.code.Code;

import com.vividsolutions.jts.geom.Geometry;

public class RioolStore {

	@PersistenceContext(unitName = "eenvplus")
	protected EntityManager entityManager;

	@Inject
	protected ValidatorFactory validatorFactory;

	public RioolObject getObject(Class<RioolObject> type, Long id) {
		return entityManager.find(type, id);
	}

	public RioolObject getObject(Class<RioolObject> type, Long namespaceId,
			String alternatieveId) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<RioolObject> criteria = builder.createQuery(type);
		Root<RioolObject> root = criteria.from(type);
		criteria.select(root);

		criteria.where(builder.equal(root.get("namespaceId"), namespaceId),
				builder.equal(root.get("alternatieveId"), alternatieveId));

		TypedQuery<RioolObject> query = entityManager.createQuery(criteria);
		query.setMaxResults(1);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean exists(Class<RioolObject> type, Long namespaceId,
			String alternatieveId) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<RioolObject> root = criteria.from(type);
		criteria.select(builder.count(root));

		criteria.where(builder.equal(root.get("namespaceId"), namespaceId),
				builder.equal(root.get("alternatieveId"), alternatieveId));

		TypedQuery<Long> query = entityManager.createQuery(criteria);

		return query.getSingleResult() > 0L;
	}

	public List<RioolObject> query(Class<RioolObject>[] types,
			Geometry location, int maxResults) {

		List<RioolObject> results = new ArrayList<RioolObject>();

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		for (Class<RioolObject> type : types) {
			CriteriaQuery<RioolObject> criteria = builder.createQuery(type);
			Root<RioolObject> root = criteria.from(type);
			criteria.select(root);

			if (location != null) {
				location.setSRID(31370);

				criteria.where(builder.equal(
						builder.function("intersects", Boolean.class,
								root.get("geom"), builder.literal(location)),
						Boolean.TRUE));
			}

			TypedQuery<RioolObject> query = entityManager.createQuery(criteria);
			query.setMaxResults(maxResults);

			results.addAll(query.getResultList());
		}

		return results;
	}

	public Set<ConstraintViolation<RioolObject>> validate(RioolObject object,
			Class<?>... groups) {

		// Fix SRID
		object.getGeom().setSRID(31370);

		Validator validator = validatorFactory.getValidator();

		return validator.validate(object, groups);
	}

	public void persist(RioolObject object) {

		entityManager.joinTransaction();

		Date date = new Date();
		object.setCreationDate(date);
		object.setBeginLifespanVersion(date);

		// Fix SRID
		object.getGeom().setSRID(31370);

		entityManager.persist(object);
	}

	public RioolObject merge(RioolObject object) {

		entityManager.joinTransaction();

		Date date = new Date();
		object.setBeginLifespanVersion(date);

		// Fix SRID
		object.getGeom().setSRID(31370);

		return entityManager.merge(object);
	}

	public void remove(RioolObject object) {

		entityManager.joinTransaction();

		// First get the object from the database
		object = entityManager.getReference(object.getClass(), object.getId());

		// Does a soft delete by setting endLifespanVersion
		entityManager.remove(object);
	}

	public void flush() {

		entityManager.flush();
	}

	public List<Code> getCodes(Class<Code> type) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Code> criteria = builder.createQuery(type);
		Root<Code> root = criteria.from(type);
		criteria.select(root);

		TypedQuery<Code> query = entityManager.createQuery(criteria);

		return query.getResultList();
	}

	public Code getCode(Class<Code> type, Long id) {
		return entityManager.find(type, id);
	}
}
