package repository;

import java.util.List;

public interface GenericDao<T> {

	public List<T> findAll();
	public T get(int id);
	public T update(T object);
	public void delete(T object);
	public void insert(T object);
	public boolean exists(int id);
	
}
