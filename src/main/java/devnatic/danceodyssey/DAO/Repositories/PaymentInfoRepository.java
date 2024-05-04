package devnatic.danceodyssey.DAO.Repositories;


import devnatic.danceodyssey.DAO.Entities.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
}

