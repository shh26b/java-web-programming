package com.shihabmahamud.eshoppers.web;

import com.shihabmahamud.eshoppers.domain.Cart;
import com.shihabmahamud.eshoppers.exceptions.ProductNotFoundException;
import com.shihabmahamud.eshoppers.repository.*;
import com.shihabmahamud.eshoppers.service.Action;
import com.shihabmahamud.eshoppers.service.CartService;
import com.shihabmahamud.eshoppers.service.CartServiceImpl;
import com.shihabmahamud.eshoppers.util.SecurityContext;
import com.shihabmahamud.eshoppers.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {

    private final CartService cartService = new CartServiceImpl(
            new CartRepositoryImpl(),
            new ProductRepositoryImpl(),
            new CartItemRepositoryImpl());
    private final static Logger LOGGER = LoggerFactory.getLogger(HomeServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var productId = req.getParameter("productId");
        var action = req.getParameter("action");
        var cart = getCart(req);

        if (StringUtil.isNotEmpty(action)) {
            try {
                processCart(productId, action, cart);
            } catch (ProductNotFoundException e) {
                LOGGER.error(String.valueOf(e));
            }

            resp.sendRedirect("/checkout");
            return;
        }

        LOGGER.info("Received request to add product with id: {} to cart", productId);
        try {
            cartService.addProductToCart(productId, cart);
        } catch (ProductNotFoundException e) {
            LOGGER.error(String.valueOf(e));
        }
        resp.sendRedirect("/home");
    }

    private void processCart(String productId, String action, Cart cart) throws ProductNotFoundException {
        switch (Action.valueOf(action.toUpperCase())) {
            case ADD -> {
                LOGGER.info("Received request to add product with id: {} to cart", productId);
                cartService.addProductToCart(productId, cart);
            }
            case REMOVE -> {
                LOGGER.info("Received request to remove product with id: {} to cart", productId);
                cartService.removeProductToCart(productId, cart);
            }
            case TOTALLY_REMOVE -> {
                LOGGER.info("Received request to add product with id: {} to cart", productId);
                cartService.removeProductTotallyToCart(productId, cart);
            }
        }
    }

    private Cart getCart(HttpServletRequest req) {
        final var currentUser = SecurityContext.getCurrentUser(req);
        return cartService.getCartByUser(currentUser);
    }
}