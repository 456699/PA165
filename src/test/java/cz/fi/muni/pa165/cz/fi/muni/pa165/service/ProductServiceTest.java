package cz.fi.muni.pa165.cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Price;
import cz.fi.muni.pa165.enums.Currency;
import cz.fi.muni.pa165.service.ProductService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
public class ProductServiceTest extends AbstractTestNGSpringContextTests
{
    @Mock
    private ProductDao productDao;

    @Autowired
    @InjectMocks
    private ProductService productService;

    @BeforeClass
    public void setup() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void changeCurrencyTest(){
        Price price = new Price();
        price.setCurrency(Currency.CZK);
        price.setValue(new BigDecimal(27));
        productService.convertToCurrency(price, Currency.CZK);
        Assert.assertTrue(price.getValue().compareTo(BigDecimal.valueOf(27, 0)) == 0);
        productService.convertToCurrency(price, Currency.EUR);
        Assert.assertTrue(price.getValue().compareTo(BigDecimal.valueOf(108, 2)) == 0);
    }
}
