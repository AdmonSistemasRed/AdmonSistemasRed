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
public class TelecommunicationsJpaController implements Serializable {

    public TelecommunicationsJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }
    public void create(Telecommunications telecommunications) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (telecommunications.getItItemCollection() == null) {
            telecommunications.setItItemCollection(new ArrayList<ItItem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ItItem> attachedItItemCollection = new ArrayList<ItItem>();
            for (ItItem itItemCollectionItItemToAttach : telecommunications.getItItemCollection()) {
                itItemCollectionItItemToAttach = em.getReference(itItemCollectionItItemToAttach.getClass(), itItemCollectionItItemToAttach.getItSerie());
                attachedItItemCollection.add(itItemCollectionItItemToAttach);
            }
            telecommunications.setItItemCollection(attachedItItemCollection);
            em.persist(telecommunications);
            for (ItItem itItemCollectionItItem : telecommunications.getItItemCollection()) {
                Telecommunications oldTelecommunicationsidTelecomOfItItemCollectionItItem = itItemCollectionItItem.getTelecommunicationsidTelecom();
                itItemCollectionItItem.setTelecommunicationsidTelecom(telecommunications);
                itItemCollectionItItem = em.merge(itItemCollectionItItem);
                if (oldTelecommunicationsidTelecomOfItItemCollectionItItem != null) {
                    oldTelecommunicationsidTelecomOfItItemCollectionItItem.getItItemCollection().remove(itItemCollectionItItem);
                    oldTelecommunicationsidTelecomOfItItemCollectionItItem = em.merge(oldTelecommunicationsidTelecomOfItItemCollectionItItem);
                }
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

    public void edit(Telecommunications telecommunications) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telecommunications persistentTelecommunications = em.find(Telecommunications.class, telecommunications.getIdTelecom());
            Collection<ItItem> itItemCollectionOld = persistentTelecommunications.getItItemCollection();
            Collection<ItItem> itItemCollectionNew = telecommunications.getItItemCollection();
            List<String> illegalOrphanMessages = null;
            for (ItItem itItemCollectionOldItItem : itItemCollectionOld) {
                if (!itItemCollectionNew.contains(itItemCollectionOldItItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItItem " + itItemCollectionOldItItem + " since its telecommunicationsidTelecom field is not nullable.");
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
            telecommunications.setItItemCollection(itItemCollectionNew);
            telecommunications = em.merge(telecommunications);
            for (ItItem itItemCollectionNewItItem : itItemCollectionNew) {
                if (!itItemCollectionOld.contains(itItemCollectionNewItItem)) {
                    Telecommunications oldTelecommunicationsidTelecomOfItItemCollectionNewItItem = itItemCollectionNewItItem.getTelecommunicationsidTelecom();
                    itItemCollectionNewItItem.setTelecommunicationsidTelecom(telecommunications);
                    itItemCollectionNewItItem = em.merge(itItemCollectionNewItItem);
                    if (oldTelecommunicationsidTelecomOfItItemCollectionNewItItem != null && !oldTelecommunicationsidTelecomOfItItemCollectionNewItItem.equals(telecommunications)) {
                        oldTelecommunicationsidTelecomOfItItemCollectionNewItItem.getItItemCollection().remove(itItemCollectionNewItItem);
                        oldTelecommunicationsidTelecomOfItItemCollectionNewItItem = em.merge(oldTelecommunicationsidTelecomOfItItemCollectionNewItItem);
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
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
            List<String> illegalOrphanMessages = null;
            Collection<ItItem> itItemCollectionOrphanCheck = telecommunications.getItItemCollection();
            for (ItItem itItemCollectionOrphanCheckItItem : itItemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Telecommunications (" + telecommunications + ") cannot be destroyed since the ItItem " + itItemCollectionOrphanCheckItItem + " in its itItemCollection field has a non-nullable telecommunicationsidTelecom field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
