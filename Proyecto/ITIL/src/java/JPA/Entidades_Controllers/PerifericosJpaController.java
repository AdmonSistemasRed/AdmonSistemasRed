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
import JPA.Entidades_Controllers.exceptions.IllegalOrphanException;
import JPA.Entidades_Controllers.exceptions.NonexistentEntityException;
import JPA.Entidades_Controllers.exceptions.PreexistingEntityException;
import JPA.Entidades_Controllers.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author madman
 */
public class PerifericosJpaController implements Serializable {

    public PerifericosJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }

    public void create(Perifericos perifericos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (perifericos.getItItemCollection() == null) {
            perifericos.setItItemCollection(new ArrayList<ItItem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ItItem> attachedItItemCollection = new ArrayList<ItItem>();
            for (ItItem itItemCollectionItItemToAttach : perifericos.getItItemCollection()) {
                itItemCollectionItItemToAttach = em.getReference(itItemCollectionItItemToAttach.getClass(), itItemCollectionItItemToAttach.getItSerie());
                attachedItItemCollection.add(itItemCollectionItItemToAttach);
            }
            perifericos.setItItemCollection(attachedItItemCollection);
            em.persist(perifericos);
            for (ItItem itItemCollectionItItem : perifericos.getItItemCollection()) {
                Perifericos oldPerifericosidPerifericoOfItItemCollectionItItem = itItemCollectionItItem.getPerifericosidPeriferico();
                itItemCollectionItItem.setPerifericosidPeriferico(perifericos);
                itItemCollectionItItem = em.merge(itItemCollectionItItem);
                if (oldPerifericosidPerifericoOfItItemCollectionItItem != null) {
                    oldPerifericosidPerifericoOfItItemCollectionItItem.getItItemCollection().remove(itItemCollectionItItem);
                    oldPerifericosidPerifericoOfItItemCollectionItItem = em.merge(oldPerifericosidPerifericoOfItItemCollectionItItem);
                }
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

    public void edit(Perifericos perifericos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Perifericos persistentPerifericos = em.find(Perifericos.class, perifericos.getIdPeriferico());
            Collection<ItItem> itItemCollectionOld = persistentPerifericos.getItItemCollection();
            Collection<ItItem> itItemCollectionNew = perifericos.getItItemCollection();
            List<String> illegalOrphanMessages = null;
            for (ItItem itItemCollectionOldItItem : itItemCollectionOld) {
                if (!itItemCollectionNew.contains(itItemCollectionOldItItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItItem " + itItemCollectionOldItItem + " since its perifericosidPeriferico field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ItItem> attachedItItemCollectionNew = new ArrayList<ItItem>();
            for (ItItem itItemCollectionNewItItemToAttach : itItemCollectionNew) {
                itItemCollectionNewItItemToAttach = em.getReference(itItemCollectionNewItItemToAttach.getClass(), itItemCollectionNewItItemToAttach.getItSerie());
                attachedItItemCollectionNew.add(itItemCollectionNewItItemToAttach);
            }
            itItemCollectionNew = attachedItItemCollectionNew;
            perifericos.setItItemCollection(itItemCollectionNew);
            perifericos = em.merge(perifericos);
            for (ItItem itItemCollectionNewItItem : itItemCollectionNew) {
                if (!itItemCollectionOld.contains(itItemCollectionNewItItem)) {
                    Perifericos oldPerifericosidPerifericoOfItItemCollectionNewItItem = itItemCollectionNewItItem.getPerifericosidPeriferico();
                    itItemCollectionNewItItem.setPerifericosidPeriferico(perifericos);
                    itItemCollectionNewItItem = em.merge(itItemCollectionNewItItem);
                    if (oldPerifericosidPerifericoOfItItemCollectionNewItItem != null && !oldPerifericosidPerifericoOfItItemCollectionNewItItem.equals(perifericos)) {
                        oldPerifericosidPerifericoOfItItemCollectionNewItItem.getItItemCollection().remove(itItemCollectionNewItItem);
                        oldPerifericosidPerifericoOfItItemCollectionNewItItem = em.merge(oldPerifericosidPerifericoOfItItemCollectionNewItItem);
                    }
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
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
            List<String> illegalOrphanMessages = null;
            Collection<ItItem> itItemCollectionOrphanCheck = perifericos.getItItemCollection();
            for (ItItem itItemCollectionOrphanCheckItItem : itItemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Perifericos (" + perifericos + ") cannot be destroyed since the ItItem " + itItemCollectionOrphanCheckItItem + " in its itItemCollection field has a non-nullable perifericosidPeriferico field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
