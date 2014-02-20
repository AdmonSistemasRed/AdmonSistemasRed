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
import JPA.Entidades.Workstation;
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
public class WorkstationJpaController implements Serializable {

    public WorkstationJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }

    public void create(Workstation workstation) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (workstation.getComputadoraCollection() == null) {
            workstation.setComputadoraCollection(new ArrayList<Computadora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Computadora> attachedComputadoraCollection = new ArrayList<Computadora>();
            for (Computadora computadoraCollectionComputadoraToAttach : workstation.getComputadoraCollection()) {
                computadoraCollectionComputadoraToAttach = em.getReference(computadoraCollectionComputadoraToAttach.getClass(), computadoraCollectionComputadoraToAttach.getIdComputadora());
                attachedComputadoraCollection.add(computadoraCollectionComputadoraToAttach);
            }
            workstation.setComputadoraCollection(attachedComputadoraCollection);
            em.persist(workstation);
            for (Computadora computadoraCollectionComputadora : workstation.getComputadoraCollection()) {
                Workstation oldWorkstationidWorkstationOfComputadoraCollectionComputadora = computadoraCollectionComputadora.getWorkstationidWorkstation();
                computadoraCollectionComputadora.setWorkstationidWorkstation(workstation);
                computadoraCollectionComputadora = em.merge(computadoraCollectionComputadora);
                if (oldWorkstationidWorkstationOfComputadoraCollectionComputadora != null) {
                    oldWorkstationidWorkstationOfComputadoraCollectionComputadora.getComputadoraCollection().remove(computadoraCollectionComputadora);
                    oldWorkstationidWorkstationOfComputadoraCollectionComputadora = em.merge(oldWorkstationidWorkstationOfComputadoraCollectionComputadora);
                }
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

    public void edit(Workstation workstation) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workstation persistentWorkstation = em.find(Workstation.class, workstation.getIdWorkstation());
            Collection<Computadora> computadoraCollectionOld = persistentWorkstation.getComputadoraCollection();
            Collection<Computadora> computadoraCollectionNew = workstation.getComputadoraCollection();
            List<String> illegalOrphanMessages = null;
            for (Computadora computadoraCollectionOldComputadora : computadoraCollectionOld) {
                if (!computadoraCollectionNew.contains(computadoraCollectionOldComputadora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Computadora " + computadoraCollectionOldComputadora + " since its workstationidWorkstation field is not nullable.");
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
            workstation.setComputadoraCollection(computadoraCollectionNew);
            workstation = em.merge(workstation);
            for (Computadora computadoraCollectionNewComputadora : computadoraCollectionNew) {
                if (!computadoraCollectionOld.contains(computadoraCollectionNewComputadora)) {
                    Workstation oldWorkstationidWorkstationOfComputadoraCollectionNewComputadora = computadoraCollectionNewComputadora.getWorkstationidWorkstation();
                    computadoraCollectionNewComputadora.setWorkstationidWorkstation(workstation);
                    computadoraCollectionNewComputadora = em.merge(computadoraCollectionNewComputadora);
                    if (oldWorkstationidWorkstationOfComputadoraCollectionNewComputadora != null && !oldWorkstationidWorkstationOfComputadoraCollectionNewComputadora.equals(workstation)) {
                        oldWorkstationidWorkstationOfComputadoraCollectionNewComputadora.getComputadoraCollection().remove(computadoraCollectionNewComputadora);
                        oldWorkstationidWorkstationOfComputadoraCollectionNewComputadora = em.merge(oldWorkstationidWorkstationOfComputadoraCollectionNewComputadora);
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
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
            List<String> illegalOrphanMessages = null;
            Collection<Computadora> computadoraCollectionOrphanCheck = workstation.getComputadoraCollection();
            for (Computadora computadoraCollectionOrphanCheckComputadora : computadoraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Workstation (" + workstation + ") cannot be destroyed since the Computadora " + computadoraCollectionOrphanCheckComputadora + " in its computadoraCollection field has a non-nullable workstationidWorkstation field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
