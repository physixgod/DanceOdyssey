package devnatic.danceodyssey.Interfaces;

import com.stripe.exception.StripeException;

public interface IStripeSerivce {
    public void effectuerPaiement(Long montant, String devise ) throws StripeException;
}
