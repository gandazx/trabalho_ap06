package br.edu.idp.tech.poo.dao;

import br.edu.idp.tech.poo.entidade.Carro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class CarroJpaDao implements CarroDao {

    private EntityManager entityManager;

    public CarroJpaDao() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bd_carros");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void salvar(Carro carro) {
        entityManager.getTransaction().begin();
        entityManager.persist(carro);
        entityManager.getTransaction().commit();
    }

    @Override
    public void salvar(List<Carro> novosCarros) {
        for (Carro carro : novosCarros) {
            this.salvar(carro);
        }
    }

    @Override
    public List<Carro> findAll() {
        return entityManager.createQuery("from Carro", Carro.class).getResultList();
    }
}

