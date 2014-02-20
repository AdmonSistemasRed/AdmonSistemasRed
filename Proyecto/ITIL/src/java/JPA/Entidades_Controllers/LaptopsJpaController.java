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
import JPA.Entidades.Laptops;
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
public class LaptopsJpaController implements Serializable {

    public LaptopsJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }

    public void create(Laptops laptops) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (laptops.getComputadoraCollection() == null) {
            laptops.setComputadoraCollection(new ArrayList<Computadora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Computadora> attachedComputadoraCollection = new ArrayList<Computadora>();
            for (Computadora computadoraCollectionComputadoraToAttach : laptops.getComputadoraCollection()) {
                computadoraCollectionComputadoraToAttach = em.getReference(computadoraCollectionComputadoraToAttach.getClass(), computadoraCollectionComputadoraToAttach.getIdComputadora());
                attachedComputadoraCollection.add(computadoraCollectionComputadoraToAttach);
            }
            laptops.setComputadoraCollection(attachedComputadoraCollection);
            em.persist(laptops);
            for (Computadora computadoraCollectionComputadora : laptops.getComputadoraCollection()) {
                Laptops oldLaptopsidLaptopOfComputadoraCollectionComputadora = computadoraCollectionComputadora.getLaptopsidLaptop();
                computadoraCollectionComputadora.setLaptopsidLaptop(laptops);
                computadoraCollectionComputadora = em.merge(computadoraCollectionComputadora);
                if (oldLaptopsidLaptopOfComputadoraCollectionComputadora != null) {
                    oldLaptopsidLaptopOfComputadoraCollectionComputadora.getComputadoraCollection().remove(computadoraCollectionComputadora);
                    oldLaptopsidLaptopOfComputadoraCollectionComputadora = em.merge(oldLaptopsidLaptopOfComputadoraCollectionComputadora);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLaptops(laptops.getIdLaptop()) != null) {
                throw new PreexistingEntityException("Laptops " + laptops + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Laptops laptops) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Laptops persistentLaptops = em.find(Laptops.class, laptops.getIdLaptop());
            Collection<Computadora> computadoraCollectionOld = persistentLaptops.getComputadoraCollection();
            Collection<Computadora> computadoraCollectionNew = laptops.getComputadoraCollection();
            List<String> illegalOrphanMessages = null;
            for (Computadora computadoraCollectionOldComputadora : computadoraCollectionOld) {
                if (!computadoraCollectionNew.contains(computadoraCollectionOldComputadora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Computadora " + computadoraCollectionOldComputadora + " since its laptopsidLaptop field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Computadora> attachedComputadoraCollectionNew = new ArrayList<Computadora>();
            for (Computadora computadoraCollectionNewComputadoraToAttach : computadoraCollectionNew) {
                computadoraCollectionNewComputadoraToAttach = em.getReference(computadoraCollectionNewComputadoraToAttach.getClass(), computadoraCollectionNewComputadoraToAttach.getIdComputadora());
                attachedComputadoraCollectionNew.add(computadoraCollectionNewComputadoraToAttach);
            }
            computadoraCollectionNew = attachedComputadoraCollectionNew;
            laptops.setComputadoraCollection(computadoraCollectionNew);
            laptops = em.merge(laptops);
            for (Computadora computadoraCollectionNewComputadora : computadoraCollectionNew) {
                if (!computadoraCollectionOld.contains(computadoraCollectionNewComputadora)) {
                    Laptops oldLaptopsidLaptopOfComputadoraCollectionNewComputadora = computadoraCollectionNewComputadora.getLaptopsidLaptop();
                    computadoraCollectionNewComputadora.setLaptopsidLaptop(laptops);
                    computadoraCollectionNewComputadora = em.merge(computadoraCollectionNewComputadora);
                    if (oldLaptopsidLaptopOfComputadoraCollectionNewComputadora != null && !oldLaptopsidLaptopOfComputadoraCollectionNewComputadora.equals(laptops)) {
                        oldLaptopsidLaptopOfComputadoraCollectionNewComputadora.getComputadoraCollection().remove(computadoraCollectionNewComputadora);
                        oldLaptopsidLaptopOfComputadoraCollectionNewComputadora = em.merge(oldLaptopsidLaptopOfComputadoraCollectionNewComputadora);
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
                String id = laptops.getIdLaptop();
                if (findLaptops(id) == null) {
                    throw new NonexistentEntityException("The laptops with id " + id + " no longer exists.");
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
            Laptops laptops;
            try {
                laptops = em.getReference(Laptops.class, id);
                laptops.getIdLaptop();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The laptops with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Computadora> computadoraCollectionOrphanCheck = laptops.getComputadoraCollection();
            for (Computadora computadoraCollectionOrphanCheckComputadora : computadoraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Laptops (" + laptops + ") cannot be destroyed since the Computadora " + computadoraCollectionOrphanCheckComputadora + " in its computadoraCollection field has a non-nullable laptopsidLaptop field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(laptops);
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

    public List<Laptops> findLaptopsEntities() {
        return findLaptopsEntities(true, -1, -1);
    }

    public List<Laptops> findLaptopsEntities(int maxResults, int firstResult) {
        return findLaptopsEntities(false, maxResults, firstResult);
    }

    private List<Laptops> findLaptopsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Laptops.class));
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

    public Laptops findLaptops(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Laptops.class, id);
        } finally {
            em.close();
        }
    }

    public int getLaptopsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Laptops> rt = cq.from(Laptops.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
