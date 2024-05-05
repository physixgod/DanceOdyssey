package devnatic.danceodyssey.Controller;

import devnatic.danceodyssey.Interfaces.IDeliveryService;
import devnatic.danceodyssey.Interfaces.IOrdersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
@Slf4j
public class DeliveryController {
    private  final IDeliveryService iDeliveryService;
    private  final IOrdersService iOrdersService;

}
