/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cma.fa.tc.impl.business.builder.product;

import cma.fa.tc.def.business.entity.Price;
import cma.fa.tc.def.business.entity.Product;
import cma.fa.tc.def.business.entity.Tax;
import cma.fa.tc.impl.business.entity.product.PricedProduct;
import cma.fa.tc.impl.business.service.calculator.price.TcProductPriceCalculator;
import cma.fa.tc.impl.business.service.repository.SimpleProducts;
import cma.fa.tc.impl.business.service.repository.Taxes;
import cma.fa.tc.impl.utils.files.SimpleCsvReader;
import cma.fa.tc.impl.utils.math.NearestRound;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author christophe
 */
@Slf4j
public class PricedBuilderTest extends TestCase {
    
    public PricedBuilderTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of start method, of class PricedBuilder.
     */
    public void testAll () {
        
        SimpleProducts products = new SimpleProducts(
            new SimpleCsvReader("/data/products.csv"),
            new SimpleBuilder(new Taxes(new SimpleCsvReader("/data/taxes.csv"))));
        
        PricedBuilder builder = new PricedBuilder(
            new TcProductPriceCalculator(new NearestRound(0.05)));
        
        Stream.of(
                "BOOK1:12.49f",
                "CD1:16.49f",
                "FOOD1:0.85f",
                "FOOD2:10.5f",
                "FOOD3:11.85f",
                "COSM1:54.65f",
                "COSM2:32.19f",
                "COSM3:20.89f",
                "DRUG1:9.75f")
            .map(str -> {
                String[] parts = str.split(":", 2);
                return new AbstractMap.SimpleEntry<String, Float>(parts[0], Float.parseFloat(parts[1]));
            })
            .collect(Collectors.toMap(
                map -> map.getKey(),
                map -> map.getValue()))
            .entrySet()
            .forEach(e -> assertEquals(
                e.getValue(),
                builder.product(products.byCode(e.getKey())).build().price().includingTaxes()))
        ;
        
    }
    
    
    
}
