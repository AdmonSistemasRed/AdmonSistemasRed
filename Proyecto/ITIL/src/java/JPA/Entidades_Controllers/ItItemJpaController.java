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
import JPA.Entidades.ItItem;
import JPA.Entidades.Telecommunications;
import JPA.Entidades.Perifericos;
import JPA.Entidades.ItItemHasEmpleado;
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
public class ItItemJpaController implements Serializable {

    public ItItemJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }

    public void create(ItItem itItem) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (itItem.getItItemHasEmpleadoCollection() == null) {
            itItem.setItItemHasEmpleadoCollection(new ArrayList<ItItemHasEmpleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Computadora computadoraidComputadora = itItem.getComputadoraidComputadora();
            if (computadoraidComputadora != null) {
                computadoraidComputadora = em.getReference(computadoraidComputadora.getClass(), computadoraidComputadora.getIdComputadora());
                itItem.setComputadoraidComputadora(computadoraidComputadora);
            }
            Telecommunications telecommunicationsidTelecom = itItem.getTelecommunicationsidTelecom();
            if (telecommunicationsidTelecom != null) {
                telecommunicationsidTelecom = em.getReference(telecommunicationsidTelecom.getClass(), telecommunicationsidTelecom.getIdTelecom());
                itItem.setTelecommunicationsidTelecom(telecommunicationsidTelecom);
            }
            Perifericos perifericosidPeriferico = itItem.getPerifericosidPeriferico();
            if (perifericosidPeriferico != null) {
                perifericosidPeriferico = em.getReference(perifericosidPeriferico.getClass(), perifericosidPeriferico.getIdPeriferico());
                itItem.setPerifericosidPeriferico(perifericosidPeriferico);
            }
            Collection<ItItemHasEmpleado> attachedItItemHasEmpleadoCollection = new ArrayList<ItItemHasEmpleado>();
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach : itItem.getItItemHasEmpleadoCollection()) {
                itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach = em.getReference(itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach.getClass(), itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach.getItItemHasEmpleadoPK());
                attachedItItemHasEmpleadoCollection.add(itItemHasEmpleadoCollectionItItemHasEmpleadoToAttach);
            }
            itItem.setItItemHasEmpleadoCollection(attachedItItemHasEmpleadoCollection);
            em.persist(itItem);
            if (computadoraidComputadora != null) {
                computadoraidComputadora.getItItemCollection().add(itItem);
                computadoraidComputadora = em.merge(computadoraidComputadora);
            }
            if (telecommunicationsidTelecom != null) {
                telecommunicationsidTelecom.getItItemCollection().add(itItem);
                telecommunicationsidTelecom = em.merge(telecommunicationsidTelecom);
            }
            if (perifericosidPeriferico != null) {
                perifericosidPeriferico.getItItemCollection().add(itItem);
                perifericosidPeriferico = em.merge(perifericosidPeriferico);
            }
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionItItemHasEmpleado : itItem.getItItemHasEmpleadoCollection()) {
                ItItem oldItItemOfItItemHasEmpleadoCollectionItItemHasEmpleado = itItemHasEmpleadoCollectionItItemHasEmpleado.getItItem();
                itItemHasEmpleadoCollectionItItemHasEmpleado.setItItem(itItem);
                itItemHasEmpleadoCollectionItItemHasEmpleado = em.merge(itItemHasEmpleadoCollectionItItemHasEmpleado);
                if (oldItItemOfItItemHasEmpleadoCollectionItItemHasEmpleado != null) {
                    oldItItemOfItItemHasEmpleadoCollectionItItemHasEmpleado.getItItemHasEmpleadoCollection().remove(itItemHasEmpleadoCollectionItItemHasEmpleado);
                    oldItItemOfItItemHasEmpleadoCollectionItItemHasEmpleado = em.merge(oldItItemOfItItemHasEmpleadoCollectionItItemHasEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findItItem(itItem.getItSerie()) != null) {
                throw new PreexistingEntityException("ItItem " + itItem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItItem itItem) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItItem persistentItItem = em.find(ItItem.class, itItem.getItSerie());
            Computadora computadoraidComputadoraOld = persistentItItem.getComputadoraidComputadora();
            Computadora computadoraidComputadoraNew = itItem.getComputadoraidComputadora();
            Telecommunications telecommunicationsidTelecomOld = persistentItItem.getTelecommunicationsidTelecom();
            Telecommunications telecommunicationsidTelecomNew = itItem.getTelecommunicationsidTelecom();
            Perifericos perifericosidPerifericoOld = persistentItItem.getPerifericosidPeriferico();
            Perifericos perifericosidPerifericoNew = itItem.getPerifericosidPeriferico();
            Collection<ItItemHasEmpleado> itItemHasEmpleadoCollectionOld = persistentItItem.getItItemHasEmpleadoCollection();
            Collection<ItItemHasEmpleado> itItemHasEmpleadoCollectionNew = itItem.getItItemHasEmpleadoCollection();
            List<String> illegalOrphanMessages = null;
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionOldItItemHasEmpleado : itItemHasEmpleadoCollectionOld) {
                if (!itItemHasEmpleadoCollectionNew.contains(itItemHasEmpleadoCollectionOldItItemHasEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItItemHasEmpleado " + itItemHasEmpleadoCollectionOldItItemHasEmpleado + " since its itItem field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (computadoraidComputadoraNew != null) {
                computadoraidComputadoraNew = em.getReference(computadoraidComputadoraNew.getClass(), computadoraidComputadoraNew.getIdComputadora());
                itItem.setComputadoraidComputadora(computadoraidComputadoraNew);
            }
            if (telecommunicationsidTelecomNew != null) {
                telecommunicationsidTelecomNew = em.getReference(telecommunicationsidTelecomNew.getClass(), telecommunicationsidTelecomNew.getIdTelecom());
                itItem.setTelecommunicationsidTelecom(telecommunicationsidTelecomNew);
            }
            if (perifericosidPerifericoNew != null) {
                perifericosidPerifericoNew = em.getReference(perifericosidPerifericoNew.getClass(), perifericosidPerifericoNew.getIdPeriferico());
                itItem.setPerifericosidPeriferico(perifericosidPerifericoNew);
            }
            Collection<ItItemHasEmpleado> attachedItItemHasEmpleadoCollectionNew = new ArrayList<ItItemHasEmpleado>();
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach : itItemHasEmpleadoCollectionNew) {
                itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach = em.getReference(itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach.getClass(), itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach.getItItemHasEmpleadoPK());
                attachedItItemHasEmpleadoCollectionNew.add(itItemHasEmpleadoCollectionNewItItemHasEmpleadoToAttach);
            }
            itItemHasEmpleadoCollectionNew = attachedItItemHasEmpleadoCollectionNew;
            itItem.setItItemHasEmpleadoCollection(itItemHasEmpleadoCollectionNew);
            itItem = em.merge(itItem);
            if (computadoraidComputadoraOld != null && !computadoraidComputadoraOld.equals(computadoraidComputadoraNew)) {
                computadoraidComputadoraOld.getItItemCollection().remove(itItem);
                computadoraidComputadoraOld = em.merge(computadoraidComputadoraOld);
            }
            if (computadoraidComputadoraNew != null && !computadoraidComputadoraNew.equals(computadoraidComputadoraOld)) {
                computadoraidComputadoraNew.getItItemCollection().add(itItem);
                computadoraidComputadoraNew = em.merge(computadoraidComputadoraNew);
            }
            if (telecommunicationsidTelecomOld != null && !telecommunicationsidTelecomOld.equals(telecommunicationsidTelecomNew)) {
                telecommunicationsidTelecomOld.getItItemCollection().remove(itItem);
                telecommunicationsidTelecomOld = em.merge(telecommunicationsidTelecomOld);
            }
            if (telecommunicationsidTelecomNew != null && !telecommunicationsidTelecomNew.equals(telecommunicationsidTelecomOld)) {
                telecommunicationsidTelecomNew.getItItemCollection().add(itItem);
                telecommunicationsidTelecomNew = em.merge(telecommunicationsidTelecomNew);
            }
            if (perifericosidPerifericoOld != null && !perifericosidPerifericoOld.equals(perifericosidPerifericoNew)) {
                perifericosidPerifericoOld.getItItemCollection().remove(itItem);
                perifericosidPerifericoOld = em.merge(perifericosidPerifericoOld);
            }
            if (perifericosidPerifericoNew != null && !perifericosidPerifericoNew.equals(perifericosidPerifericoOld)) {
                perifericosidPerifericoNew.getItItemCollection().add(itItem);
                perifericosidPerifericoNew = em.merge(perifericosidPerifericoNew);
            }
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionNewItItemHasEmpleado : itItemHasEmpleadoCollectionNew) {
                if (!itItemHasEmpleadoCollectionOld.contains(itItemHasEmpleadoCollectionNewItItemHasEmpleado)) {
                    ItItem oldItItemOfItItemHasEmpleadoCollectionNewItItemHasEmpleado = itItemHasEmpleadoCollectionNewItItemHasEmpleado.getItItem();
                    itItemHasEmpleadoCollectionNewItItemHasEmpleado.setItItem(itItem);
                    itItemHasEmpleadoCollectionNewItItemHasEmpleado = em.merge(itItemHasEmpleadoCollectionNewItItemHasEmpleado);
                    if (oldItItemOfItItemHasEmpleadoCollectionNewItItemHasEmpleado != null && !oldItItemOfItItemHasEmpleadoCollectionNewItItemHasEmpleado.equals(itItem)) {
                        oldItItemOfItItemHasEmpleadoCollectionNewItItemHasEmpleado.getItItemHasEmpleadoCollection().remove(itItemHasEmpleadoCollectionNewItItemHasEmpleado);
                        oldItItemOfItItemHasEmpleadoCollectionNewItItemHasEmpleado = em.merge(oldItItemOfItItemHasEmpleadoCollectionNewItItemHasEmpleado);
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
                String id = itItem.getItSerie();
                if (findItItem(id) == null) {
                    throw new NonexistentEntityException("The itItem with id " + id + " no longer exists.");
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
            ItItem itItem;
            try {
                itItem = em.getReference(ItItem.class, id);
                itItem.getItSerie();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itItem with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ItItemHasEmpleado> itItemHasEmpleadoCollectionOrphanCheck = itItem.getItItemHasEmpleadoCollection();
            for (ItItemHasEmpleado itItemHasEmpleadoCollectionOrphanCheckItItemHasEmpleado : itItemHasEmpleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the ItItemHasEmpleado " + itItemHasEmpleadoCollectionOrphanCheckItItemHasEmpleado + " in its itItemHasEmpleadoCollection field has a non-nullable itItem field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Computadora computadoraidComputadora = itItem.getComputadoraidComputadora();
            if (computadoraidComputadora != null) {
                computadoraidComputadora.getItItemCollection().remove(itItem);
                computadoraidComputadora = em.merge(computadoraidComputadora);
            }
            Telecommunications telecommunicationsidTelecom = itItem.getTelecommunicationsidTelecom();
            if (telecommunicationsidTelecom != null) {
                telecommunicationsidTelecom.getItItemCollection().remove(itItem);
                telecommunicationsidTelecom = em.merge(telecommunicationsidTelecom);
            }
            Perifericos perifericosidPeriferico = itItem.getPerifericosidPeriferico();
            if (perifericosidPeriferico != null) {
                perifericosidPeriferico.getItItemCollection().remove(itItem);
                perifericosidPeriferico = em.merge(perifericosidPeriferico);
            }
            em.remove(itItem);
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

    public List<ItItem> findItItemEntities() {
        return findItItemEntities(true, -1, -1);
    }

    public List<ItItem> findItItemEntities(int maxResults, int firstResult) {
        return findItItemEntities(false, maxResults, firstResult);
    }

    private List<ItItem> findItItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItItem.class));
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

    public ItItem findItItem(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItItem.class, id);
        } finally {
            em.close();
        }
    }

    public int getItItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItItem> rt = cq.from(ItItem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
