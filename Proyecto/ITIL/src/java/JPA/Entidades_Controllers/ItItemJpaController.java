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
import JPA.Entidades.ItItem;
import JPA.Entidades.ItItemPK;
import java.util.ArrayList;
import java.util.Collection;
import JPA.Entidades.Servidor;
import JPA.Entidades.Software;
import JPA.Entidades.Telecommunications;
import JPA.Entidades.Perifericos;
import JPA.Entidades.Laptops;
import JPA.Entidades.Workstation;
import JPA.Entidades_Controllers.exceptions.IllegalOrphanException;
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
public class ItItemJpaController implements Serializable {

    public ItItemJpaController() {
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("It_ITILPU");
        return emf.createEntityManager();
    }

    public void create(ItItem itItem) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (itItem.getItItemPK() == null) {
            itItem.setItItemPK(new ItItemPK());
        }
        if (itItem.getEmpleadoCollection() == null) {
            itItem.setEmpleadoCollection(new ArrayList<Empleado>());
        }
        if (itItem.getServidorCollection() == null) {
            itItem.setServidorCollection(new ArrayList<Servidor>());
        }
        if (itItem.getSoftwareCollection() == null) {
            itItem.setSoftwareCollection(new ArrayList<Software>());
        }
        if (itItem.getTelecommunicationsCollection() == null) {
            itItem.setTelecommunicationsCollection(new ArrayList<Telecommunications>());
        }
        if (itItem.getPerifericosCollection() == null) {
            itItem.setPerifericosCollection(new ArrayList<Perifericos>());
        }
        if (itItem.getLaptopsCollection() == null) {
            itItem.setLaptopsCollection(new ArrayList<Laptops>());
        }
        if (itItem.getWorkstationCollection() == null) {
            itItem.setWorkstationCollection(new ArrayList<Workstation>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Empleado> attachedEmpleadoCollection = new ArrayList<Empleado>();
            for (Empleado empleadoCollectionEmpleadoToAttach : itItem.getEmpleadoCollection()) {
                empleadoCollectionEmpleadoToAttach = em.getReference(empleadoCollectionEmpleadoToAttach.getClass(), empleadoCollectionEmpleadoToAttach.getEmpNoEmpleado());
                attachedEmpleadoCollection.add(empleadoCollectionEmpleadoToAttach);
            }
            itItem.setEmpleadoCollection(attachedEmpleadoCollection);
            Collection<Servidor> attachedServidorCollection = new ArrayList<Servidor>();
            for (Servidor servidorCollectionServidorToAttach : itItem.getServidorCollection()) {
                servidorCollectionServidorToAttach = em.getReference(servidorCollectionServidorToAttach.getClass(), servidorCollectionServidorToAttach.getIdServidor());
                attachedServidorCollection.add(servidorCollectionServidorToAttach);
            }
            itItem.setServidorCollection(attachedServidorCollection);
            Collection<Software> attachedSoftwareCollection = new ArrayList<Software>();
            for (Software softwareCollectionSoftwareToAttach : itItem.getSoftwareCollection()) {
                softwareCollectionSoftwareToAttach = em.getReference(softwareCollectionSoftwareToAttach.getClass(), softwareCollectionSoftwareToAttach.getIdSoftware());
                attachedSoftwareCollection.add(softwareCollectionSoftwareToAttach);
            }
            itItem.setSoftwareCollection(attachedSoftwareCollection);
            Collection<Telecommunications> attachedTelecommunicationsCollection = new ArrayList<Telecommunications>();
            for (Telecommunications telecommunicationsCollectionTelecommunicationsToAttach : itItem.getTelecommunicationsCollection()) {
                telecommunicationsCollectionTelecommunicationsToAttach = em.getReference(telecommunicationsCollectionTelecommunicationsToAttach.getClass(), telecommunicationsCollectionTelecommunicationsToAttach.getIdTelecom());
                attachedTelecommunicationsCollection.add(telecommunicationsCollectionTelecommunicationsToAttach);
            }
            itItem.setTelecommunicationsCollection(attachedTelecommunicationsCollection);
            Collection<Perifericos> attachedPerifericosCollection = new ArrayList<Perifericos>();
            for (Perifericos perifericosCollectionPerifericosToAttach : itItem.getPerifericosCollection()) {
                perifericosCollectionPerifericosToAttach = em.getReference(perifericosCollectionPerifericosToAttach.getClass(), perifericosCollectionPerifericosToAttach.getIdPeriferico());
                attachedPerifericosCollection.add(perifericosCollectionPerifericosToAttach);
            }
            itItem.setPerifericosCollection(attachedPerifericosCollection);
            Collection<Laptops> attachedLaptopsCollection = new ArrayList<Laptops>();
            for (Laptops laptopsCollectionLaptopsToAttach : itItem.getLaptopsCollection()) {
                laptopsCollectionLaptopsToAttach = em.getReference(laptopsCollectionLaptopsToAttach.getClass(), laptopsCollectionLaptopsToAttach.getIdLaptop());
                attachedLaptopsCollection.add(laptopsCollectionLaptopsToAttach);
            }
            itItem.setLaptopsCollection(attachedLaptopsCollection);
            Collection<Workstation> attachedWorkstationCollection = new ArrayList<Workstation>();
            for (Workstation workstationCollectionWorkstationToAttach : itItem.getWorkstationCollection()) {
                workstationCollectionWorkstationToAttach = em.getReference(workstationCollectionWorkstationToAttach.getClass(), workstationCollectionWorkstationToAttach.getIdWorkstation());
                attachedWorkstationCollection.add(workstationCollectionWorkstationToAttach);
            }
            itItem.setWorkstationCollection(attachedWorkstationCollection);
            em.persist(itItem);
            for (Empleado empleadoCollectionEmpleado : itItem.getEmpleadoCollection()) {
                empleadoCollectionEmpleado.getItItemCollection().add(itItem);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
            }
            for (Servidor servidorCollectionServidor : itItem.getServidorCollection()) {
                ItItem oldItItemOfServidorCollectionServidor = servidorCollectionServidor.getItItem();
                servidorCollectionServidor.setItItem(itItem);
                servidorCollectionServidor = em.merge(servidorCollectionServidor);
                if (oldItItemOfServidorCollectionServidor != null) {
                    oldItItemOfServidorCollectionServidor.getServidorCollection().remove(servidorCollectionServidor);
                    oldItItemOfServidorCollectionServidor = em.merge(oldItItemOfServidorCollectionServidor);
                }
            }
            for (Software softwareCollectionSoftware : itItem.getSoftwareCollection()) {
                ItItem oldItItemOfSoftwareCollectionSoftware = softwareCollectionSoftware.getItItem();
                softwareCollectionSoftware.setItItem(itItem);
                softwareCollectionSoftware = em.merge(softwareCollectionSoftware);
                if (oldItItemOfSoftwareCollectionSoftware != null) {
                    oldItItemOfSoftwareCollectionSoftware.getSoftwareCollection().remove(softwareCollectionSoftware);
                    oldItItemOfSoftwareCollectionSoftware = em.merge(oldItItemOfSoftwareCollectionSoftware);
                }
            }
            for (Telecommunications telecommunicationsCollectionTelecommunications : itItem.getTelecommunicationsCollection()) {
                ItItem oldItItemOfTelecommunicationsCollectionTelecommunications = telecommunicationsCollectionTelecommunications.getItItem();
                telecommunicationsCollectionTelecommunications.setItItem(itItem);
                telecommunicationsCollectionTelecommunications = em.merge(telecommunicationsCollectionTelecommunications);
                if (oldItItemOfTelecommunicationsCollectionTelecommunications != null) {
                    oldItItemOfTelecommunicationsCollectionTelecommunications.getTelecommunicationsCollection().remove(telecommunicationsCollectionTelecommunications);
                    oldItItemOfTelecommunicationsCollectionTelecommunications = em.merge(oldItItemOfTelecommunicationsCollectionTelecommunications);
                }
            }
            for (Perifericos perifericosCollectionPerifericos : itItem.getPerifericosCollection()) {
                ItItem oldItItemOfPerifericosCollectionPerifericos = perifericosCollectionPerifericos.getItItem();
                perifericosCollectionPerifericos.setItItem(itItem);
                perifericosCollectionPerifericos = em.merge(perifericosCollectionPerifericos);
                if (oldItItemOfPerifericosCollectionPerifericos != null) {
                    oldItItemOfPerifericosCollectionPerifericos.getPerifericosCollection().remove(perifericosCollectionPerifericos);
                    oldItItemOfPerifericosCollectionPerifericos = em.merge(oldItItemOfPerifericosCollectionPerifericos);
                }
            }
            for (Laptops laptopsCollectionLaptops : itItem.getLaptopsCollection()) {
                ItItem oldItItemOfLaptopsCollectionLaptops = laptopsCollectionLaptops.getItItem();
                laptopsCollectionLaptops.setItItem(itItem);
                laptopsCollectionLaptops = em.merge(laptopsCollectionLaptops);
                if (oldItItemOfLaptopsCollectionLaptops != null) {
                    oldItItemOfLaptopsCollectionLaptops.getLaptopsCollection().remove(laptopsCollectionLaptops);
                    oldItItemOfLaptopsCollectionLaptops = em.merge(oldItItemOfLaptopsCollectionLaptops);
                }
            }
            for (Workstation workstationCollectionWorkstation : itItem.getWorkstationCollection()) {
                ItItem oldItItemOfWorkstationCollectionWorkstation = workstationCollectionWorkstation.getItItem();
                workstationCollectionWorkstation.setItItem(itItem);
                workstationCollectionWorkstation = em.merge(workstationCollectionWorkstation);
                if (oldItItemOfWorkstationCollectionWorkstation != null) {
                    oldItItemOfWorkstationCollectionWorkstation.getWorkstationCollection().remove(workstationCollectionWorkstation);
                    oldItItemOfWorkstationCollectionWorkstation = em.merge(oldItItemOfWorkstationCollectionWorkstation);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findItItem(itItem.getItItemPK()) != null) {
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
            ItItem persistentItItem = em.find(ItItem.class, itItem.getItItemPK());
            Collection<Empleado> empleadoCollectionOld = persistentItItem.getEmpleadoCollection();
            Collection<Empleado> empleadoCollectionNew = itItem.getEmpleadoCollection();
            Collection<Servidor> servidorCollectionOld = persistentItItem.getServidorCollection();
            Collection<Servidor> servidorCollectionNew = itItem.getServidorCollection();
            Collection<Software> softwareCollectionOld = persistentItItem.getSoftwareCollection();
            Collection<Software> softwareCollectionNew = itItem.getSoftwareCollection();
            Collection<Telecommunications> telecommunicationsCollectionOld = persistentItItem.getTelecommunicationsCollection();
            Collection<Telecommunications> telecommunicationsCollectionNew = itItem.getTelecommunicationsCollection();
            Collection<Perifericos> perifericosCollectionOld = persistentItItem.getPerifericosCollection();
            Collection<Perifericos> perifericosCollectionNew = itItem.getPerifericosCollection();
            Collection<Laptops> laptopsCollectionOld = persistentItItem.getLaptopsCollection();
            Collection<Laptops> laptopsCollectionNew = itItem.getLaptopsCollection();
            Collection<Workstation> workstationCollectionOld = persistentItItem.getWorkstationCollection();
            Collection<Workstation> workstationCollectionNew = itItem.getWorkstationCollection();
            List<String> illegalOrphanMessages = null;
            for (Servidor servidorCollectionOldServidor : servidorCollectionOld) {
                if (!servidorCollectionNew.contains(servidorCollectionOldServidor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Servidor " + servidorCollectionOldServidor + " since its itItem field is not nullable.");
                }
            }
            for (Software softwareCollectionOldSoftware : softwareCollectionOld) {
                if (!softwareCollectionNew.contains(softwareCollectionOldSoftware)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Software " + softwareCollectionOldSoftware + " since its itItem field is not nullable.");
                }
            }
            for (Telecommunications telecommunicationsCollectionOldTelecommunications : telecommunicationsCollectionOld) {
                if (!telecommunicationsCollectionNew.contains(telecommunicationsCollectionOldTelecommunications)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telecommunications " + telecommunicationsCollectionOldTelecommunications + " since its itItem field is not nullable.");
                }
            }
            for (Perifericos perifericosCollectionOldPerifericos : perifericosCollectionOld) {
                if (!perifericosCollectionNew.contains(perifericosCollectionOldPerifericos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Perifericos " + perifericosCollectionOldPerifericos + " since its itItem field is not nullable.");
                }
            }
            for (Laptops laptopsCollectionOldLaptops : laptopsCollectionOld) {
                if (!laptopsCollectionNew.contains(laptopsCollectionOldLaptops)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Laptops " + laptopsCollectionOldLaptops + " since its itItem field is not nullable.");
                }
            }
            for (Workstation workstationCollectionOldWorkstation : workstationCollectionOld) {
                if (!workstationCollectionNew.contains(workstationCollectionOldWorkstation)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Workstation " + workstationCollectionOldWorkstation + " since its itItem field is not nullable.");
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
            itItem.setEmpleadoCollection(empleadoCollectionNew);
            Collection<Servidor> attachedServidorCollectionNew = new ArrayList<Servidor>();
            for (Servidor servidorCollectionNewServidorToAttach : servidorCollectionNew) {
                servidorCollectionNewServidorToAttach = em.getReference(servidorCollectionNewServidorToAttach.getClass(), servidorCollectionNewServidorToAttach.getIdServidor());
                attachedServidorCollectionNew.add(servidorCollectionNewServidorToAttach);
            }
            servidorCollectionNew = attachedServidorCollectionNew;
            itItem.setServidorCollection(servidorCollectionNew);
            Collection<Software> attachedSoftwareCollectionNew = new ArrayList<Software>();
            for (Software softwareCollectionNewSoftwareToAttach : softwareCollectionNew) {
                softwareCollectionNewSoftwareToAttach = em.getReference(softwareCollectionNewSoftwareToAttach.getClass(), softwareCollectionNewSoftwareToAttach.getIdSoftware());
                attachedSoftwareCollectionNew.add(softwareCollectionNewSoftwareToAttach);
            }
            softwareCollectionNew = attachedSoftwareCollectionNew;
            itItem.setSoftwareCollection(softwareCollectionNew);
            Collection<Telecommunications> attachedTelecommunicationsCollectionNew = new ArrayList<Telecommunications>();
            for (Telecommunications telecommunicationsCollectionNewTelecommunicationsToAttach : telecommunicationsCollectionNew) {
                telecommunicationsCollectionNewTelecommunicationsToAttach = em.getReference(telecommunicationsCollectionNewTelecommunicationsToAttach.getClass(), telecommunicationsCollectionNewTelecommunicationsToAttach.getIdTelecom());
                attachedTelecommunicationsCollectionNew.add(telecommunicationsCollectionNewTelecommunicationsToAttach);
            }
            telecommunicationsCollectionNew = attachedTelecommunicationsCollectionNew;
            itItem.setTelecommunicationsCollection(telecommunicationsCollectionNew);
            Collection<Perifericos> attachedPerifericosCollectionNew = new ArrayList<Perifericos>();
            for (Perifericos perifericosCollectionNewPerifericosToAttach : perifericosCollectionNew) {
                perifericosCollectionNewPerifericosToAttach = em.getReference(perifericosCollectionNewPerifericosToAttach.getClass(), perifericosCollectionNewPerifericosToAttach.getIdPeriferico());
                attachedPerifericosCollectionNew.add(perifericosCollectionNewPerifericosToAttach);
            }
            perifericosCollectionNew = attachedPerifericosCollectionNew;
            itItem.setPerifericosCollection(perifericosCollectionNew);
            Collection<Laptops> attachedLaptopsCollectionNew = new ArrayList<Laptops>();
            for (Laptops laptopsCollectionNewLaptopsToAttach : laptopsCollectionNew) {
                laptopsCollectionNewLaptopsToAttach = em.getReference(laptopsCollectionNewLaptopsToAttach.getClass(), laptopsCollectionNewLaptopsToAttach.getIdLaptop());
                attachedLaptopsCollectionNew.add(laptopsCollectionNewLaptopsToAttach);
            }
            laptopsCollectionNew = attachedLaptopsCollectionNew;
            itItem.setLaptopsCollection(laptopsCollectionNew);
            Collection<Workstation> attachedWorkstationCollectionNew = new ArrayList<Workstation>();
            for (Workstation workstationCollectionNewWorkstationToAttach : workstationCollectionNew) {
                workstationCollectionNewWorkstationToAttach = em.getReference(workstationCollectionNewWorkstationToAttach.getClass(), workstationCollectionNewWorkstationToAttach.getIdWorkstation());
                attachedWorkstationCollectionNew.add(workstationCollectionNewWorkstationToAttach);
            }
            workstationCollectionNew = attachedWorkstationCollectionNew;
            itItem.setWorkstationCollection(workstationCollectionNew);
            itItem = em.merge(itItem);
            for (Empleado empleadoCollectionOldEmpleado : empleadoCollectionOld) {
                if (!empleadoCollectionNew.contains(empleadoCollectionOldEmpleado)) {
                    empleadoCollectionOldEmpleado.getItItemCollection().remove(itItem);
                    empleadoCollectionOldEmpleado = em.merge(empleadoCollectionOldEmpleado);
                }
            }
            for (Empleado empleadoCollectionNewEmpleado : empleadoCollectionNew) {
                if (!empleadoCollectionOld.contains(empleadoCollectionNewEmpleado)) {
                    empleadoCollectionNewEmpleado.getItItemCollection().add(itItem);
                    empleadoCollectionNewEmpleado = em.merge(empleadoCollectionNewEmpleado);
                }
            }
            for (Servidor servidorCollectionNewServidor : servidorCollectionNew) {
                if (!servidorCollectionOld.contains(servidorCollectionNewServidor)) {
                    ItItem oldItItemOfServidorCollectionNewServidor = servidorCollectionNewServidor.getItItem();
                    servidorCollectionNewServidor.setItItem(itItem);
                    servidorCollectionNewServidor = em.merge(servidorCollectionNewServidor);
                    if (oldItItemOfServidorCollectionNewServidor != null && !oldItItemOfServidorCollectionNewServidor.equals(itItem)) {
                        oldItItemOfServidorCollectionNewServidor.getServidorCollection().remove(servidorCollectionNewServidor);
                        oldItItemOfServidorCollectionNewServidor = em.merge(oldItItemOfServidorCollectionNewServidor);
                    }
                }
            }
            for (Software softwareCollectionNewSoftware : softwareCollectionNew) {
                if (!softwareCollectionOld.contains(softwareCollectionNewSoftware)) {
                    ItItem oldItItemOfSoftwareCollectionNewSoftware = softwareCollectionNewSoftware.getItItem();
                    softwareCollectionNewSoftware.setItItem(itItem);
                    softwareCollectionNewSoftware = em.merge(softwareCollectionNewSoftware);
                    if (oldItItemOfSoftwareCollectionNewSoftware != null && !oldItItemOfSoftwareCollectionNewSoftware.equals(itItem)) {
                        oldItItemOfSoftwareCollectionNewSoftware.getSoftwareCollection().remove(softwareCollectionNewSoftware);
                        oldItItemOfSoftwareCollectionNewSoftware = em.merge(oldItItemOfSoftwareCollectionNewSoftware);
                    }
                }
            }
            for (Telecommunications telecommunicationsCollectionNewTelecommunications : telecommunicationsCollectionNew) {
                if (!telecommunicationsCollectionOld.contains(telecommunicationsCollectionNewTelecommunications)) {
                    ItItem oldItItemOfTelecommunicationsCollectionNewTelecommunications = telecommunicationsCollectionNewTelecommunications.getItItem();
                    telecommunicationsCollectionNewTelecommunications.setItItem(itItem);
                    telecommunicationsCollectionNewTelecommunications = em.merge(telecommunicationsCollectionNewTelecommunications);
                    if (oldItItemOfTelecommunicationsCollectionNewTelecommunications != null && !oldItItemOfTelecommunicationsCollectionNewTelecommunications.equals(itItem)) {
                        oldItItemOfTelecommunicationsCollectionNewTelecommunications.getTelecommunicationsCollection().remove(telecommunicationsCollectionNewTelecommunications);
                        oldItItemOfTelecommunicationsCollectionNewTelecommunications = em.merge(oldItItemOfTelecommunicationsCollectionNewTelecommunications);
                    }
                }
            }
            for (Perifericos perifericosCollectionNewPerifericos : perifericosCollectionNew) {
                if (!perifericosCollectionOld.contains(perifericosCollectionNewPerifericos)) {
                    ItItem oldItItemOfPerifericosCollectionNewPerifericos = perifericosCollectionNewPerifericos.getItItem();
                    perifericosCollectionNewPerifericos.setItItem(itItem);
                    perifericosCollectionNewPerifericos = em.merge(perifericosCollectionNewPerifericos);
                    if (oldItItemOfPerifericosCollectionNewPerifericos != null && !oldItItemOfPerifericosCollectionNewPerifericos.equals(itItem)) {
                        oldItItemOfPerifericosCollectionNewPerifericos.getPerifericosCollection().remove(perifericosCollectionNewPerifericos);
                        oldItItemOfPerifericosCollectionNewPerifericos = em.merge(oldItItemOfPerifericosCollectionNewPerifericos);
                    }
                }
            }
            for (Laptops laptopsCollectionNewLaptops : laptopsCollectionNew) {
                if (!laptopsCollectionOld.contains(laptopsCollectionNewLaptops)) {
                    ItItem oldItItemOfLaptopsCollectionNewLaptops = laptopsCollectionNewLaptops.getItItem();
                    laptopsCollectionNewLaptops.setItItem(itItem);
                    laptopsCollectionNewLaptops = em.merge(laptopsCollectionNewLaptops);
                    if (oldItItemOfLaptopsCollectionNewLaptops != null && !oldItItemOfLaptopsCollectionNewLaptops.equals(itItem)) {
                        oldItItemOfLaptopsCollectionNewLaptops.getLaptopsCollection().remove(laptopsCollectionNewLaptops);
                        oldItItemOfLaptopsCollectionNewLaptops = em.merge(oldItItemOfLaptopsCollectionNewLaptops);
                    }
                }
            }
            for (Workstation workstationCollectionNewWorkstation : workstationCollectionNew) {
                if (!workstationCollectionOld.contains(workstationCollectionNewWorkstation)) {
                    ItItem oldItItemOfWorkstationCollectionNewWorkstation = workstationCollectionNewWorkstation.getItItem();
                    workstationCollectionNewWorkstation.setItItem(itItem);
                    workstationCollectionNewWorkstation = em.merge(workstationCollectionNewWorkstation);
                    if (oldItItemOfWorkstationCollectionNewWorkstation != null && !oldItItemOfWorkstationCollectionNewWorkstation.equals(itItem)) {
                        oldItItemOfWorkstationCollectionNewWorkstation.getWorkstationCollection().remove(workstationCollectionNewWorkstation);
                        oldItItemOfWorkstationCollectionNewWorkstation = em.merge(oldItItemOfWorkstationCollectionNewWorkstation);
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
                ItItemPK id = itItem.getItItemPK();
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

    public void destroy(ItItemPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItItem itItem;
            try {
                itItem = em.getReference(ItItem.class, id);
                itItem.getItItemPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itItem with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Servidor> servidorCollectionOrphanCheck = itItem.getServidorCollection();
            for (Servidor servidorCollectionOrphanCheckServidor : servidorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Servidor " + servidorCollectionOrphanCheckServidor + " in its servidorCollection field has a non-nullable itItem field.");
            }
            Collection<Software> softwareCollectionOrphanCheck = itItem.getSoftwareCollection();
            for (Software softwareCollectionOrphanCheckSoftware : softwareCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Software " + softwareCollectionOrphanCheckSoftware + " in its softwareCollection field has a non-nullable itItem field.");
            }
            Collection<Telecommunications> telecommunicationsCollectionOrphanCheck = itItem.getTelecommunicationsCollection();
            for (Telecommunications telecommunicationsCollectionOrphanCheckTelecommunications : telecommunicationsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Telecommunications " + telecommunicationsCollectionOrphanCheckTelecommunications + " in its telecommunicationsCollection field has a non-nullable itItem field.");
            }
            Collection<Perifericos> perifericosCollectionOrphanCheck = itItem.getPerifericosCollection();
            for (Perifericos perifericosCollectionOrphanCheckPerifericos : perifericosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Perifericos " + perifericosCollectionOrphanCheckPerifericos + " in its perifericosCollection field has a non-nullable itItem field.");
            }
            Collection<Laptops> laptopsCollectionOrphanCheck = itItem.getLaptopsCollection();
            for (Laptops laptopsCollectionOrphanCheckLaptops : laptopsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Laptops " + laptopsCollectionOrphanCheckLaptops + " in its laptopsCollection field has a non-nullable itItem field.");
            }
            Collection<Workstation> workstationCollectionOrphanCheck = itItem.getWorkstationCollection();
            for (Workstation workstationCollectionOrphanCheckWorkstation : workstationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ItItem (" + itItem + ") cannot be destroyed since the Workstation " + workstationCollectionOrphanCheckWorkstation + " in its workstationCollection field has a non-nullable itItem field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Empleado> empleadoCollection = itItem.getEmpleadoCollection();
            for (Empleado empleadoCollectionEmpleado : empleadoCollection) {
                empleadoCollectionEmpleado.getItItemCollection().remove(itItem);
                empleadoCollectionEmpleado = em.merge(empleadoCollectionEmpleado);
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

    public ItItem findItItem(ItItemPK id) {
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
