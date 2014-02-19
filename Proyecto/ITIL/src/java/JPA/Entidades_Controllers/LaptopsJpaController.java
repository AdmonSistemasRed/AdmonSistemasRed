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
import JPA.Entidades.Laptops;
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
public class LaptopsJpaController implements Serializable {

    public LaptopsJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Laptops laptops) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItItem itItem = laptops.getItItem();
            if (itItem != null) {
                itItem = em.getReference(itItem.getClass(), itItem.getItItemPK());
                laptops.setItItem(itItem);
            }
            em.persist(laptops);
            if (itItem != null) {
                itItem.getLaptopsCollection().add(laptops);
                itItem = em.merge(itItem);
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

    public void edit(Laptops laptops) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Laptops persistentLaptops = em.find(Laptops.class, laptops.getIdLaptop());
            ItItem itItemOld = persistentLaptops.getItItem();
            ItItem itItemNew = laptops.getItItem();
            if (itItemNew != null) {
                itItemNew = em.getReference(itItemNew.getClass(), itItemNew.getItItemPK());
                laptops.setItItem(itItemNew);
            }
            laptops = em.merge(laptops);
            if (itItemOld != null && !itItemOld.equals(itItemNew)) {
                itItemOld.getLaptopsCollection().remove(laptops);
                itItemOld = em.merge(itItemOld);
            }
            if (itItemNew != null && !itItemNew.equals(itItemOld)) {
                itItemNew.getLaptopsCollection().add(laptops);
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

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
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
            ItItem itItem = laptops.getItItem();
            if (itItem != null) {
                itItem.getLaptopsCollection().remove(laptops);
                itItem = em.merge(itItem);
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
