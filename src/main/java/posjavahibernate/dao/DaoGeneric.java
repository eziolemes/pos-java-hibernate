package posjavahibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import posjavahibernate.HibernateUtil;

public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();
	
	public void salvar(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();
	}
	
	public E updateMerge(E entidade) { // salva ou atualiza
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E e = entityManager.merge(entidade);
		transaction.commit();
		
		return e;
	}
	
	public E pesquisar(E entity) { // Mais viável que do a do worcontrol, pois o de lá, precisa enviar a classe por parametro.
		
		Object id = HibernateUtil.getPrimaryKey(entity);
		
		E e = (E) entityManager.find(entity.getClass(), id);
		
		return e;
	}
	
	public List<E> listar(Class<E> entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		transaction.commit();
		
		return lista;
	}
	
	public void deletarPorId(E entity) {
		
		Object id = HibernateUtil.getPrimaryKey(entity);
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.createNativeQuery("delete from " + entity.getClass().getSimpleName().toLowerCase() + " where id = " + id).executeUpdate(); // exclui com segurança
		
		transaction.commit();
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}