package digitalcity.demeyert.overstockmanager.controller;

import digitalcity.demeyert.overstockmanager.service.MKMService;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.LightArticle;
import org.api.mkm.modele.Order;
import org.api.mkm.services.OrderService;
import org.api.mkm.services.StockService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:8100")
@RestController
@RequestMapping("/mkm")
public class MkmController {

    private final StockService stockService;
    private final OrderService orderService;

    private final MKMService mkmService;

    public MkmController(StockService stockService, OrderService orderService, MKMService mkmService) {
        this.stockService = stockService;
        this.orderService = orderService;
        this.mkmService = mkmService;
    }

    @GetMapping("getStock")
    public List<LightArticle> getStock() throws IOException {
        return mkmService.getStock();
    }

    @GetMapping("getStockDemo")
    public List<LightArticle> getFullStock() throws IOException {
        return mkmService.getStockForDemo();
    }

    @GetMapping("shoppingCard")
    public List<Article> shoppingCard() throws IOException {
        return mkmService.getShoppingcartArticles();
    }
    @GetMapping("search")
    public List<LightArticle> searchByName(int id) throws IOException {
        return mkmService.getStock(id);
        //try rajouter articles au debut
    }
    @GetMapping("order")
    public List<Order> searchByOrder() throws IOException {
        return orderService.listOrders(OrderService.ACTOR.seller, OrderService.STATE.sent, 5);
    }

}
