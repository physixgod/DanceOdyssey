package devnatic.danceodyssey.Services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import devnatic.danceodyssey.Interfaces.IStripeSerivce;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService implements IStripeSerivce {
    public StripeService() {
        Stripe.apiKey="sk_test_51P83aYP4ZqaIVp4bGqs8OskBFJK8u0qJtK2pXQygtcmWuCHkCzl1ZssipYqbLF0hzggpXPCJSe1rvpsB2qDan32C00OEKAcdbY" ;
    }


    @Override
    public void effectuerPaiement(Long montant, String devise) throws StripeException {

        Map<String, Object> params = new HashMap<>();
        params.put("amount", montant);
        params.put("currency", devise);

        Charge.create(params);
    }}
