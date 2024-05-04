package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.DAO.Entities.CART;
import devnatic.danceodyssey.DAO.Entities.Competition;
import devnatic.danceodyssey.DAO.Entities.Products;
import devnatic.danceodyssey.DAO.Entities.Test;
import devnatic.danceodyssey.DAO.Repositories.CartRepository;
import devnatic.danceodyssey.DAO.Repositories.CompetitionRepository;
import devnatic.danceodyssey.DAO.Repositories.ProductRepository;
import devnatic.danceodyssey.DAO.Repositories.TestRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class test {
    ProductRepository productRepository;
    CompetitionRepository competitionRepository;
    @GetMapping("GetAll")
    public List<Products> getProdcuts(){
        return productRepository.findAll();
    }
    @GetMapping("getcompetitionss")
    public List<Competition> GETCOMPETITIONS(){
        return competitionRepository.findAll();
    }
    CartRepository cartRepository;
    @GetMapping("getcarts")
    public List<CART> getcarts(){
        return cartRepository.findAll();
    }
    TestRepo testRepo;
    @GetMapping("get")
    public List<Test> getTest(){
        return testRepo.findAll();
    }
}

