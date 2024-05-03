package devnatic.danceodyssey.Interfaces;

import devnatic.danceodyssey.DAO.Entities.CART;

public interface ICartService {
    CART getCartById(Integer cartId);

}
