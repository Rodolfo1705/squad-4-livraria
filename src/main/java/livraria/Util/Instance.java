package Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Instance {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("livrariavirtual");
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    public static int totalImpressos(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String hql = "SELECT COUNT(*) FROM Impresso";
        Query query = entityManager.createQuery(hql);
        int count = ((Number) query.getSingleResult()).intValue();
        entityManager.close();
        return count;
    }

    public static int totalEletronico(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String hql = "SELECT COUNT(*) FROM Eletronico";
        Query query = entityManager.createQuery(hql);
        int count = ((Number) query.getSingleResult()).intValue();
        entityManager.close();
        return count;
    }
}