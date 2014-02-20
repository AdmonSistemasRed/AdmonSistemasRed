/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades_Controllers;

import JPA.Entidades.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Entidades.Sucursal;
import JPA.Entidades.Lenguaje;
import java.util.ArrayList;
import java.util.Collection;
import JPA.Entidades.Roles;
import JPA.Entidades.ItItemHasEmpleado;
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
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (empleado.getLenguajeCollection() == null) {
            empleado.setLenguajeCollection(new ArrayList<Lenguaje>());
        }
        if (empleado.getRolesCollection() == null) {
            empleado.setRolesCollection(new ArrayList<Roles>());
        }
        if (empleado.getItItemHasEmpleadoCollection() == null) {
            empleado.setItItemHasEmpleadoCollection(new ArrayList<ItItemHasEmpleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursalSucursalidSucursal = empleado.getSucursalSucursalidSucursal();
            if (sucursalSucursalidSucursal != null) {
                sucursalSucursalidSucursal = em.getReference(sucursalSucursalidSucursal.getClass(), sucursalSucursalidSucursal.getSucursalidSucursal());
                empleado.setSucursalSucursalidSucursal(sucursalSucursalidSucursal);
            }
            Collection<Lenguaje> attachedLenguajeCollection = new ArrayList<Lenguaje>();
            for (Lenguaje lenguajeCollectionLenguajeToAttach : empleado.getLenguajeCollection()) {
                lenguajeCollectionLenguajeToAttach = em.getReference(lenguajeCollectionLenguajeToAttach.getClass(), lenguajeCollectionLenguajeToAttach.getLenLenguaje());
                attachedLenguajeCollection.add(lenguajeCollectionLenguajeToAttach);
            }
            empleado.setLenguajeCollection(attachedLenguajeCollection);
            Collection<Roles> attachedRolesCollection = new ArrayList<Roles>();
            for (Roles rolesCollectionRolesToAttach : empleado.getRolesCollection()) {
                rolesCollectionRolesToAttach = em.getReference(rolesCollectionRolesToAttach.getClass(), rolesCollectionRolesToAttach.getRolidRol());
                attachedRolesCollection.add(rolesCollectionRolesToAttach);
            }
            empleado.setRolesCollection(attachedRolesCollection);
            Collection<ItItemHasEmpleado> attachedItItemHasEmpleadoCollection = new ArrayList<ItItemHasEmpleado>();
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach : empleado.getItItemHasEmpleadoCollection()) {
                itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach = em.getReference(itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach.getClass(), itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach.getItItemHasEmpleadoPK());
                attachedItItemHasEmpleadoCollection.add(itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach);
            }
            empleado.setItItemHasEmpleadoCollection(attachedItItemHasEmpleadoCollection);
            em.persist(empleado);
            if (sucursalSucursalidSucursal != null) {
                sucursalSucursalidSucursal.getEmpleadoCollection().add(empleado);
                sucursalSucursalidSucursal = em.merge(sucursalSucursalidSucursal);
            }
            for (Lenguaje lenguajeCollectionLenguaje : empleado.getLenguajeCollection()) {
                lenguajeCollectionLenguaje.getEmpleadoCollection().add(empleado);
                lenguajeCollectionLenguaje = em.merge(lenguajeCollectionLenguaje);
            }
            for (Roles rolesCollectionRoles : empleado.getRolesCollection()) {
                rolesCollectionRoles.getEmpleadoCollection().add(empleado);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
            }
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionItItemHasEmpleado : empleado.getItItemHasEmpleadoCollection()) {
                Empleado oldEmpleadoOfItItemHasEmpleadoCollectionItItemHasEmpleado = itItemHasEmpleadoCollectionItItemHasEmpleado.getEmpleado();
                itItemHasEmpleadoCollectionItItemHasEmpleado.setEmpleado(empleado);
                itItemHasEmpleadoCollectionItItemHasEmpleado = em.merge(itItemHasEmpleadoCollectionItItemHasEmpleado);
                if (oldEmpleadoOfItItemHasEmpleadoCollectionItItemHasEmpleado != null) {
                    oldEmpleadoOfItItemHasEmpleadoCollectionItItemHasEmpleado.getItItemHasEmpleadoCollection().remove(itItemHasEmpleadoCollectionItItemHasEmpleado);
                    oldEmpleadoOfItItemHasEmpleadoCollectionItItemHasEmpleado = em.merge(oldEmpleadoOfItItemHasEmpleadoCollectionItItemHasEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmpleado(empleado.getEmpNoEmpleado()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getEmpNoEmpleado());
            Sucursal sucursalSucursalidSucursalOld = persistentEmpleado.getSucursalSucursalidSucursal();
            Sucursal sucursalSucursalidSucursalNew = empleado.getSucursalSucursalidSucursal();
            Collection<Lenguaje> lenguajeCollectionOld = persistentEmpleado.getLenguajeCollection();
            Collection<Lenguaje> lenguajeCollectionNew = empleado.getLenguajeCollection();
            Collection<Roles> rolesCollectionOld = persistentEmpleado.getRolesCollection();
            Collection<Roles> rolesCollectionNew = empleado.getRolesCollection();
            Collection<ItItemHasEmpleado> itItemHasEmpleadoCollectionOld = persistentEmpleado.getItItemHasEmpleadoCollection();
            Collection<ItItemHasEmpleado> itItemHasEmpleadoCollectionNew = empleado.getItItemHasEmpleadoCollection();
            List<String> illegalOrphanMessages = null;
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionOldItItemHasEmpleado : itItemHasEmpleadoCollectionOld) {
                if (!itItemHasEmpleadoCollectionNew.contains(itItemHasEmpleadoCollectionOldItItemHasEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItItemHasEmpleado " + itItemHasEmpleadoCollectionOldItItemHasEmpleado + " since its empleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sucursalSucursalidSucursalNew != null) {
                sucursalSucursalidSucursalNew = em.getReference(sucursalSucursalidSucursalNew.getClass(), sucursalSucursalidSucursalNew.getSucursalidSucursal());
                empleado.setSucursalSucursalidSucursal(sucursalSucursalidSucursalNew);
            }
            Collection<Lenguaje> attachedLenguajeCollectionNew = new ArrayList<Lenguaje>();
            for (Lenguaje lenguajeCollectionNewLenguajeToAttach : lenguajeCollectionNew) {
                lenguajeCollectionNewLenguajeToAttach = em.getReference(lenguajeCollectionNewLenguajeToAttach.getClass(), lenguajeCollectionNewLenguajeToAttach.getLenLenguaje());
                attachedLenguajeCollectionNew.add(lenguajeCollectionNewLenguajeToAttach);
            }
            lenguajeCollectionNew = attachedLenguajeCollectionNew;
            empleado.setLenguajeCollection(lenguajeCollectionNew);
            Collection<Roles> attachedRolesCollectionNew = new ArrayList<Roles>();
            for (Roles rolesCollectionNewRolesToAttach : rolesCollectionNew) {
                rolesCollectionNewRolesToAttach = em.getReference(rolesCollectionNewRolesToAttach.getClass(), rolesCollectionNewRolesToAttach.getRolidRol());
                attachedRolesCollectionNew.add(rolesCollectionNewRolesToAttach);
            }
            rolesCollectionNew = attachedRolesCollectionNew;
            empleado.setRolesCollection(rolesCollectionNew);
            Collection<ItItemHasEmpleado> attachedItItemHasEmpleadoCollectionNew = new ArrayList<ItItemHasEmpleado>();
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach : itItemHasEmpleadoCollectionNew) {
                itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach = em.getReference(itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach.getClass(), itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach.getItItemHasEmpleadoPK());
                attachedItItemHasEmpleadoCollectionNew.add(itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach);
            }
            itItemHasEmpleadoCollectionNew = attachedItItemHasEmpleadoCollectionNew;
            empleado.setItItemHasEmpleadoCollection(itItemHasEmpleadoCollectionNew);
            empleado = em.merge(empleado);
            if (sucursalSucursalidSucursalOld != null && !sucursalSucursalidSucursalOld.equals(sucursalSucursalidSucursalNew)) {
                sucursalSucursalidSucursalOld.getEmpleadoCollection().remove(empleado);
                sucursalSucursalidSucursalOld = em.merge(sucursalSucursalidSucursalOld);
            }
            if (sucursalSucursalidSucursalNew != null && !sucursalSucursalidSucursalNew.equals(sucursalSucursalidSucursalOld)) {
                sucursalSucursalidSucursalNew.getEmpleadoCollection().add(empleado);
                sucursalSucursalidSucursalNew = em.merge(sucursalSucursalidSucursalNew);
            }
            for (Lenguaje lenguajeCollectionOldLenguaje : lenguajeCollectionOld) {
                if (!lenguajeCollectionNew.contains(lenguajeCollectionOldLenguaje)) {
                    lenguajeCollectionOldLenguaje.getEmpleadoCollection().remove(empleado);
                    lenguajeCollectionOldLenguaje = em.merge(lenguajeCollectionOldLenguaje);
                }
            }
            for (Lenguaje lenguajeCollectionNewLenguaje : lenguajeCollectionNew) {
                if (!lenguajeCollectionOld.contains(lenguajeCollectionNewLenguaje)) {
                    lenguajeCollectionNewLenguaje.getEmpleadoCollection().add(empleado);
                    lenguajeCollectionNewLenguaje = em.merge(lenguajeCollectionNewLenguaje);
                }
            }
            for (Roles rolesCollectionOldRoles : rolesCollectionOld) {
                if (!rolesCollectionNew.contains(rolesCollectionOldRoles)) {
                    rolesCollectionOldRoles.getEmpleadoCollection().remove(empleado);
                    rolesCollectionOldRoles = em.merge(rolesCollectionOldRoles);
                }
            }
            for (Roles rolesCollectionNewRoles : rolesCollectionNew) {
                if (!rolesCollectionOld.contains(rolesCollectionNewRoles)) {
                    rolesCollectionNewRoles.getEmpleadoCollection().add(empleado);
                    rolesCollectionNewRoles = em.merge(rolesCollectionNewRoles);
                }
            }
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionNewItItemHasEmpleado : itItemHasEmpleadoCollectionNew) {
                if (!itItemHasEmpleadoCollectionOld.contains(itItemHasEmpleadoCollectionNewItItemHasEmpleado)) {
                    Empleado oldEmpleadoOfItItemHasEmpleadoCollectionNewItItemHasEmpleado = itItemHasEmpleadoCollectionNewItItemHasEmpleado.getEmpleado();
                    itItemHasEmpleadoCollectionNewItItemHasEmpleado.setEmpleado(empleado);
                    itItemHasEmpleadoCollectionNewItItemHasEmpleado = em.merge(itItemHasEmpleadoCollectionNewItItemHasEmpleado);
                    if (oldEmpleadoOfItItemHasEmpleadoCollectionNewItItemHasEmpleado != null && !oldEmpleadoOfItItemHasEmpleadoCollectionNewItItemHasEmpleado.equals(empleado)) {
                        oldEmpleadoOfItItemHasEmpleadoCollectionNewItItemHasEmpleado.getItItemHasEmpleadoCollection().remove(itItemHasEmpleadoCollectionNewItItemHasEmpleado);
                        oldEmpleadoOfItItemHasEmpleadoCollectionNewItItemHasEmpleado = em.merge(oldEmpleadoOfItItemHasEmpleadoCollectionNewItItemHasEmpleado);
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
                String id = empleado.getEmpNoEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getEmpNoEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ItItemHasEmpleado> itItemHasEmpleadoCollectionOrphanCheck = empleado.getItItemHasEmpleadoCollection();
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionOrphanCheckItItemHasEmpleado : itItemHasEmpleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the ItItemHasEmpleado " + itItemHasEmpleadoCollectionOrphanCheckItItemHasEmpleado + " in its itItemHasEmpleadoCollection field has a non-nullable empleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sucursal sucursalSucursalidSucursal = empleado.getSucursalSucursalidSucursal();
            if (sucursalSucursalidSucursal != null) {
                sucursalSucursalidSucursal.getEmpleadoCollection().remove(empleado);
                sucursalSucursalidSucursal = em.merge(sucursalSucursalidSucursal);
            }
            Collection<Lenguaje> lenguajeCollection = empleado.getLenguajeCollection();
            for (Lenguaje lenguajeCollectionLenguaje : lenguajeCollection) {
                lenguajeCollectionLenguaje.getEmpleadoCollection().remove(empleado);
                lenguajeCollectionLenguaje = em.merge(lenguajeCollectionLenguaje);
            }
            Collection<Roles> rolesCollection = empleado.getRolesCollection();
            for (Roles rolesCollectionRoles : rolesCollection) {
                rolesCollectionRoles.getEmpleadoCollection().remove(empleado);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
            }
            em.remove(empleado);
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

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
