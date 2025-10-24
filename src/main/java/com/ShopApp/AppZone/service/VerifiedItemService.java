package com.ShopApp.AppZone.service;

import com.ShopApp.AppZone.model.ItemClassification;
import com.ShopApp.AppZone.model.PurchaseItem;
import com.ShopApp.AppZone.model.VerifiedItem;
import com.ShopApp.AppZone.repository.VerifiedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;



@Service
public class VerifiedItemService {

    @Autowired
    private VerifiedItemRepository repo;

    @Autowired
    private ItemClassificationService itemClassificationService;

    @Autowired
    private PurchaseItemService purchaseItemService;

    // Generate next verification ID
    private String generateVerificationId(String itemClassificationId) {
        List<VerifiedItem> allItems = repo.findAll();
        int max = allItems.stream()
                .map(VerifiedItem::getVerification)
                .filter(v -> v != null && v.startsWith(itemClassificationId + "-I"))
                .map(v -> v.substring(v.lastIndexOf("I") + 1))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        int next = max + 1;
        return String.format("%s-I%03d", itemClassificationId, next);
    }

    public List<VerifiedItem> generateFromPurchaseItems(String purchaseId, List<PurchaseItem> items) {
        List<VerifiedItem> verifiedItems = new ArrayList<>();

        // Map to track next number for each classification in this batch
        Map<String, Integer> counters = new HashMap<>();

        for (PurchaseItem item : items) {
            ItemClassification ic = itemClassificationService.getById(item.getItemClassificationId());
            if (ic == null) continue;

            int differenceNumber = ic.getDifferenceNumber();
            int packedQuantity = ic.getPackedQuantity();
            int purchaseQuantity = item.getQuantity();

            // Determine number of IDs to generate
            int count = (differenceNumber == 1 && packedQuantity == 1) ? purchaseQuantity :
                    (differenceNumber > 1 && packedQuantity > 1) ? differenceNumber :
                            (differenceNumber == 1 && packedQuantity > 1) ? differenceNumber : 0;

            if (count == 0) continue;

            // initialize counter from DB if not already
            counters.computeIfAbsent(item.getItemClassificationId(),
                    id -> getCurrentMax(id));

            for (int i = 0; i < count; i++) {
                VerifiedItem v = new VerifiedItem();
                v.setPurchaseId(purchaseId);
                v.setProductCategoryId(item.getProductCategoryId());
                v.setProductId(item.getProductId());
                v.setItemClassificationId(item.getItemClassificationId());
                v.setItemId(item.getItemId());
                v.setRate(item.getRate());

                // Assign quantity based on rules
                int qty = 0;
                if (packedQuantity == 1 && differenceNumber == 1) {
                    qty = 1; // Saree: each ID = 1
                } else if (packedQuantity > 1 && differenceNumber > 1) {
                    qty = (i == 0) ? packedQuantity * purchaseQuantity : 0; // Pencil: first ID = PQ*quantity, others = 0
                } else if (packedQuantity > 1 && differenceNumber == 1) {
                    qty = packedQuantity * purchaseQuantity; // Pen: single ID = PQ*quantity
                }
                v.setQuantity(qty);

                // increment counter and generate ID
                int next = counters.get(item.getItemClassificationId()) + 1;
                counters.put(item.getItemClassificationId(), next);

                v.setVerification(String.format("%s-I%03d", item.getItemClassificationId(), next));
                v.setStatus("new");

                verifiedItems.add(v);
            }
        }

        return repo.saveAll(verifiedItems);
    }

    // helper: get current max number from DB
    private int getCurrentMax(String classificationId) {
        return repo.findByItemClassificationId(classificationId).stream()
                .map(VerifiedItem::getVerification)
                .filter(v -> v != null && v.startsWith(classificationId + "-I"))
                .map(v -> v.substring(v.lastIndexOf("I") + 1))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }


    public List<VerifiedItem> generateFromPurchaseItemsByPurchaseId(String purchaseId) {
        List<PurchaseItem> items = purchaseItemService.getItemsByPurchaseId(purchaseId);
        if (items == null || items.isEmpty()) return List.of();
        return generateFromPurchaseItems(purchaseId, items);
    }

    public VerifiedItem save(VerifiedItem item) {
        return repo.save(item);
    }

    public Optional<VerifiedItem> getById(String id) {
        return repo.findById(id);
    }

    public List<VerifiedItem> getAll() {
        return repo.findAll();
    }

    public List<VerifiedItem> getByPurchaseId(String purchaseId) {
        return repo.findByPurchaseId(purchaseId);
    }

    public VerifiedItem updateItem(VerifiedItem existingItem, MultipartFile itemImage, VerifiedItem updatedItem) throws IOException {
        existingItem.setSellingCost(updatedItem.getSellingCost());
        existingItem.setDiscount1(updatedItem.getDiscount1());
        existingItem.setDiscount2(updatedItem.getDiscount2());
        existingItem.setDiscount3(updatedItem.getDiscount3());
        existingItem.setHotsale1(updatedItem.getHotsale1());
        existingItem.setHotsale2(updatedItem.getHotsale2());
        existingItem.setSalesType(updatedItem.getSalesType());
        existingItem.setSpecification(updatedItem.getSpecification());
        existingItem.setStatus(updatedItem.getStatus());

        if (itemImage != null && !itemImage.isEmpty()) {
            String base64 = Base64.getEncoder().encodeToString(itemImage.getBytes());
            existingItem.setItemImage("data:" + itemImage.getContentType() + ";base64," + base64);
        }

        return repo.save(existingItem);
    }







}
