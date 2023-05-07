package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("rpgPu");
        EntityManager em = createBegin(factory);
        addData(em);
        comitClose(em);

        Scanner scan = new Scanner(System.in);
        boolean isWorking = true;

        while(isWorking) {
            em = createBegin(factory);
            switch (scan.nextLine()) {
                case "add mage" -> addMage(em);
                case "add tower" -> addTower(em);
                case "delete mage" -> deleteMage(em);
                case "delete tower" -> deleteTower(em);
                case "query" -> sampleQuery(em);
                case "end", "stop", ".", "quit", "q" -> isWorking = false;
                default -> printDB(em);
            }
            comitClose(em);
        }
        factory.close();
    }

    private static EntityManager createBegin(EntityManagerFactory factory) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        return em;
    }

    private static void comitClose(EntityManager em) {
        em.getTransaction().commit();
        em.close();
    }

    private static void printDB(EntityManager em) {
        Query query = em.createNamedQuery("Mage.selectAll");
        for (Object m : query.getResultList())
            System.out.println(m);

        query = em.createNamedQuery("Tower.selectAll");
        for (Object t : query.getResultList())
            System.out.println(t);
    }

    private static void addData(EntityManager em) {
        Tower dalaran = new Tower("Dalaran", 50);
        em.persist(dalaran);
        Tower karazhan = new Tower("Karazhan", 35);
        em.persist(karazhan);
        Tower netherspite = new Tower("Netherspite", 30);
        em.persist(netherspite);

        em.persist(new Mage("Khadgar", 30, dalaran));
        em.persist(new Mage("Jaina", 50, dalaran));
        em.persist(new Mage("Medivh", 45, karazhan));
        em.persist(new Mage("Aegwynn", 50, netherspite));
        em.persist(new Mage("Guldan", 45, netherspite));
    }

    private static void addTower(EntityManager em) {
        Scanner scan = new Scanner(System.in);
        System.out.println("name:");
        String name = scan.next();
        System.out.println("height:");
        int height = scan.nextInt();
        em.persist(new Tower(name, height));
    }

    private static void addMage(EntityManager em) {
        Scanner scan = new Scanner(System.in);
        System.out.println("name:");
        String name = scan.next();
        System.out.println("level:");
        int level = scan.nextInt();
        System.out.println("tower name:");
        String tower_name = scan.next();
        Tower tower = em.getReference(Tower.class, tower_name);
        em.persist(new Mage(name, level, tower));
    }

    private static void deleteTower(EntityManager em) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Write name of tower:");
        String name = scan.next();
        Tower tower = em.getReference(Tower.class, name);
        em.remove(tower);
    }
    private static void deleteMage(EntityManager em) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Write name of mage:");
        String name = scan.next();
        Mage mage = em.getReference(Mage.class, name);
        em.remove(mage);
    }

    private static void sampleQuery(EntityManager em) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Give level:");
        int level = scan.nextInt();
        String queryString = "SELECT p FROM Mage p WHERE p.level > " + level + " ORDER BY p.name";
        Query query = em.createQuery(queryString, Mage.class);
        List<Mage> mages = query.getResultList();
        for (Mage m : mages) {
            System.out.println(m);
        }
    }
}