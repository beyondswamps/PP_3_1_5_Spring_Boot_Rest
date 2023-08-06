package ru.kata.spring.boot_security.demo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;


    public UserDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveUser(User user) {
        entityManager
                .persist(user);
    }

    @Override
    public List<User> getUsers() {
        return entityManager
                .createQuery("from User", User.class)
                .getResultList();
    }

    @Override
    public User getUser(Long id) {
        return entityManager
                .find(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> maybeUser = entityManager
                .createQuery("from User user join fetch user.roles where user.email=:email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();

        return maybeUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void updateUser(User user) {
        entityManager
                .merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager
                .createQuery("delete from User where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
