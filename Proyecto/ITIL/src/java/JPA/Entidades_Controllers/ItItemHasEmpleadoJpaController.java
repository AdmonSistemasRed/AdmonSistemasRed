/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades_Controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Entidades.Empleado;
import JPA.Entidades.ItItem;
import JPA.Entidades.ItItemHasEmpleado;
import JPA.Entidades.ItItemHasEmpleadoPK;
import JPA.Entidades_Controllers.exceptions.NonexistentEntityException;
import JPA.Entidades_Controllers.exceptions.PreexistingEntityException;
import JPA.Entidades_Controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author madman
 */
public class ItItemHasEmpleadoJpaController implements Serializable {

    public ItItemHasEmpleadoJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }
    public void create(ItItemHasEmpleado itItemHasEmpleado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (itItemHasEmpleado.getItItemHasEmpleadoPK() == null) {
            itItemHasEmpleado.setItItemHasEmpleadoPK(new ItItemHasEmpleadoPK());
        }
        itItemHasEmpleado.getItItemHasEmpleadoPK().setITitemitserie(itItemHasEmpleado.getItItem().getItSerie());
        itItemHasEmpleado.getItItemHasEmpleadoPK().setEmpleadoempNoEmpleado(itItemHasEmpleado.getEmpleado().getEmpNoEmpleado());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = itItemHasEmpleado.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getEmpNoEmpleado());
                itItemHasEmpleado.setEmpleado(empleado);
            }
            ItItem itItem = itItemHasEmpleado.getItItem();
            if (itItem != null) {
                itItem = em.getReference(itItem.getClass(), itItem.getItSerie());
                itItemHasEmpleado.setItItem(itItem);
            }
            em.persist(itItemHasEmpleado);
            if (empleado != null) {
                empleado.getItItemHasEmpleadoCollection().add(itItemHasEmpleado);
                empleado = em.merge(empleado);
            }
            if (itItem != null) {
                itItem.getItItemHasEmpleadoCollection().add(itItemHasEmpleado);
                itItem = em.merge(itItem);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findItItemHasEmpleado(itItemHasEmpleado.getItItemHasEmpleadoPK()) != null) {
                throw new PreexistingEntityException("ItItemHasEmpleado " + itItemHasEmpleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItItemHasEmpleado itItemHasEmpleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        itItemHasEmpleado.getItItemHasEmpleadoPK().setITitemitserie(itItemHasEmpleado.getItItem().getItSerie());
        itItemHasEmpleado.getItItemHasEmpleadoPK().setEmpleadoempNoEmpleado(itItemHasEmpleado.getEmpleado().getEmpNoEmpleado());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItItemHasEmpleado persistentItItemHasEmpleado = em.find(ItItemHasEmpleado.class, itItemHasEmpleado.getItItemHasEmpleadoPK());
            Empleado empleadoOld = persistentItItemHasEmpleado.getEmpleado();
            Empleado empleadoNew = itItemHasEmpleado.getEmpleado();
            ItItem itItemOld = persistentItItemHasEmpleado.getItItem();
            ItItem itItemNew = itItemHasEmpleado.getItItem();
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getEmpNoEmpleado());
                itItemHasEmpleado.setEmpleado(empleadoNew);
            }
            if (itItemNew != null) {
                itItemNew = em.getReference(itItemNew.getClass(), itItemNew.getItSerie());
                itItemHasEmpleado.setItItem(itItemNew);
            }
            itItemHasEmpleado = em.merge(itItemHasEmpleado);
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getItItemHasEmpleadoCollection().remove(itItemHasEmpleado);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getItItemHasEmpleadoCollection().add(itItemHasEmpleado);
                empleadoNew = em.merge(empleadoNew);
            }
            if (itItemOld != null && !itItemOld.equals(itItemNew)) {
                itItemOld.getItItemHasEmpleadoCollection().remove(itItemHasEmpleado);
                itItemOld = em.merge(itItemOld);
            }
            if (itItemNew != null && !itItemNew.equals(itItemOld)) {
                itItemNew.getItItemHasEmpleadoCollection().add(itItemHasEmpleado);
                itItemNew = em.merge(itItemNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ItItemHasEmpleadoPK id = itItemHasEmpleado.getItItemHasEmpleadoPK();
                if (findItItemHasEmpleado(id) == null) {
                    throw new NonexistentEntityException("The itItemHasEmpleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ItItemHasEmpleadoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItItemHasEmpleado itItemHasEmpleado;
            try {
                itItemHasEmpleado = em.getReference(ItItemHasEmpleado.class, id);
                itItemHasEmpleado.getItItemHasEmpleadoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itItemHasEmpleado with id " + id + " no longer exists.", enfe);
            }
            Empleado empleado = itItemHasEmpleado.getEmpleado();
            if (empleado != null) {
                empleado.getItItemHasEmpleadoCollection().remove(itItemHasEmpleado);
                empleado = em.merge(empleado);
            }
            ItItem itItem = itItemHasEmpleado.getItItem();
            if (itItem != null) {
                itItem.getItItemHasEmpleadoCollection().remove(itItemHasEmpleado);
                itItem = em.merge(itItem);
            }
            em.remove(itItemHasEmpleado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItItemHasEmpleado> findItItemHasEmpleadoEntities() {
        return findItItemHasEmpleadoEntities(true, -1, -1);
    }

    public List<ItItemHasEmpleado> findItItemHasEmpleadoEntities(int maxResults, int firstResult) {
        return findItItemHasEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<ItItemHasEmpleado> findItItemHasEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItItemHasEmpleado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ItItemHasEmpleado findItItemHasEmpleado(ItItemHasEmpleadoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItItemHasEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getItItemHasEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItItemHasEmpleado> rt = cq.from(ItItemHasEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
