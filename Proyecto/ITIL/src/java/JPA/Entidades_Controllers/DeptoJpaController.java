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
import JPA.Entidades.Area;
import JPA.Entidades.Depto;
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
public class DeptoJpaController implements Serializable {

    public DeptoJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Depto depto) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area areaareidArea = depto.getAreaareidArea();
            if (areaareidArea != null) {
                areaareidArea = em.getReference(areaareidArea.getClass(), areaareidArea.getAreidArea());
                depto.setAreaareidArea(areaareidArea);
            }
            em.persist(depto);
            if (areaareidArea != null) {
                areaareidArea.getDeptoCollection().add(depto);
                areaareidArea = em.merge(areaareidArea);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDepto(depto.getDepDepartamento()) != null) {
                throw new PreexistingEntityException("Depto " + depto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Depto depto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Depto persistentDepto = em.find(Depto.class, depto.getDepDepartamento());
            Area areaareidAreaOld = persistentDepto.getAreaareidArea();
            Area areaareidAreaNew = depto.getAreaareidArea();
            if (areaareidAreaNew != null) {
                areaareidAreaNew = em.getReference(areaareidAreaNew.getClass(), areaareidAreaNew.getAreidArea());
                depto.setAreaareidArea(areaareidAreaNew);
            }
            depto = em.merge(depto);
            if (areaareidAreaOld != null && !areaareidAreaOld.equals(areaareidAreaNew)) {
                areaareidAreaOld.getDeptoCollection().remove(depto);
                areaareidAreaOld = em.merge(areaareidAreaOld);
            }
            if (areaareidAreaNew != null && !areaareidAreaNew.equals(areaareidAreaOld)) {
                areaareidAreaNew.getDeptoCollection().add(depto);
                areaareidAreaNew = em.merge(areaareidAreaNew);
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
                String id = depto.getDepDepartamento();
                if (findDepto(id) == null) {
                    throw new NonexistentEntityException("The depto with id " + id + " no longer exists.");
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
            Depto depto;
            try {
                depto = em.getReference(Depto.class, id);
                depto.getDepDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The depto with id " + id + " no longer exists.", enfe);
            }
            Area areaareidArea = depto.getAreaareidArea();
            if (areaareidArea != null) {
                areaareidArea.getDeptoCollection().remove(depto);
                areaareidArea = em.merge(areaareidArea);
            }
            em.remove(depto);
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

    public List<Depto> findDeptoEntities() {
        return findDeptoEntities(true, -1, -1);
    }

    public List<Depto> findDeptoEntities(int maxResults, int firstResult) {
        return findDeptoEntities(false, maxResults, firstResult);
    }

    private List<Depto> findDeptoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Depto.class));
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

    public Depto findDepto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Depto.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeptoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Depto> rt = cq.from(Depto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
