package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    //private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        if( sourceCurrency == null || targetCurrency == null || sourceAmount == null )
            throw new IllegalArgumentException("One of the arguments is NULL");

        Logger logger = Logger.getLogger(CurrencyConvertorImpl.class);
        PropertyConfigurator.configure("log4j.properties");
        logger.trace(String.format("Call to convert with source '%s', target '%s' and amount %s",
                sourceCurrency.toString(), targetCurrency.toString(),
                sourceAmount.toString()));

        BigDecimal conversion;
        try {
            conversion = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
        } catch( ExternalServiceFailureException ex ) {
            logger.error("External service failure");
            throw new UnknownExchangeRateException(ex.getMessage());
        }
        if( conversion == null ) {
            logger.warn("Unknown currency");
            throw new UnknownExchangeRateException("Unknown currency");
        }

        return sourceAmount.multiply(conversion);
    }

}
