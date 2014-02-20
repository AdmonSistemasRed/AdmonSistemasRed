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
import JPA.Entidades.Empleado;
import java.util.ArrayList;
import java.util.Collection;
import JPA.Entidades.Area;
import JPA.Entidades.Sucursal;
import JPA.Entidades_Controllers.exceptions.IllegalOrphanException;
import JPA.Entidades_Controllers.exceptions.NonexistentEntityException;
import JPA.Entidades_Controllers.exceptions.PreexistingEntityException;
import JPA.Entidades_Controllers.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author madman
 */
public class SucursalJpaController implements Serializable {

    public SucursalJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }

    public void create(Sucursal sucursal) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (sucursal.getEmpleadoCollection() == null) {
            sucursal.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        if (sucursal.getAreaCollection() == null) {
            sucursal.setAreaCollection(new ArrayList<Area>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : sucursal.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getEmpNoEmpleado());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            sucursal.setEmpleadoCollection(attachedEmpleadoCollection);
            Collection<Area> attachedAreaCollection = new ArrayList<Area>();
            for (Area areaCollectionAreaToAttach : sucursal.getAreaCollection()) {
                areaCollectionAreaToAttach = em.getReference(areaCollectionAreaToAttach.getClass(), areaCollectionAreaToAttach.getAreidArea());
                attachedAreaCollection.add(areaCollectionAreaToAttach);
            }
            sucursal.setAreaCollection(attachedAreaCollection);
            em.persist(sucursal);
            for (Empleado empleadoCollectionEmpleado : sucursal.getEmpleadoCollection()) {
                Sucursal oldSucursalSucursalidSucursalOfEmpleadoCollectionEmpleado = empleadoCollectionEmpleado.getSucursalSucursalidSucursal();
                empleadoCollectionEmpleado.setSucursalSucursalidSucursal(sucursal);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
                if (oldSucursalSucursalidSucursalOfEmpleadoCollectionEmpleado != null) {
                    oldSucursalSucursalidSucursalOfEmpleadoCollectionEmpleado.getEmpleadoCollection().remove(empleadoCollectionEmpleado);
                    oldSucursalSucursalidSucursalOfEmpleadoCollectionEmpleado = em.merge(oldSucursalSucursalidSucursalOfEmpleadoCollectionEmpleado);
                }
            }
            for (Area areaCollectionArea : sucursal.getAreaCollection()) {
                Sucursal oldSucursalSucursalidSucursalOfAreaCollectionArea = areaCollectionArea.getSucursalSucursalidSucursal();
                areaCollectionArea.setSucursalSucursalidSucursal(sucursal);
                areaCollectionArea = em.merge(areaCollectionArea);
                if (oldSucursalSucursalidSucursalOfAreaCollectionArea != null) {
                    oldSucursalSucursalidSucursalOfAreaCollectionArea.getAreaCollection().remove(areaCollectionArea);
                    oldSucursalSucursalidSucursalOfAreaCollectionArea = em.merge(oldSucursalSucursalidSucursalOfAreaCollectionArea);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSucursal(sucursal.getSucursalidSucursal()) != null) {
                throw new PreexistingEntityException("Sucursal " + sucursal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sucursal sucursal) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal persistentSucursal = em.find(Sucursal.class, sucursal.getSucursalidSucursal());
            Collection<Empleado> empleadoCollectionOld = persistentSucursal.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = sucursal.getEmpleadoCollection();
            Collection<Area> areaCollectionOld = persistentSucursal.getAreaCollection();
            Collection<Area> areaCollectionNew = sucursal.getAreaCollection();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoCollectionOldEmpleado + " since its sucursalSucursalidSucursal field is not nullable.");
                }
            }
            for (Area areaCollectionOldArea : areaCollectionOld) {
                if (!areaCollectionNew.contains(areaCollectionOldArea)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Area " + areaCollectionOldArea + " since its sucursalSucursalidSucursal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Empleado> attachedEmpleadoCollectionNew = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionNewEmpleadoToAttach : empleadoCollectionNew) {
                empleadoCollectionNewEmpleadoToAttach = em.getReference(empleadoCollectionNewEmpleadoToAttach.getClass(), empleadoCollectionNewEmpleadoToAttach.getEmpNoEmpleado());
                attachedEmpleadoCollectionNew.add(empleadoCollectionNewEmpleadoToAttach);
            }
            empleadoCollectionNew = attachedEmpleadoCollectionNew;
            sucursal.setEmpleadoCollection(empleadoCollectionNew);
            Collection<Area> attachedAreaCollectionNew = new ArrayList<Area>();
            for (Area areaCollectionNewAreaToAttach : areaCollectionNew) {
                areaCollectionNewAreaToAttach = em.getReference(areaCollectionNewAreaToAttach.getClass(), areaCollectionNewAreaToAttach.getAreidArea());
                attachedAreaCollectionNew.add(areaCollectionNewAreaToAttach);
            }
            areaCollectionNew = attachedAreaCollectionNew;
            sucursal.setAreaCollection(areaCollectionNew);
            sucursal = em.merge(sucursal);
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    Sucursal oldSucursalSucursalidSucursalOfEmpleadoCollectionNewEmpleado = empleadoCollectionNewEmpleado.getSucursalSucursalidSucursal();
                    empleadoCollectionNewEmpleado.setSucursalSucursalidSucursal(sucursal);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                    if (oldSucursalSucursalidSucursalOfEmpleadoCollectionNewEmpleado != null && !oldSucursalSucursalidSucursalOfEmpleadoCollectionNewEmpleado.equals(sucursal)) {
                        oldSucursalSucursalidSucursalOfEmpleadoCollectionNewEmpleado.getEmpleadoCollection().remove(empleadoCollectionNewEmpleado);
                        oldSucursalSucursalidSucursalOfEmpleadoCollectionNewEmpleado = em.merge(oldSucursalSucursalidSucursalOfEmpleadoCollectionNewEmpleado);
                    }
                }
            }
            for (Area areaCollectionNewArea : areaCollectionNew) {
                if (!areaCollectionOld.contains(areaCollectionNewArea)) {
                    Sucursal oldSucursalSucursalidSucursalOfAreaCollectionNewArea = areaCollectionNewArea.getSucursalSucursalidSucursal();
                    areaCollectionNewArea.setSucursalSucursalidSucursal(sucursal);
                    areaCollectionNewArea = em.merge(areaCollectionNewArea);
                    if (oldSucursalSucursalidSucursalOfAreaCollectionNewArea != null && !oldSucursalSucursalidSucursalOfAreaCollectionNewArea.equals(sucursal)) {
                        oldSucursalSucursalidSucursalOfAreaCollectionNewArea.getAreaCollection().remove(areaCollectionNewArea);
                        oldSucursalSucursalidSucursalOfAreaCollectionNewArea = em.merge(oldSucursalSucursalidSucursalOfAreaCollectionNewArea);
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
                Integer id = sucursal.getSucursalidSucursal();
                if (findSucursal(id) == null) {
                    throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursal;
            try {
                sucursal = em.getReference(Sucursal.class, id);
                sucursal.getSucursalidSucursal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Empleado> empleadoCollectionOrphanCheck = sucursal.getEmpleadoCollection();
            for (Empleado empleadoCollectionOrphanCheckEmpleado : empleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the Empleado " + empleadoCollectionOrphanCheckEmpleado + " in its empleadoCollection field has a non-nullable sucursalSucursalidSucursal field.");
            }
            Collection<Area> areaCollectionOrphanCheck = sucursal.getAreaCollection();
            for (Area areaCollectionOrphanCheckArea : areaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the Area " + areaCollectionOrphanCheckArea + " in its areaCollection field has a non-nullable sucursalSucursalidSucursal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sucursal);
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

    public List<Sucursal> findSucursalEntities() {
        return findSucursalEntities(true, -1, -1);
    }

    public List<Sucursal> findSucursalEntities(int maxResults, int firstResult) {
        return findSucursalEntities(false, maxResults, firstResult);
    }

    private List<Sucursal> findSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursal.class));
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

    public Sucursal findSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursal> rt = cq.from(Sucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
