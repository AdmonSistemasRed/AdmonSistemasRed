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
import JPA.Entidades.ItItem;
import JPA.Entidades.Workstation;
import JPA.Entidades_Controllers.exceptions.NonexistentEntityException;
import JPA.Entidades_Controllers.exceptions.PreexistingEntityException;
import JPA.Entidades_Controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author madman
 */
public class WorkstationJpaController implements Serializable {

    public WorkstationJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Workstation workstation) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItItem itItem = workstation.getItItem();
            if (itItem != null) {
                itItem = em.getReference(itItem.getClass(), itItem.getItItemPK());
                workstation.setItItem(itItem);
            }
            em.persist(workstation);
            if (itItem != null) {
                itItem.getWorkstationCollection().add(workstation);
                itItem = em.merge(itItem);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findWorkstation(workstation.getIdWorkstation()) != null) {
                throw new PreexistingEntityException("Workstation " + workstation + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Workstation workstation) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workstation persistentWorkstation = em.find(Workstation.class, workstation.getIdWorkstation());
            ItItem itItemOld = persistentWorkstation.getItItem();
            ItItem itItemNew = workstation.getItItem();
            if (itItemNew != null) {
                itItemNew = em.getReference(itItemNew.getClass(), itItemNew.getItItemPK());
                workstation.setItItem(itItemNew);
            }
            workstation = em.merge(workstation);
            if (itItemOld != null && !itItemOld.equals(itItemNew)) {
                itItemOld.getWorkstationCollection().remove(workstation);
                itItemOld = em.merge(itItemOld);
            }
            if (itItemNew != null && !itItemNew.equals(itItemOld)) {
                itItemNew.getWorkstationCollection().add(workstation);
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
                String id = workstation.getIdWorkstation();
                if (findWorkstation(id) == null) {
                    throw new NonexistentEntityException("The workstation with id " + id + " no longer exists.");
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
            Workstation workstation;
            try {
                workstation = em.getReference(Workstation.class, id);
                workstation.getIdWorkstation();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workstation with id " + id + " no longer exists.", enfe);
            }
            ItItem itItem = workstation.getItItem();
            if (itItem != null) {
                itItem.getWorkstationCollection().remove(workstation);
                itItem = em.merge(itItem);
            }
            em.remove(workstation);
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

    public List<Workstation> findWorkstationEntities() {
        return findWorkstationEntities(true, -1, -1);
    }

    public List<Workstation> findWorkstationEntities(int maxResults, int firstResult) {
        return findWorkstationEntities(false, maxResults, firstResult);
    }

    private List<Workstation> findWorkstationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Workstation.class));
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

    public Workstation findWorkstation(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Workstation.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkstationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Workstation> rt = cq.from(Workstation.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
