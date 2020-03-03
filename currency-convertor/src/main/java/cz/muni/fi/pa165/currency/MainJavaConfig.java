package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Currency;

public class MainJavaConfig {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
        CurrencyConvertor cc = (CurrencyConvertor) ctx.getBean("currencyConvertor");
        System.out.println(cc.convert(Currency.getInstance("EUR"),
                Currency.getInstance("CZK"), new java.math.BigDecimal(1)));
    }
}
