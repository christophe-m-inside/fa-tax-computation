/*
 * Copyright (C) 2014 Christophe Martel <mail.christophe.martel@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cma.fa.tc.impl.business.entity.product;


import cma.fa.tc.def.business.entity.Price;
import cma.fa.tc.def.business.entity.PricedProduct;
import java.util.Set;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import cma.fa.tc.def.business.entity.Tax;
import cma.fa.tc.impl.business.entity.TcPricedProduct;
import java.time.LocalDateTime;



/**
 *
 * @author Christophe Martel <mail.christophe.martel@gmail.com>
 */
@EqualsAndHashCode(of={"createdAt"}, callSuper=true)
@ToString
@Slf4j
public class FinalProduct extends TcPricedProduct {
    
    @Accessors(chain = true, fluent = true)
    @Getter
    private final LocalDateTime createdAt;
    
    public FinalProduct (
            String code,
            String label,
            float unitPrice,
            Set<Tax> taxes,
            Price price,
            LocalDateTime createdAt) {
        super(
            code,
            label,
            unitPrice,
            taxes/*
                .stream()
                .map(t -> new FinalTax((Tax) t, createdAt))
                .collect(Collectors.toSet())*/,
            price);
        this.createdAt = createdAt;
    }
    
    public FinalProduct (
            PricedProduct product,
            LocalDateTime createdAt) {
        this(
            product.code(),
            product.label(),
            product.unitPrice(),
            product.taxes(),
            product.price(),
            createdAt);
        
    }
    
    public FinalProduct (PricedProduct product) {
        this(
            product.code(),
            product.label(),
            product.unitPrice(),
            product.taxes(),
            product.price(),
            LocalDateTime.now());
        
    }

}
