package cz.muni.fi.pa165.currency;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Java6Assertions.*;

public class CurrencyConvertorImplTest {

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        ExchangeRateTable e = mock(ExchangeRateTable.class);
        when(e.getExchangeRate(
                Currency.getInstance(Locale.JAPAN),
                Currency.getInstance(Locale.US)))
                .thenReturn(new BigDecimal("0.2"));
        CurrencyConvertor c = new CurrencyConvertorImpl(e);
        BigDecimal b = c.convert(
                            Currency.getInstance(Locale.JAPAN),
                            Currency.getInstance(Locale.US),
                            new BigDecimal("2.4") );
        assertThat(b).isEqualTo("0.48");
        b = c.convert(
                Currency.getInstance(Locale.JAPAN),
                Currency.getInstance(Locale.US),
                new BigDecimal("9223372036854775807") );
        assertThat(b).isEqualTo("1844674407370955161.4");
    }

    @Test
    public void testConvertWithNullSourceCurrency() throws ExternalServiceFailureException {
        ExchangeRateTable e = mock(ExchangeRateTable.class);
        when(e.getExchangeRate(
                Currency.getInstance(Locale.JAPAN),
                Currency.getInstance(Locale.US)))
                .thenReturn(new BigDecimal("0.2"));
        CurrencyConvertor c = new CurrencyConvertorImpl(e);
        try {
            c.convert(null, Currency.getInstance(Locale.US), new BigDecimal("2"));
            org.junit.Assert.fail("Expected exception not thrown");
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testConvertWithNullTargetCurrency() throws ExternalServiceFailureException {
        ExchangeRateTable e = mock(ExchangeRateTable.class);
        when(e.getExchangeRate(
                Currency.getInstance(Locale.JAPAN),
                Currency.getInstance(Locale.US)))
                .thenReturn(new BigDecimal("0.2"));
        CurrencyConvertor c = new CurrencyConvertorImpl(e);
        try {
            c.convert(Currency.getInstance(Locale.US), null, new BigDecimal("2"));
            org.junit.Assert.fail("Expected exception not thrown");
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testConvertWithNullSourceAmount() throws ExternalServiceFailureException {
        ExchangeRateTable e = mock(ExchangeRateTable.class);
        when(e.getExchangeRate(
                Currency.getInstance(Locale.JAPAN),
                Currency.getInstance(Locale.US)))
                .thenReturn(new BigDecimal("0.2"));
        CurrencyConvertor c = new CurrencyConvertorImpl(e);
        try {
            c.convert(Currency.getInstance(Locale.US), Currency.getInstance(Locale.US), null);
            org.junit.Assert.fail("Expected exception not thrown");
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        ExchangeRateTable e = mock(ExchangeRateTable.class);
        when(e.getExchangeRate(
                Currency.getInstance(Locale.JAPAN),
                Currency.getInstance(Locale.US)))
                .thenReturn(null);
        CurrencyConvertor c = new CurrencyConvertorImpl(e);
        try {
            c.convert(Currency.getInstance(Locale.US), Currency.getInstance(Locale.US),
                      new BigDecimal("2"));
            org.junit.Assert.fail("Expected exception not thrown");
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(UnknownExchangeRateException.class);
        }
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        ExchangeRateTable e = mock(ExchangeRateTable.class);
        when(e.getExchangeRate(
                Currency.getInstance(Locale.JAPAN),
                Currency.getInstance(Locale.US)))
                .thenThrow(new ExternalServiceFailureException("Service Failure"));
        CurrencyConvertor c = new CurrencyConvertorImpl(e);
        try {
            c.convert(Currency.getInstance(Locale.JAPAN), Currency.getInstance(Locale.US),
                    new BigDecimal("2"));
            org.junit.Assert.fail("Expected exception not thrown");
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(UnknownExchangeRateException.class);
        }
    }

}
