package com.shihabmahamud.eshoppers.repository;

import com.shihabmahamud.eshoppers.domain.CartItem;
import com.shihabmahamud.eshoppers.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class CartItemRepositoryImpl implements CartItemRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepositoryImpl.class);
    private final DataSource ds = ConnectionPool.getInstance().getDataSource();
    private static final Set<CartItem> CARTS = new CopyOnWriteArraySet<>();

    @Override
    public CartItem save(CartItem cartItem) {
        CARTS.add(cartItem);
        return cartItem;
    }

    @Override
    public CartItem update(CartItem cartItem) {
        CARTS.add(cartItem);
        return cartItem;
    }

    @Override
    public void remove(CartItem cartItem) {
        CARTS.remove(cartItem);
    }
}
