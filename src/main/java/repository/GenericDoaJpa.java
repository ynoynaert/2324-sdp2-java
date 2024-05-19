package repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class GenericDoaJpa<T> implements GenericDao<T>{

	private static final EntityManagerFactory  emf = Persistence.createEntityManagerFactory("SDPA3");
	protected static final EntityManager em=emf.createEntityManager();
	private final Class<T> type;
	
	public GenericDoaJpa(Class<T> type) {
		this.type = type;
	}
	
	public static void closePersistency() {
		em.close();
		emf.close();
	}
	
	public static void startTransaction() {
		em.getTransaction().begin();
	}
	
	public static void commitTransaction() {
		em.getTransaction().commit();
	}
	
	public static void rollbackTransaction() {
		em.getTransaction().rollback();
	}

	@Override
	public List<T> findAll() {
		return em.createQuery("select entity from " + type.getName() + " entity", type).getResultList();
	}

	@Override
	public T get(int id) {
		return em.find(type, id);
	}

	@Override
	public T update(T object) {
		return em.merge(object);
	}

	@Override
	public void delete(T object) {
		em.remove(em.merge(object));	
	}

	@Override
	public void insert(T object) {
		em.persist(object);
	}

	@Override
	public boolean exists(int id) {
		T entity = em.find(type, id);
		return entity != null;
	}
	
}
