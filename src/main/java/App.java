import java.util.ArrayList;
import java.util.List;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;

    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }

    public String bestCharge(List<String> inputs) {
        //TODO: write code here
        int total = 0;


        List<Item> itemTmp = itemRepository.findAll();
        List<SalesPromotion> sales = salesPromotionRepository.findAll();
        int finalPrice[] = new int[2];
        ArrayList<String> promotionItem = new ArrayList<>();

        String res = "============= 订餐明细 =============\n";
        for (String x : inputs) {
            String tmp[] = x.split(" ");
            for (Item i: itemTmp) {
                if(i.getId().equals(tmp[0])) {
                    for (String s: sales.get(1).getRelatedItems()) {
                        if(s.equals(tmp[0])){
                            finalPrice[0] -= (i.getPrice() * Integer.parseInt(tmp[2]))/2;
                            promotionItem.add(i.getName());
                        }
                    }

                    total += (i.getPrice() * Integer.parseInt(tmp[2]));
                    finalPrice[0] += (i.getPrice() * Integer.parseInt(tmp[2]));
                    res += String.format("%s x %s = %.0f元\n", i.getName(), tmp[2],(i.getPrice() * Integer.parseInt(tmp[2])));
                }
            }

        }

        finalPrice[1] = total>=30?total-6:total;
        if(finalPrice[0]<finalPrice[1]){
            res += String.format("-----------------------------------\n使用优惠:\n指定菜品半价(");
           for(int i = 0;i<promotionItem.size();i++){
               res += String.format(promotionItem.get(i));
               if(i != promotionItem.size()-1)
                   res += String.format("，");

           }
            res += String.format(")，省%d元\n",total - finalPrice[0]);
            total = finalPrice[0];

        }
        if(finalPrice[0]>finalPrice[1]){
            res += String.format("-----------------------------------\n" +
                        "使用优惠:\n" +
                        "满30减6元，省6元\n");
                total = finalPrice[1];
        }

        res += String.format("-----------------------------------");
        res += String.format("\n总计：%d元\n===================================", total);


        return res;
    }
}
