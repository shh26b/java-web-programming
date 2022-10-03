package com.shihabmahamud.eshoppers.repository;

import com.shihabmahamud.eshoppers.domain.User;
import com.shihabmahamud.eshoppers.web.HomeServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserRepositoryImpl implements UserRepository{
    private static final Set<User> USERS = new CopyOnWriteArraySet<>();
    private final static Logger LOGGER = LoggerFactory.getLogger(HomeServlet.class);
    @Override
    public void save(User user) {
        USERS.add(user);
        LOGGER.debug(USERS.toString());
    }

    @Override
    public User findOneByUsername(String username) {
        for (var user : USERS) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        return null;
    }
}
