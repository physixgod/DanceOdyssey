package devnatic.danceodyssey.Interfaces;

import devnatic.danceodyssey.DAO.Entities.Delivery;
import devnatic.danceodyssey.DAO.Entities.OrderLine;
import devnatic.danceodyssey.DAO.Entities.Orders;

import java.util.List;

public interface IOrdersService {
    List<Orders> Get_AllOrders(Integer idcart);
    public List<Orders> Get_AllOrders();
    void Accept_Orders(Integer idorders);

    void Refuse_orders(Integer idorders);
    void Cancel_orders(Integer idorders) ;
    public Delivery affectcamandtolivaison(String Region, Delivery l) ;
    public Orders Confirm_Order(Orders orders, Integer idCart) ;
    public List<OrderLine> getOrderLinesByOrderId(Integer orderId);
    public List<String> getProductNamesByCartId(Integer cartId);

}
