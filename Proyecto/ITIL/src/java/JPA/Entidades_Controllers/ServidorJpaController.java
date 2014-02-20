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
import JPA.Entidades.Servidor;
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
public class ServidorJpaController implements Serializable {

    public ServidorJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }

    public void create(Servidor servidor) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (servidor.getComputadoraCollection() == null) {
            servidor.setComputadoraCollection(new ArrayList<Computadora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Computadora> attachedComputadoraCollection = new ArrayList<Computadora>();
            for (Computadora computadoraCollectionComputadoraToAttach : servidor.getComputadoraCollection()) {
                computadoraCollectionComputadoraToAttach = em.getReference(computadoraCollectionComputadoraToAttach.getClass(), computadoraCollectionComputadoraToAttach.getIdComputadora());
                attachedComputadoraCollection.add(computadoraCollectionComputadoraToAttach);
            }
            servidor.setComputadoraCollection(attachedComputadoraCollection);
            em.persist(servidor);
            for (Computadora computadoraCollectionComputadora : servidor.getComputadoraCollection()) {
                Servidor oldServidoridServidorOfComputadoraCollectionComputadora = computadoraCollectionComputadora.getServidoridServidor();
                computadoraCollectionComputadora.setServidoridServidor(servidor);
                computadoraCollectionComputadora = em.merge(computadoraCollectionComputadora);
                if (oldServidoridServidorOfComputadoraCollectionComputadora != null) {
                    oldServidoridServidorOfComputadoraCollectionComputadora.getComputadoraCollection().remove(computadoraCollectionComputadora);
                    oldServidoridServidorOfComputadoraCollectionComputadora = em.merge(oldServidoridServidorOfComputadoraCollectionComputadora);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findServidor(servidor.getIdServidor()) != null) {
                throw new PreexistingEntityException("Servidor " + servidor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servidor servidor) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servidor persistentServidor = em.find(Servidor.class, servidor.getIdServidor());
            Collection<Computadora> computadoraCollectionOld = persistentServidor.getComputadoraCollection();
            Collection<Computadora> computadoraCollectionNew = servidor.getComputadoraCollection();
            List<String> illegalOrphanMessages = null;
            for (Computadora computadoraCollectionOldComputadora : computadoraCollectionOld) {
                if (!computadoraCollectionNew.contains(computadoraCollectionOldComputadora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Computadora " + computadoraCollectionOldComputadora + " since its servidoridServidor field is not nullable.");
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
            servidor.setComputadoraCollection(computadoraCollectionNew);
            servidor = em.merge(servidor);
            for (Computadora computadoraCollectionNewComputadora : computadoraCollectionNew) {
                if (!computadoraCollectionOld.contains(computadoraCollectionNewComputadora)) {
                    Servidor oldServidoridServidorOfComputadoraCollectionNewComputadora = computadoraCollectionNewComputadora.getServidoridServidor();
                    computadoraCollectionNewComputadora.setServidoridServidor(servidor);
                    computadoraCollectionNewComputadora = em.merge(computadoraCollectionNewComputadora);
                    if (oldServidoridServidorOfComputadoraCollectionNewComputadora != null && !oldServidoridServidorOfComputadoraCollectionNewComputadora.equals(servidor)) {
                        oldServidoridServidorOfComputadoraCollectionNewComputadora.getComputadoraCollection().remove(computadoraCollectionNewComputadora);
                        oldServidoridServidorOfComputadoraCollectionNewComputadora = em.merge(oldServidoridServidorOfComputadoraCollectionNewComputadora);
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
                String id = servidor.getIdServidor();
                if (findServidor(id) == null) {
                    throw new NonexistentEntityException("The servidor with id " + id + " no longer exists.");
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
            Servidor servidor;
            try {
                servidor = em.getReference(Servidor.class, id);
                servidor.getIdServidor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servidor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Computadora> computadoraCollectionOrphanCheck = servidor.getComputadoraCollection();
            for (Computadora computadoraCollectionOrphanCheckComputadora : computadoraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servidor (" + servidor + ") cannot be destroyed since the Computadora " + computadoraCollectionOrphanCheckComputadora + " in its computadoraCollection field has a non-nullable servidoridServidor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(servidor);
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

    public List<Servidor> findServidorEntities() {
        return findServidorEntities(true, -1, -1);
    }

    public List<Servidor> findServidorEntities(int maxResults, int firstResult) {
        return findServidorEntities(false, maxResults, firstResult);
    }

    private List<Servidor> findServidorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servidor.class));
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

    public Servidor findServidor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servidor.class, id);
        } finally {
            em.close();
        }
    }

    public int getServidorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servidor> rt = cq.from(Servidor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
