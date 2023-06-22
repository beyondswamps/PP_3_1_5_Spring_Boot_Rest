package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Role> getAllRoles() {
        return entityManager.
                createQuery("from Role", Role.class)
                .getResultList();
    }

    @Override
    public Role findByName(String name) {
        return entityManager
                .createQuery("from Role role where role.name=:name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        entityManager
                .persist(role);
    }
}
