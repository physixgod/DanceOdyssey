package devnatic.danceodyssey.Interfaces;

import devnatic.danceodyssey.DAO.Entities.Delivery;
import devnatic.danceodyssey.DAO.Entities.OrderLine;
import devnatic.danceodyssey.DAO.Entities.Products;

import java.util.List;
import java.util.Set;

public interface IOrderLineService {

    public OrderLine  addProductToOrderAndCreateCart(Integer productId, Integer nbrProduct, Integer cartId);
    public List<OrderLine> getOrderLinesWithNullOrderLineId();
    public OrderLine  removeOrderLine(Integer orderLineId, Integer cartId);
    public OrderLine updateOrderLineQuantity(Integer orderLineId, Integer newQuantity);
}

