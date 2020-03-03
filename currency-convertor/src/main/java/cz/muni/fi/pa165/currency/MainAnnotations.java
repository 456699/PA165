package cz.muni.fi.pa165.currency;

import org.springframework.context.annotation.*;

import java.util.Currency;

public class MainAnnotations {
    public static void main(String args[]) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ExchangeRateTableImpl.class);
        context.register(CurrencyConvertorImpl.class);
        context.refresh();
        CurrencyConvertor conv = (CurrencyConvertor) context.getBean(CurrencyConvertorImpl.class);
        System.out.println(conv.convert(Currency.getInstance("EUR"),
                Currency.getInstance("CZK"), new java.math.BigDecimal(1)));
    }
}
