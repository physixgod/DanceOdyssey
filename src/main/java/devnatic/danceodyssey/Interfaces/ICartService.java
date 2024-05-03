package devnatic.danceodyssey.Interfaces;

import devnatic.danceodyssey.DAO.Entities.CART;
import devnatic.danceodyssey.DAO.Entities.User;

public interface ICartService {
    CART getCartById(Integer cartId);
    public User AssignUserToCart(User user);
}
