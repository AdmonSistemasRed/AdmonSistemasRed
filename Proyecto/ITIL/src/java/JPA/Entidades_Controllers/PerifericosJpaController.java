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
import JPA.Entidades.Perifericos;
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
public class PerifericosJpaController implements Serializable {

    public PerifericosJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }
    public void create(Perifericos perifericos) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItItem itItem = perifericos.getItItem();
            if (itItem != null) {
                itItem = em.getReference(itItem.getClass(), itItem.getItItemPK());
                perifericos.setItItem(itItem);
            }
            em.persist(perifericos);
            if (itItem != null) {
                itItem.getPerifericosCollection().add(perifericos);
                itItem = em.merge(itItem);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPerifericos(perifericos.getIdPeriferico()) != null) {
                throw new PreexistingEntityException("Perifericos " + perifericos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Perifericos perifericos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perifericos persistentPerifericos = em.find(Perifericos.class, perifericos.getIdPeriferico());
            ItItem itItemOld = persistentPerifericos.getItItem();
            ItItem itItemNew = perifericos.getItItem();
            if (itItemNew != null) {
                itItemNew = em.getReference(itItemNew.getClass(), itItemNew.getItItemPK());
                perifericos.setItItem(itItemNew);
            }
            perifericos = em.merge(perifericos);
            if (itItemOld != null && !itItemOld.equals(itItemNew)) {
                itItemOld.getPerifericosCollection().remove(perifericos);
                itItemOld = em.merge(itItemOld);
            }
            if (itItemNew != null && !itItemNew.equals(itItemOld)) {
                itItemNew.getPerifericosCollection().add(perifericos);
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
                String id = perifericos.getIdPeriferico();
                if (findPerifericos(id) == null) {
                    throw new NonexistentEntityException("The perifericos with id " + id + " no longer exists.");
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
            Perifericos perifericos;
            try {
                perifericos = em.getReference(Perifericos.class, id);
                perifericos.getIdPeriferico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perifericos with id " + id + " no longer exists.", enfe);
            }
            ItItem itItem = perifericos.getItItem();
            if (itItem != null) {
                itItem.getPerifericosCollection().remove(perifericos);
                itItem = em.merge(itItem);
            }
            em.remove(perifericos);
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

    public List<Perifericos> findPerifericosEntities() {
        return findPerifericosEntities(true, -1, -1);
    }

    public List<Perifericos> findPerifericosEntities(int maxResults, int firstResult) {
        return findPerifericosEntities(false, maxResults, firstResult);
    }

    private List<Perifericos> findPerifericosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Perifericos.class));
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

    public Perifericos findPerifericos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Perifericos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerifericosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Perifericos> rt = cq.from(Perifericos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
