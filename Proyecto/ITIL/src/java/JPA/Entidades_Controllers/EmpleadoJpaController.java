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
import JPA.Entidades.Empleado;
import JPA.Entidades.Lenguaje;
import java.util.ArrayList;
import java.util.Collection;
import JPA.Entidades.Roles;
import JPA.Entidades.ItItem;
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
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (empleado.getLenguajeCollection() == null) {
            empleado.setLenguajeCollection(new ArrayList<Lenguaje>());
        }
        if (empleado.getRolesCollection() == null) {
            empleado.setRolesCollection(new ArrayList<Roles>());
        }
        if (empleado.getItItemCollection() == null) {
            empleado.setItItemCollection(new ArrayList<ItItem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area areaareidArea = empleado.getAreaareidArea();
            if (areaareidArea != null) {
                areaareidArea = em.getReference(areaareidArea.getClass(), areaareidArea.getAreidArea());
                empleado.setAreaareidArea(areaareidArea);
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
            Collection<ItItem> attachedItItemCollection = new ArrayList<ItItem>();
            for (ItItem itItemCollectionItItemToAttach : empleado.getItItemCollection()) {
                itItemCollectionItItemToAttach = em.getReference(itItemCollectionItItemToAttach.getClass(), itItemCollectionItItemToAttach.getItItemPK());
                attachedItItemCollection.add(itItemCollectionItItemToAttach);
            }
            empleado.setItItemCollection(attachedItItemCollection);
            em.persist(empleado);
            if (areaareidArea != null) {
                areaareidArea.getEmpleadoCollection().add(empleado);
                areaareidArea = em.merge(areaareidArea);
            }
            for (Lenguaje lenguajeCollectionLenguaje : empleado.getLenguajeCollection()) {
                lenguajeCollectionLenguaje.getEmpleadoCollection().add(empleado);
                lenguajeCollectionLenguaje = em.merge(lenguajeCollectionLenguaje);
            }
            for (Roles rolesCollectionRoles : empleado.getRolesCollection()) {
                rolesCollectionRoles.getEmpleadoCollection().add(empleado);
                rolesCollectionRoles = em.merge(rolesCollectionRoles);
            }
            for (ItItem itItemCollectionItItem : empleado.getItItemCollection()) {
                itItemCollectionItItem.getEmpleadoCollection().add(empleado);
                itItemCollectionItItem = em.merge(itItemCollectionItItem);
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

    public void edit(Empleado empleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getEmpNoEmpleado());
            Area areaareidAreaOld = persistentEmpleado.getAreaareidArea();
            Area areaareidAreaNew = empleado.getAreaareidArea();
            Collection<Lenguaje> lenguajeCollectionOld = persistentEmpleado.getLenguajeCollection();
            Collection<Lenguaje> lenguajeCollectionNew = empleado.getLenguajeCollection();
            Collection<Roles> rolesCollectionOld = persistentEmpleado.getRolesCollection();
            Collection<Roles> rolesCollectionNew = empleado.getRolesCollection();
            Collection<ItItem> itItemCollectionOld = persistentEmpleado.getItItemCollection();
            Collection<ItItem> itItemCollectionNew = empleado.getItItemCollection();
            if (areaareidAreaNew != null) {
                areaareidAreaNew = em.getReference(areaareidAreaNew.getClass(), areaareidAreaNew.getAreidArea());
                empleado.setAreaareidArea(areaareidAreaNew);
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
            Collection<ItItem> attachedItItemCollectionNew = new ArrayList<ItItem>();
            for (ItItem itItemCollectionNewItItemToAttach : itItemCollectionNew) {
                itItemCollectionNewItItemToAttach = em.getReference(itItemCollectionNewItItemToAttach.getClass(), itItemCollectionNewItItemToAttach.getItItemPK());
                attachedItItemCollectionNew.add(itItemCollectionNewItItemToAttach);
            }
            itItemCollectionNew = attachedItItemCollectionNew;
            empleado.setItItemCollection(itItemCollectionNew);
            empleado = em.merge(empleado);
            if (areaareidAreaOld != null && !areaareidAreaOld.equals(areaareidAreaNew)) {
                areaareidAreaOld.getEmpleadoCollection().remove(empleado);
                areaareidAreaOld = em.merge(areaareidAreaOld);
            }
            if (areaareidAreaNew != null && !areaareidAreaNew.equals(areaareidAreaOld)) {
                areaareidAreaNew.getEmpleadoCollection().add(empleado);
                areaareidAreaNew = em.merge(areaareidAreaNew);
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
            for (ItItem itItemCollectionOldItItem : itItemCollectionOld) {
                if (!itItemCollectionNew.contains(itItemCollectionOldItItem)) {
                    itItemCollectionOldItItem.getEmpleadoCollection().remove(empleado);
                    itItemCollectionOldItItem = em.merge(itItemCollectionOldItItem);
                }
            }
            for (ItItem itItemCollectionNewItItem : itItemCollectionNew) {
                if (!itItemCollectionOld.contains(itItemCollectionNewItItem)) {
                    itItemCollectionNewItItem.getEmpleadoCollection().add(empleado);
                    itItemCollectionNewItItem = em.merge(itItemCollectionNewItItem);
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

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
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
            Area areaareidArea = empleado.getAreaareidArea();
            if (areaareidArea != null) {
                areaareidArea.getEmpleadoCollection().remove(empleado);
                areaareidArea = em.merge(areaareidArea);
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
            Collection<ItItem> itItemCollection = empleado.getItItemCollection();
            for (ItItem itItemCollectionItItem : itItemCollection) {
                itItemCollectionItItem.getEmpleadoCollection().remove(empleado);
                itItemCollectionItItem = em.merge(itItemCollectionItItem);
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
