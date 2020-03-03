package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import javax.inject.Named;

@Named
public class ExchangeRateTableImpl implements ExchangeRateTable {
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency)
            throws ExternalServiceFailureException {
        if( sourceCurrency == Currency.getInstance("EUR") &&
            targetCurrency == Currency.getInstance("CZK") ) {
            return new BigDecimal(27);
        }
        return null;
    }
}
