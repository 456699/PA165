package cz.muni.fi.pa165.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class JavaConfig {
    @Bean(name="exchangeRate")
    public ExchangeRateTable getExchangeRate() {
        return new ExchangeRateTableImpl();
    }

    @Bean(name="currencyConvertor")
    @Autowired
    public CurrencyConvertor getCC(ExchangeRateTable exchangeRateTable) {
        return new CurrencyConvertorImpl(exchangeRateTable);
    }
}
