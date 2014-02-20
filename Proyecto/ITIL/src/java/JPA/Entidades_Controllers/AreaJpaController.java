/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades_Controllers;

import JPA.Entidades.Area;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Entidades.Sucursal;
import JPA.Entidades.Depto;
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
public class AreaJpaController implements Serializable {

    public AreaJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }

    public void create(Area area) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (area.getDeptoCollection() == null) {
            area.setDeptoCollection(new ArrayList<Depto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursalSucursalidSucursal = area.getSucursalSucursalidSucursal();
            if (sucursalSucursalidSucursal != null) {
                sucursalSucursalidSucursal = em.getReference(sucursalSucursalidSucursal.getClass(), sucursalSucursalidSucursal.getSucursalidSucursal());
                area.setSucursalSucursalidSucursal(sucursalSucursalidSucursal);
            }
            Collection<Depto> attachedDeptoCollection = new ArrayList<Depto>();
            for (Depto deptoCollectionDeptoToAttach : area.getDeptoCollection()) {
                deptoCollectionDeptoToAttach = em.getReference(deptoCollectionDeptoToAttach.getClass(), deptoCollectionDeptoToAttach.getDepDepartamento());
                attachedDeptoCollection.add(deptoCollectionDeptoToAttach);
            }
            area.setDeptoCollection(attachedDeptoCollection);
            em.persist(area);
            if (sucursalSucursalidSucursal != null) {
                sucursalSucursalidSucursal.getAreaCollection().add(area);
                sucursalSucursalidSucursal = em.merge(sucursalSucursalidSucursal);
            }
            for (Depto deptoCollectionDepto : area.getDeptoCollection()) {
                Area oldAreaareidAreaOfDeptoCollectionDepto = deptoCollectionDepto.getAreaareidArea();
                deptoCollectionDepto.setAreaareidArea(area);
                deptoCollectionDepto = em.merge(deptoCollectionDepto);
                if (oldAreaareidAreaOfDeptoCollectionDepto != null) {
                    oldAreaareidAreaOfDeptoCollectionDepto.getDeptoCollection().remove(deptoCollectionDepto);
                    oldAreaareidAreaOfDeptoCollectionDepto = em.merge(oldAreaareidAreaOfDeptoCollectionDepto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findArea(area.getAreidArea()) != null) {
                throw new PreexistingEntityException("Area " + area + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Area area) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area persistentArea = em.find(Area.class, area.getAreidArea());
            Sucursal sucursalSucursalidSucursalOld = persistentArea.getSucursalSucursalidSucursal();
            Sucursal sucursalSucursalidSucursalNew = area.getSucursalSucursalidSucursal();
            Collection<Depto> deptoCollectionOld = persistentArea.getDeptoCollection();
            Collection<Depto> deptoCollectionNew = area.getDeptoCollection();
            List<String> illegalOrphanMessages = null;
            for (Depto deptoCollectionOldDepto : deptoCollectionOld) {
                if (!deptoCollectionNew.contains(deptoCollectionOldDepto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Depto " + deptoCollectionOldDepto + " since its areaareidArea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sucursalSucursalidSucursalNew != null) {
                sucursalSucursalidSucursalNew = em.getReference(sucursalSucursalidSucursalNew.getClass(), sucursalSucursalidSucursalNew.getSucursalidSucursal());
                area.setSucursalSucursalidSucursal(sucursalSucursalidSucursalNew);
            }
            Collection<Depto> attachedDeptoCollectionNew = new ArrayList<Depto>();
            for (Depto deptoCollectionNewDeptoToAttach : deptoCollectionNew) {
                deptoCollectionNewDeptoToAttach = em.getReference(deptoCollectionNewDeptoToAttach.getClass(), deptoCollectionNewDeptoToAttach.getDepDepartamento());
                attachedDeptoCollectionNew.add(deptoCollectionNewDeptoToAttach);
            }
            deptoCollectionNew = attachedDeptoCollectionNew;
            area.setDeptoCollection(deptoCollectionNew);
            area = em.merge(area);
            if (sucursalSucursalidSucursalOld != null && !sucursalSucursalidSucursalOld.equals(sucursalSucursalidSucursalNew)) {
                sucursalSucursalidSucursalOld.getAreaCollection().remove(area);
                sucursalSucursalidSucursalOld = em.merge(sucursalSucursalidSucursalOld);
            }
            if (sucursalSucursalidSucursalNew != null && !sucursalSucursalidSucursalNew.equals(sucursalSucursalidSucursalOld)) {
                sucursalSucursalidSucursalNew.getAreaCollection().add(area);
                sucursalSucursalidSucursalNew = em.merge(sucursalSucursalidSucursalNew);
            }
            for (Depto deptoCollectionNewDepto : deptoCollectionNew) {
                if (!deptoCollectionOld.contains(deptoCollectionNewDepto)) {
                    Area oldAreaareidAreaOfDeptoCollectionNewDepto = deptoCollectionNewDepto.getAreaareidArea();
                    deptoCollectionNewDepto.setAreaareidArea(area);
                    deptoCollectionNewDepto = em.merge(deptoCollectionNewDepto);
                    if (oldAreaareidAreaOfDeptoCollectionNewDepto != null && !oldAreaareidAreaOfDeptoCollectionNewDepto.equals(area)) {
                        oldAreaareidAreaOfDeptoCollectionNewDepto.getDeptoCollection().remove(deptoCollectionNewDepto);
                        oldAreaareidAreaOfDeptoCollectionNewDepto = em.merge(oldAreaareidAreaOfDeptoCollectionNewDepto);
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
                String id = area.getAreidArea();
                if (findArea(id) == null) {
                    throw new NonexistentEntityException("The area with id " + id + " no longer exists.");
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
            Area area;
            try {
                area = em.getReference(Area.class, id);
                area.getAreidArea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The area with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Depto> deptoCollectionOrphanCheck = area.getDeptoCollection();
            for (Depto deptoCollectionOrphanCheckDepto : deptoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the Depto " + deptoCollectionOrphanCheckDepto + " in its deptoCollection field has a non-nullable areaareidArea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sucursal sucursalSucursalidSucursal = area.getSucursalSucursalidSucursal();
            if (sucursalSucursalidSucursal != null) {
                sucursalSucursalidSucursal.getAreaCollection().remove(area);
                sucursalSucursalidSucursal = em.merge(sucursalSucursalidSucursal);
            }
            em.remove(area);
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

    public List<Area> findAreaEntities() {
        return findAreaEntities(true, -1, -1);
    }

    public List<Area> findAreaEntities(int maxResults, int firstResult) {
        return findAreaEntities(false, maxResults, firstResult);
    }

    private List<Area> findAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Area.class));
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

    public Area findArea(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Area.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Area> rt = cq.from(Area.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
