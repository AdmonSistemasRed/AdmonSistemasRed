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
import JPA.Entidades.Computadora;
import JPA.Entidades.Software;
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
public class SoftwareJpaController implements Serializable {

    public SoftwareJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }
    public void create(Software software) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (software.getComputadoraCollection() == null) {
            software.setComputadoraCollection(new ArrayList<Computadora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Computadora> attachedComputadoraCollection = new ArrayList<Computadora>();
            for (Computadora computadoraCollectionComputadoraToAttach : software.getComputadoraCollection()) {
                computadoraCollectionComputadoraToAttach = em.getReference(computadoraCollectionComputadoraToAttach.getClass(), computadoraCollectionComputadoraToAttach.getIdComputadora());
                attachedComputadoraCollection.add(computadoraCollectionComputadoraToAttach);
            }
            software.setComputadoraCollection(attachedComputadoraCollection);
            em.persist(software);
            for (Computadora computadoraCollectionComputadora : software.getComputadoraCollection()) {
                computadoraCollectionComputadora.getSoftwareCollection().add(software);
                computadoraCollectionComputadora = em.merge(computadoraCollectionComputadora);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSoftware(software.getIdSoftware()) != null) {
                throw new PreexistingEntityException("Software " + software + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Software software) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Software persistentSoftware = em.find(Software.class, software.getIdSoftware());
            Collection<Computadora> computadoraCollectionOld = persistentSoftware.getComputadoraCollection();
            Collection<Computadora> computadoraCollectionNew = software.getComputadoraCollection();
            Collection<Computadora> attachedComputadoraCollectionNew = new ArrayList<Computadora>();
            for (Computadora computadoraCollectionNewComputadoraToAttach : computadoraCollectionNew) {
                computadoraCollectionNewComputadoraToAttach = em.getReference(computadoraCollectionNewComputadoraToAttach.getClass(), computadoraCollectionNewComputadoraToAttach.getIdComputadora());
                attachedComputadoraCollectionNew.add(computadoraCollectionNewComputadoraToAttach);
            }
            computadoraCollectionNew = attachedComputadoraCollectionNew;
            software.setComputadoraCollection(computadoraCollectionNew);
            software = em.merge(software);
            for (Computadora computadoraCollectionOldComputadora : computadoraCollectionOld) {
                if (!computadoraCollectionNew.contains(computadoraCollectionOldComputadora)) {
                    computadoraCollectionOldComputadora.getSoftwareCollection().remove(software);
                    computadoraCollectionOldComputadora = em.merge(computadoraCollectionOldComputadora);
                }
            }
            for (Computadora computadoraCollectionNewComputadora : computadoraCollectionNew) {
                if (!computadoraCollectionOld.contains(computadoraCollectionNewComputadora)) {
                    computadoraCollectionNewComputadora.getSoftwareCollection().add(software);
                    computadoraCollectionNewComputadora = em.merge(computadoraCollectionNewComputadora);
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
                String id = software.getIdSoftware();
                if (findSoftware(id) == null) {
                    throw new NonexistentEntityException("The software with id " + id + " no longer exists.");
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
            Software software;
            try {
                software = em.getReference(Software.class, id);
                software.getIdSoftware();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The software with id " + id + " no longer exists.", enfe);
            }
            Collection<Computadora> computadoraCollection = software.getComputadoraCollection();
            for (Computadora computadoraCollectionComputadora : computadoraCollection) {
                computadoraCollectionComputadora.getSoftwareCollection().remove(software);
                computadoraCollectionComputadora = em.merge(computadoraCollectionComputadora);
            }
            em.remove(software);
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

    public List<Software> findSoftwareEntities() {
        return findSoftwareEntities(true, -1, -1);
    }

    public List<Software> findSoftwareEntities(int maxResults, int firstResult) {
        return findSoftwareEntities(false, maxResults, firstResult);
    }

    private List<Software> findSoftwareEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Software.class));
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

    public Software findSoftware(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Software.class, id);
        } finally {
            em.close();
        }
    }

    public int getSoftwareCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Software> rt = cq.from(Software.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
