package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.CART;
import devnatic.danceodyssey.DAO.Entities.OrderLine;
import devnatic.danceodyssey.DAO.Entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface OrderLineRepository extends JpaRepository<OrderLine,Integer > {
    List<OrderLine> findByOrdersIsNull();
    List<OrderLine> findByCart(CART cart);
    List<OrderLine> findByOrdersOrdersId(Integer orderId);
    List<OrderLine> findByCartId(Integer cartId);

}
