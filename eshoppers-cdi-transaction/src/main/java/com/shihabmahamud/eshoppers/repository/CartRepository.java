package com.shihabmahamud.eshoppers.repository;

import com.shihabmahamud.eshoppers.domain.Cart;
import com.shihabmahamud.eshoppers.domain.User;

public interface CartRepository {
    Cart findByUser(User currentUser);
    Cart save(Cart cart);
    void update(Cart cart);
    Cart findOne(long id);
}
