package com.ShopApp.AppZone.service;



import com.ShopApp.AppZone.model.Sales;
import com.ShopApp.AppZone.model.SalesItem;

import com.ShopApp.AppZone.model.SalesItemDTO;
import com.ShopApp.AppZone.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;

    // Generate unique alpha-numeric sales ID
    public String generateSalesId() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long countToday = salesRepository.countBySalesDate(LocalDate.now());
        String sequencePart = String.format("%03d", countToday + 1);
        return "S" + datePart + sequencePart;
    }

    // Save Sale
    public Sales saveSale(String salesId, String customerId, List<SalesItemDTO> itemsDto,
                          BigDecimal cash, BigDecimal upi, BigDecimal credit) {
        Sales sale = new Sales();
        sale.setSalesId(salesId);
        sale.setCustomerId(customerId);
        sale.setSalesDate(LocalDate.now());

        List<SalesItem> items = new ArrayList<>();
        BigDecimal originalTotal = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (SalesItemDTO dto : itemsDto) {
            SalesItem item = new SalesItem();
            item.setSales(sale);
            item.setParticulars(dto.getParticulars());
            item.setQuantity(dto.getQuantity());
            item.setSellingCost(dto.getSellingCost());
            item.setTotal(dto.getTotal());

            BigDecimal itemTotal = dto.getTotal();
            BigDecimal itemDiscount = dto.getSellingCost().multiply(BigDecimal.valueOf(dto.getQuantity())).subtract(itemTotal);

            originalTotal = originalTotal.add(dto.getSellingCost().multiply(BigDecimal.valueOf(dto.getQuantity())));
            totalDiscount = totalDiscount.add(itemDiscount);

            items.add(item);
        }

        sale.setItems(items);
        sale.setOriginalTotal(originalTotal);
        sale.setTotalDiscount(totalDiscount);

        BigDecimal grandTotal = originalTotal.subtract(totalDiscount);
        sale.setGrandTotal(grandTotal);
        sale.setRoundedTotal(roundedTotal(grandTotal));

        sale.setCash(cash != null ? cash : BigDecimal.ZERO);
        sale.setUpi(upi != null ? upi : BigDecimal.ZERO);
        sale.setCredit(credit != null ? credit : BigDecimal.ZERO);

        return salesRepository.save(sale);
    }





    public Sales getSaleById(String salesId) {
        return salesRepository.findById(salesId).orElse(null);
    }

    public Sales updatePayments(String salesId, BigDecimal cash, BigDecimal upi, BigDecimal credit) {
        Sales sale = salesRepository.findById(salesId).orElse(null);
        if (sale == null) return null;

        BigDecimal totalPaid = (cash != null ? cash : BigDecimal.ZERO)
                .add(upi != null ? upi : BigDecimal.ZERO)
                .add(credit != null ? credit : BigDecimal.ZERO);

        sale.setCash(cash);
        sale.setUpi(upi);
        sale.setCredit(credit);
        sale.setGrandTotal(totalPaid);
        sale.setTotalDiscount(sale.getOriginalTotal().subtract(totalPaid));
        sale.setRoundedTotal(roundedTotal(totalPaid));

        return salesRepository.save(sale);
    }








    private BigDecimal roundedTotal(BigDecimal total) {
        int lastDigit = total.intValue() % 10;
        if (lastDigit <= 2) return total.subtract(BigDecimal.valueOf(lastDigit));
        if (lastDigit <= 7) return total.subtract(BigDecimal.valueOf(lastDigit)).add(BigDecimal.valueOf(5));
        return total.subtract(BigDecimal.valueOf(lastDigit)).add(BigDecimal.valueOf(10));
    }
}
