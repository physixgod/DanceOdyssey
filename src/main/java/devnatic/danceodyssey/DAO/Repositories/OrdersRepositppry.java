package devnatic.danceodyssey.DAO.Repositories;

import devnatic.danceodyssey.DAO.Entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepositppry  extends JpaRepository<Orders,Integer> {
    @Query("SELECT c FROM Orders c where c.buyer_address = :region and c.etat=:WAITING ")
    public List<Orders> getnotaffectedCommand(@Param("region") String region);

}
