package devnatic.danceodyssey.Services;

import devnatic.danceodyssey.DAO.Repositories.CatalogueRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class Catalogue_Service implements  ICatalogue_Service {
    private  final CatalogueRepository catalogueRepository;
}
