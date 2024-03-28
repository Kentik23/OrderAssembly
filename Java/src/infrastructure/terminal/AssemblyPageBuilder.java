package infrastructure.terminal;

import application.model.ProductDetails;
import entities.Rack;

import java.util.Arrays;
import java.util.List;

public class AssemblyPageBuilder {
    public String build(int[] orderNumbers, List<ProductDetails> productDetailsList) {
        StringBuilder assemblingPage = new StringBuilder("=+=+=+=\nСтраница сборки заказов ").append(Arrays.toString(orderNumbers)).append('\n').append('\n');
        String rackName = "";
        for (ProductDetails productDetails : productDetailsList) {
            //Вывод названия стеллажа, если он встречается впервые
            if (!rackName.equals(productDetails.getMainRack().getName())) {
                rackName = productDetails.getMainRack().getName();
                assemblingPage.append("===Стеллаж ").append(rackName).append('\n');
            }
            //вывод информации по товару
            assemblingPage.append(productDetails.getName()).append(" (id=").append(productDetails.getId()).append(')').append('\n');
            //вывод информации по заказу
            assemblingPage.append("заказ ").append(productDetails.getOrderId()).append(", ").append(productDetails.getCount()).append(" шт").append('\n');
            //Если список с дополнительными стеллажами не пустой, то указываем их названия
            if (!productDetails.getAdditionalRacks().isEmpty()) {
                assemblingPage.append("доп стеллаж: ");
                for (Rack rack : productDetails.getAdditionalRacks())
                    assemblingPage.append(rack.getName()).append(',');
                assemblingPage.deleteCharAt(assemblingPage.length() - 1).append('\n');
            }
            assemblingPage.append('\n');
        }
        return assemblingPage.toString();
    }
}
