package org.raidenjpa.spec;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

import org.raiden.exception.NotYetImplementedException;
import org.raidenjpa.db.InMemoryDB;
import org.raidenjpa.reflection.ReflectionUtil;
import org.raidenjpa.spec.criteria.RaidenCriteriaBuilder;
import org.raidenjpa.spec.criteria.RaidenCriteriaQuery;
import org.raidenjpa.util.BadSmell;

public class RaidenEntityManager implements EntityManager {

	@Override
	public void persist(Object entity) {
		// TODO Auto-generated method stub

	}

	@BadSmell("This one could be expensive")
	@Override
	public <T> T merge(T entity) {
		List<String> fields = ReflectionUtil.getBeanFields(entity);
		for (String field : fields) {
			Object attrObject = ReflectionUtil.getBeanField(entity, field);
			if (attrObject == null) {
				continue;
			}
			
			Method method = ReflectionUtil.getMethodByName(entity.getClass(), ReflectionUtil.toMethodName("get", field));
			if (attrObject instanceof Collection) {
				OneToMany oneToMany = method.getAnnotation(OneToMany.class);
				if (oneToMany == null || oneToMany.cascade() == null) {
					continue;
				}
				
				for (Object obj : ((Collection<?>) attrObject)) {
					if (obj.getClass().isAnnotationPresent(Entity.class)) {
						merge(obj);
					}
				}
			} else {
				ManyToOne manyToOne = method.getAnnotation(ManyToOne.class);
				if (manyToOne != null && manyToOne.cascade().length != 0) {
					merge(attrObject);
				}
			}
		}
		
		return InMemoryDB.me().put(entity);
	}

	@Override
	public void remove(Object entity) {
		if (entity == null) {
			throw new IllegalArgumentException();
		}
		
		Object id = ReflectionUtil.getBeanId(entity);
		if (find(entity.getClass(), id) == null) {
			throw new IllegalArgumentException();
		}
		
		InMemoryDB.me().remove(entity.getClass(), id);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return InMemoryDB.me().get(entityClass, primaryKey);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey,
			Map<String, Object> properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey,
			LockModeType lockMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey,
			LockModeType lockMode, Map<String, Object> properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFlushMode(FlushModeType flushMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public FlushModeType getFlushMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lock(Object entity, LockModeType lockMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lock(Object entity, LockModeType lockMode,
			Map<String, Object> properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object entity, Map<String, Object> properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object entity, LockModeType lockMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object entity, LockModeType lockMode,
			Map<String, Object> properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void detach(Object entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LockModeType getLockMode(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createQuery(String jpql) {
		return new RaidenQuery(jpql);
	}
	
	@Override
	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return new RaidenTypedQuery<T>(createQuery(qlString));
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		RaidenCriteriaQuery<T> raidenCriteriaQuery = (RaidenCriteriaQuery<T>) criteriaQuery;
		return new RaidenTypedQuery<T>(createQuery(raidenCriteriaQuery.toJpql()));
	}

	@Override
	public Query createNamedQuery(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createNativeQuery(String sql) {
		throw new NotYetImplementedException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Query createNativeQuery(String sqlString, Class resultClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	public EntityTransaction getTransaction() {
		return new RaidenEntityTransction();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return new RaidenCriteriaBuilder();
	}

	@Override
	public Metamodel getMetamodel() {
		// TODO Auto-generated method stub
		return null;
	}

}
