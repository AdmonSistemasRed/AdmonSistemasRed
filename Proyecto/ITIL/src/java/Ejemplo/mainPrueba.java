package Ejemplo;


import JPA.Entidades.Area;
import JPA.Entidades.Lenguaje;
import JPA.Entidades_Controllers.AreaJpaController;
import JPA.Entidades_Controllers.LenguajeJpaController;
import JPA.Entidades_Controllers.exceptions.RollbackFailureException;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author madman
 */
public class mainPrueba {
    public static void main(String[] args) throws RollbackFailureException, Exception{
        
        
        LenguajeJpaController LC=new LenguajeJpaController();// declaramos un controlador de la entidad Lenguaje
        //hacemos un select de todos los lenguajes
        List<Lenguaje> AllLenguajes = LC.findLenguajeEntities();

         //los imprimimos
        System.out.println("------antes de insetar------");
        System.out.println("Hay "+LC.getLenguajeCount() +" lenguajes");
        for (int i = 0; i < AllLenguajes.size(); i++) {
            System.out.println(AllLenguajes.get(i).getLenLenguaje()); 
        }
        
        //insertamos un lenguaje
         Lenguaje l=new Lenguaje();
        l.setLenLenguaje("Esp");
        l.setLenNombre("Espanol");
        LC.create(l);
        
        System.out.println();
        System.out.println();
        
         //volvemos a imprimir
         AllLenguajes = LC.findLenguajeEntities();//recuperamos todos
        System.out.println("------despues de insetar------");
        System.out.println("Hay "+LC.getLenguajeCount() +" lenguajes");
        for (int i = 0; i < AllLenguajes.size(); i++) {
            System.out.println(AllLenguajes.get(i).getLenLenguaje() +"  "+ AllLenguajes.get(i).getLenNombre()); 
        }
        
        
        //buscamos un lenguaje en especifico
        Lenguaje findLenguaje = LC.findLenguaje("Esp");
        //System.out.println(findLenguaje.getLenLenguaje());
        
        
        //Editamos un leguaje
        l.setLenNombre("Italiano");
        LC.edit(l);
        
        System.out.println();
        System.out.println();
        //imprimimos otra vez
        AllLenguajes = LC.findLenguajeEntities();//recuperamos todos
        System.out.println("------despues de Editar------");
        System.out.println("Hay "+LC.getLenguajeCount() +" lenguajes");
        for (int i = 0; i < AllLenguajes.size(); i++) {
            System.out.println(AllLenguajes.get(i).getLenLenguaje() +"  "+ AllLenguajes.get(i).getLenNombre()); 
        }
        
        //Eliminamos
       LC.destroy("Esp");
        
        //contamos cuantos lenguajes tenemos
        System.out.println();
        System.out.println();
        System.out.println("Hay "+LC.getLenguajeCount() +" lenguajes");
    }
}