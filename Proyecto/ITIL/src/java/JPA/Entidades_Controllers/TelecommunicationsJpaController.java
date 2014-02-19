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
import JPA.Entidades.Telecommunications;
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
public class TelecommunicationsJpaController implements Serializable {

    public TelecommunicationsJpaController() {
   }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Telecommunications telecommunications) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItItem itItem = telecommunications.getItItem();
            if (itItem != null) {
                itItem = em.getReference(itItem.getClass(), itItem.getItItemPK());
                telecommunications.setItItem(itItem);
            }
            em.persist(telecommunications);
            if (itItem != null) {
                itItem.getTelecommunicationsCollection().add(telecommunications);
                itItem = em.merge(itItem);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTelecommunications(telecommunications.getIdTelecom()) != null) {
                throw new PreexistingEntityException("Telecommunications " + telecommunications + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telecommunications telecommunications) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telecommunications persistentTelecommunications = em.find(Telecommunications.class, telecommunications.getIdTelecom());
            ItItem itItemOld = persistentTelecommunications.getItItem();
            ItItem itItemNew = telecommunications.getItItem();
            if (itItemNew != null) {
                itItemNew = em.getReference(itItemNew.getClass(), itItemNew.getItItemPK());
                telecommunications.setItItem(itItemNew);
            }
            telecommunications = em.merge(telecommunications);
            if (itItemOld != null && !itItemOld.equals(itItemNew)) {
                itItemOld.getTelecommunicationsCollection().remove(telecommunications);
                itItemOld = em.merge(itItemOld);
            }
            if (itItemNew != null && !itItemNew.equals(itItemOld)) {
                itItemNew.getTelecommunicationsCollection().add(telecommunications);
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
                String id = telecommunications.getIdTelecom();
                if (findTelecommunications(id) == null) {
                    throw new NonexistentEntityException("The telecommunications with id " + id + " no longer exists.");
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
            Telecommunications telecommunications;
            try {
                telecommunications = em.getReference(Telecommunications.class, id);
                telecommunications.getIdTelecom();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telecommunications with id " + id + " no longer exists.", enfe);
            }
            ItItem itItem = telecommunications.getItItem();
            if (itItem != null) {
                itItem.getTelecommunicationsCollection().remove(telecommunications);
                itItem = em.merge(itItem);
            }
            em.remove(telecommunications);
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

    public List<Telecommunications> findTelecommunicationsEntities() {
        return findTelecommunicationsEntities(true, -1, -1);
    }

    public List<Telecommunications> findTelecommunicationsEntities(int maxResults, int firstResult) {
        return findTelecommunicationsEntities(false, maxResults, firstResult);
    }

    private List<Telecommunications> findTelecommunicationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telecommunications.class));
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

    public Telecommunications findTelecommunications(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telecommunications.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelecommunicationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telecommunications> rt = cq.from(Telecommunications.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
