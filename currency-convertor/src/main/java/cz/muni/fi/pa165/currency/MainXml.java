package cz.muni.fi.pa165.currency;

import org.springframework.context.*;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Currency;

public class MainXml {
    public static void main(String args[]) {
        ApplicationContext context =
                new FileSystemXmlApplicationContext("//home/xzvonik/PA165/currency-convertor/src/main/java/cz/muni/fi/pa165/currency/spring.xml");
        CurrencyConvertor conv = (CurrencyConvertor) context.getBean("currencyConverter");
        System.out.println(conv.convert(Currency.getInstance("EUR"),
                Currency.getInstance("CZK"), new java.math.BigDecimal(1)));
    }
}
