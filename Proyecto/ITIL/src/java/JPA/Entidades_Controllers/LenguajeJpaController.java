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
import JPA.Entidades.Lenguaje;
import JPA.Entidades_Controllers.exceptions.NonexistentEntityException;
import JPA.Entidades_Controllers.exceptions.PreexistingEntityException;
import JPA.Entidades_Controllers.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author madman
 */
public class LenguajeJpaController implements Serializable {

    public LenguajeJpaController() {
 }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Lenguaje lenguaje) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (lenguaje.getEmpleadoCollection() == null) {
            lenguaje.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
          em = getEntityManager();
            em.getTransaction().begin();
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : lenguaje.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getEmpNoEmpleado());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            lenguaje.setEmpleadoCollection(attachedEmpleadoCollection);
            em.persist(lenguaje);
            for (Empleado empleadoCollectionEmpleado : lenguaje.getEmpleadoCollection()) {
                empleadoCollectionEmpleado.getLenguajeCollection().add(lenguaje);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLenguaje(lenguaje.getLenLenguaje()) != null) {
                throw new PreexistingEntityException("Lenguaje " + lenguaje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lenguaje lenguaje) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
          em = getEntityManager();
            em.getTransaction().begin();
            Lenguaje persistentLenguaje = em.find(Lenguaje.class, lenguaje.getLenLenguaje());
            Collection<Empleado> empleadoCollectionOld = persistentLenguaje.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = lenguaje.getEmpleadoCollection();
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getEmpNoEmpleado());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            lenguaje.setEmpleadoCollection(empleadoCollectionNew);
            lenguaje = em.merge(lenguaje);
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    empleadoCollectionOldEmpleado.getLenguajeCollection().remove(lenguaje);
                    empleadoCollectionOldEmpleado = em.merge(empleadoCollectionOldEmpleado);
                }
            }
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    empleadoCollectionNewEmpleado.getLenguajeCollection().add(lenguaje);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                }
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
                String id = lenguaje.getLenLenguaje();
                if (findLenguaje(id) == null) {
                    throw new NonexistentEntityException("The lenguaje with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
          em = getEntityManager();
            em.getTransaction().begin();
            Lenguaje lenguaje;
            try {
                lenguaje = em.getReference(Lenguaje.class, id);
                lenguaje.getLenLenguaje();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lenguaje with id " + id + " no longer exists.", enfe);
            }
            Collection<Empleado> empleadoCollection = lenguaje.getEmpleadoCollection();
            for (Empleado empleadoCollectionEmpleado : empleadoCollection) {
                empleadoCollectionEmpleado.getLenguajeCollection().remove(lenguaje);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
            }
            em.remove(lenguaje);
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

    public List<Lenguaje> findLenguajeEntities() {
        return findLenguajeEntities(true, -1, -1);
    }

    public List<Lenguaje> findLenguajeEntities(int maxResults, int firstResult) {
        return findLenguajeEntities(false, maxResults, firstResult);
    }

    private List<Lenguaje> findLenguajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lenguaje.class));
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

    public Lenguaje findLenguaje(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lenguaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getLenguajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lenguaje> rt = cq.from(Lenguaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
