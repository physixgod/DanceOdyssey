package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Entities.CART;
import devnatic.danceodyssey.DAO.Entities.User;
import devnatic.danceodyssey.DAO.Repositories.CartRepository;
import devnatic.danceodyssey.DAO.Repositories.TestUserRepo;
import devnatic.danceodyssey.Interfaces.ICartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CartService implements ICartService {
    private  final CartRepository cartRepository;
    private  final TestUserRepo testUserRepo;
    @Override
    public CART getCartById(Integer cartId) {
       return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
}

    @Override
    public User AssignUserToCart(User user) {
        // Votre logique pour enregistrer l'utilisateur
        User savedUser = testUserRepo.save(user);

        // Créer un nouveau panier pour l'utilisateur
        CART cart = CART.builder().user(savedUser).build();
        cartRepository.save(cart);

        // Associez le panier à l'utilisateur
        savedUser.setCart(cart);

        return savedUser;
    }
}
