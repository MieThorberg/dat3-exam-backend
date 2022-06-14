/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dtos.RentalDTO;
import entities.House;
import entities.Rental;
import entities.Tenant;
import utils.EMF_Creator;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
//        EntityManager em = emf.createEntityManager();
//
////        em.getTransaction().begin();
//
//        House h = new House("h1", "h", 1);
//        Tenant t = new Tenant("t1", "t", "t");
//        t.setId(1);
//        h.setId(1);
////        em.persist(h);
////        em.persist(t);
//
//        Set<Tenant> tenants = new HashSet<>();
//        tenants.add(t);
//        Rental rental = new Rental("r", "r", 1, 1, "r", h, tenants);
//
//        RentalFacade facade = RentalFacade.getRentalFacade(emf);
//        RentalDTO rentalDTO = new RentalDTO(rental);
//        facade.create(rentalDTO);
////        em.getTransaction().commit();
        RentalFacade facade = RentalFacade.getRentalFacade(emf);
        System.out.println(facade.getTenantsFromHouseById(1));
    }

    public static void main(String[] args) {
        populate();
    }
}