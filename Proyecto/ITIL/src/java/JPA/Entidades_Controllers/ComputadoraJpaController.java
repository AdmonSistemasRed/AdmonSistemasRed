/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades_Controllers;

import JPA.Entidades.Computadora;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Entidades.Servidor;
import JPA.Entidades.Laptops;
import JPA.Entidades.Workstation;
import JPA.Entidades.Software;
import java.util.ArrayList;
import java.util.Collection;
import JPA.Entidades.ItItem;
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
public class ComputadoraJpaController implements Serializable {

    public ComputadoraJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("ITILPU");
        return emf.createEntityManager();
    }

    public void create(Computadora computadora) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (computadora.getSoftwareCollection() == null) {
            computadora.setSoftwareCollection(new ArrayList<Software>());
        }
        if (computadora.getItItemCollection() == null) {
            computadora.setItItemCollection(new ArrayList<ItItem>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servidor servidoridServidor = computadora.getServidoridServidor();
            if (servidoridServidor != null) {
                servidoridServidor = em.getReference(servidoridServidor.getClass(), servidoridServidor.getIdServidor());
                computadora.setServidoridServidor(servidoridServidor);
            }
            Laptops laptopsidLaptop = computadora.getLaptopsidLaptop();
            if (laptopsidLaptop != null) {
                laptopsidLaptop = em.getReference(laptopsidLaptop.getClass(), laptopsidLaptop.getIdLaptop());
                computadora.setLaptopsidLaptop(laptopsidLaptop);
            }
            Workstation workstationidWorkstation = computadora.getWorkstationidWorkstation();
            if (workstationidWorkstation != null) {
                workstationidWorkstation = em.getReference(workstationidWorkstation.getClass(), workstationidWorkstation.getIdWorkstation());
                computadora.setWorkstationidWorkstation(workstationidWorkstation);
            }
            Collection<Software> attachedSoftwareCollection = new ArrayList<Software>();
            for (Software softwareCollectionSoftwareToAttach : computadora.getSoftwareCollection()) {
                softwareCollectionSoftwareToAttach = em.getReference(softwareCollectionSoftwareToAttach.getClass(), softwareCollectionSoftwareToAttach.getIdSoftware());
                attachedSoftwareCollection.add(softwareCollectionSoftwareToAttach);
            }
            computadora.setSoftwareCollection(attachedSoftwareCollection);
            Collection<ItItem> attachedItItemCollection = new ArrayList<ItItem>();
            for (ItItem itItemCollectionItItemToAttach : computadora.getItItemCollection()) {
                itItemCollectionItItemToAttach = em.getReference(itItemCollectionItItemToAttach.getClass(), itItemCollectionItItemToAttach.getItSerie());
                attachedItItemCollection.add(itItemCollectionItItemToAttach);
            }
            computadora.setItItemCollection(attachedItItemCollection);
            em.persist(computadora);
            if (servidoridServidor != null) {
                servidoridServidor.getComputadoraCollection().add(computadora);
                servidoridServidor = em.merge(servidoridServidor);
            }
            if (laptopsidLaptop != null) {
                laptopsidLaptop.getComputadoraCollection().add(computadora);
                laptopsidLaptop = em.merge(laptopsidLaptop);
            }
            if (workstationidWorkstation != null) {
                workstationidWorkstation.getComputadoraCollection().add(computadora);
                workstationidWorkstation = em.merge(workstationidWorkstation);
            }
            for (Software softwareCollectionSoftware : computadora.getSoftwareCollection()) {
                softwareCollectionSoftware.getComputadoraCollection().add(computadora);
                softwareCollectionSoftware = em.merge(softwareCollectionSoftware);
            }
            for (ItItem itItemCollectionItItem : computadora.getItItemCollection()) {
                Computadora oldComputadoraidComputadoraOfItItemCollectionItItem = itItemCollectionItItem.getComputadoraidComputadora();
                itItemCollectionItItem.setComputadoraidComputadora(computadora);
                itItemCollectionItItem = em.merge(itItemCollectionItItem);
                if (oldComputadoraidComputadoraOfItItemCollectionItItem != null) {
                    oldComputadoraidComputadoraOfItItemCollectionItItem.getItItemCollection().remove(itItemCollectionItItem);
                    oldComputadoraidComputadoraOfItItemCollectionItItem = em.merge(oldComputadoraidComputadoraOfItItemCollectionItItem);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findComputadora(computadora.getIdComputadora()) != null) {
                throw new PreexistingEntityException("Computadora " + computadora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Computadora computadora) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Computadora persistentComputadora = em.find(Computadora.class, computadora.getIdComputadora());
            Servidor servidoridServidorOld = persistentComputadora.getServidoridServidor();
            Servidor servidoridServidorNew = computadora.getServidoridServidor();
            Laptops laptopsidLaptopOld = persistentComputadora.getLaptopsidLaptop();
            Laptops laptopsidLaptopNew = computadora.getLaptopsidLaptop();
            Workstation workstationidWorkstationOld = persistentComputadora.getWorkstationidWorkstation();
            Workstation workstationidWorkstationNew = computadora.getWorkstationidWorkstation();
            Collection<Software> softwareCollectionOld = persistentComputadora.getSoftwareCollection();
            Collection<Software> softwareCollectionNew = computadora.getSoftwareCollection();
            Collection<ItItem> itItemCollectionOld = persistentComputadora.getItItemCollection();
            Collection<ItItem> itItemCollectionNew = computadora.getItItemCollection();
            List<String> illegalOrphanMessages = null;
            for (ItItem itItemCollectionOldItItem : itItemCollectionOld) {
                if (!itItemCollectionNew.contains(itItemCollectionOldItItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItItem " + itItemCollectionOldItItem + " since its computadoraidComputadora field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (servidoridServidorNew != null) {
                servidoridServidorNew = em.getReference(servidoridServidorNew.getClass(), servidoridServidorNew.getIdServidor());
                computadora.setServidoridServidor(servidoridServidorNew);
            }
            if (laptopsidLaptopNew != null) {
                laptopsidLaptopNew = em.getReference(laptopsidLaptopNew.getClass(), laptopsidLaptopNew.getIdLaptop());
                computadora.setLaptopsidLaptop(laptopsidLaptopNew);
            }
            if (workstationidWorkstationNew != null) {
                workstationidWorkstationNew = em.getReference(workstationidWorkstationNew.getClass(), workstationidWorkstationNew.getIdWorkstation());
                computadora.setWorkstationidWorkstation(workstationidWorkstationNew);
            }
            Collection<Software> attachedSoftwareCollectionNew = new ArrayList<Software>();
            for (Software softwareCollectionNewSoftwareToAttach : softwareCollectionNew) {
                softwareCollectionNewSoftwareToAttach = em.getReference(softwareCollectionNewSoftwareToAttach.getClass(), softwareCollectionNewSoftwareToAttach.getIdSoftware());
                attachedSoftwareCollectionNew.add(softwareCollectionNewSoftwareToAttach);
            }
            softwareCollectionNew = attachedSoftwareCollectionNew;
            computadora.setSoftwareCollection(softwareCollectionNew);
            Collection<ItItem> attachedItItemCollectionNew = new ArrayList<ItItem>();
            for (ItItem itItemCollectionNewItItemToAttach : itItemCollectionNew) {
                itItemCollectionNewItItemToAttach = em.getReference(itItemCollectionNewItItemToAttach.getClass(), itItemCollectionNewItItemToAttach.getItSerie());
                attachedItItemCollectionNew.add(itItemCollectionNewItItemToAttach);
            }
            itItemCollectionNew = attachedItItemCollectionNew;
            computadora.setItItemCollection(itItemCollectionNew);
            computadora = em.merge(computadora);
            if (servidoridServidorOld != null && !servidoridServidorOld.equals(servidoridServidorNew)) {
                servidoridServidorOld.getComputadoraCollection().remove(computadora);
                servidoridServidorOld = em.merge(servidoridServidorOld);
            }
            if (servidoridServidorNew != null && !servidoridServidorNew.equals(servidoridServidorOld)) {
                servidoridServidorNew.getComputadoraCollection().add(computadora);
                servidoridServidorNew = em.merge(servidoridServidorNew);
            }
            if (laptopsidLaptopOld != null && !laptopsidLaptopOld.equals(laptopsidLaptopNew)) {
                laptopsidLaptopOld.getComputadoraCollection().remove(computadora);
                laptopsidLaptopOld = em.merge(laptopsidLaptopOld);
            }
            if (laptopsidLaptopNew != null && !laptopsidLaptopNew.equals(laptopsidLaptopOld)) {
                laptopsidLaptopNew.getComputadoraCollection().add(computadora);
                laptopsidLaptopNew = em.merge(laptopsidLaptopNew);
            }
            if (workstationidWorkstationOld != null && !workstationidWorkstationOld.equals(workstationidWorkstationNew)) {
                workstationidWorkstationOld.getComputadoraCollection().remove(computadora);
                workstationidWorkstationOld = em.merge(workstationidWorkstationOld);
            }
            if (workstationidWorkstationNew != null && !workstationidWorkstationNew.equals(workstationidWorkstationOld)) {
                workstationidWorkstationNew.getComputadoraCollection().add(computadora);
                workstationidWorkstationNew = em.merge(workstationidWorkstationNew);
            }
            for (Software softwareCollectionOldSoftware : softwareCollectionOld) {
                if (!softwareCollectionNew.contains(softwareCollectionOldSoftware)) {
                    softwareCollectionOldSoftware.getComputadoraCollection().remove(computadora);
                    softwareCollectionOldSoftware = em.merge(softwareCollectionOldSoftware);
                }
            }
            for (Software softwareCollectionNewSoftware : softwareCollectionNew) {
                if (!softwareCollectionOld.contains(softwareCollectionNewSoftware)) {
                    softwareCollectionNewSoftware.getComputadoraCollection().add(computadora);
                    softwareCollectionNewSoftware = em.merge(softwareCollectionNewSoftware);
                }
            }
            for (ItItem itItemCollectionNewItItem : itItemCollectionNew) {
                if (!itItemCollectionOld.contains(itItemCollectionNewItItem)) {
                    Computadora oldComputadoraidComputadoraOfItItemCollectionNewItItem = itItemCollectionNewItItem.getComputadoraidComputadora();
                    itItemCollectionNewItItem.setComputadoraidComputadora(computadora);
                    itItemCollectionNewItItem = em.merge(itItemCollectionNewItItem);
                    if (oldComputadoraidComputadoraOfItItemCollectionNewItItem != null && !oldComputadoraidComputadoraOfItItemCollectionNewItItem.equals(computadora)) {
                        oldComputadoraidComputadoraOfItItemCollectionNewItItem.getItItemCollection().remove(itItemCollectionNewItItem);
                        oldComputadoraidComputadoraOfItItemCollectionNewItItem = em.merge(oldComputadoraidComputadoraOfItItemCollectionNewItItem);
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
                String id = computadora.getIdComputadora();
                if (findComputadora(id) == null) {
                    throw new NonexistentEntityException("The computadora with id " + id + " no longer exists.");
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
            Computadora computadora;
            try {
                computadora = em.getReference(Computadora.class, id);
                computadora.getIdComputadora();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The computadora with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ItItem> itItemCollectionOrphanCheck = computadora.getItItemCollection();
            for (ItItem itItemCollectionOrphanCheckItItem : itItemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Computadora (" + computadora + ") cannot be destroyed since the ItItem " + itItemCollectionOrphanCheckItItem + " in its itItemCollection field has a non-nullable computadoraidComputadora field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Servidor servidoridServidor = computadora.getServidoridServidor();
            if (servidoridServidor != null) {
                servidoridServidor.getComputadoraCollection().remove(computadora);
                servidoridServidor = em.merge(servidoridServidor);
            }
            Laptops laptopsidLaptop = computadora.getLaptopsidLaptop();
            if (laptopsidLaptop != null) {
                laptopsidLaptop.getComputadoraCollection().remove(computadora);
                laptopsidLaptop = em.merge(laptopsidLaptop);
            }
            Workstation workstationidWorkstation = computadora.getWorkstationidWorkstation();
            if (workstationidWorkstation != null) {
                workstationidWorkstation.getComputadoraCollection().remove(computadora);
                workstationidWorkstation = em.merge(workstationidWorkstation);
            }
            Collection<Software> softwareCollection = computadora.getSoftwareCollection();
            for (Software softwareCollectionSoftware : softwareCollection) {
                softwareCollectionSoftware.getComputadoraCollection().remove(computadora);
                softwareCollectionSoftware = em.merge(softwareCollectionSoftware);
            }
            em.remove(computadora);
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

    public List<Computadora> findComputadoraEntities() {
        return findComputadoraEntities(true, -1, -1);
    }

    public List<Computadora> findComputadoraEntities(int maxResults, int firstResult) {
        return findComputadoraEntities(false, maxResults, firstResult);
    }

    private List<Computadora> findComputadoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Computadora.class));
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

    public Computadora findComputadora(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Computadora.class, id);
        } finally {
            em.close();
        }
    }

    public int getComputadoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Computadora> rt = cq.from(Computadora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
