package digitalcity.demeyert.overstockmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import digitalcity.demeyert.overstockmanager.mapper.CardMapper;
import digitalcity.demeyert.overstockmanager.mapper.CollecMapper;
import digitalcity.demeyert.overstockmanager.model.entity.Card;
import digitalcity.demeyert.overstockmanager.model.entity.Collec;
import digitalcity.demeyert.overstockmanager.model.entity.CollectCard;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Inserted;
import org.api.mkm.modele.LightArticle;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Service;

@Service
public class MKMService {

    private XStream xstream;
    private Logger logger = LogManager.getLogger(this.getClass());
    private CardMapper mapper;
    private CollecMapper collecMapper;
    private CollectService collectService;
    private CardService cardService;

    private List<LightArticle> mkmStock = new ArrayList<>();

    public MKMService(CardMapper mapper, CollecMapper collecMapper, CollectService collectService) {
        this.mapper = mapper;
        this.collecMapper = collecMapper;
        this.collectService = collectService;
        xstream = Tools.instNewXstream();
        xstream.addImplicitCollection(Response.class, "links", Link.class);
        xstream.addImplicitCollection(Response.class, "lightArticles",LightArticle.class);
        xstream.addImplicitCollection(Response.class, "inserted",Inserted.class);
        xstream.addImplicitCollection(Response.class, "updatedArticles", Inserted.class);

    }
    public List<LightArticle> getStockForDemo() throws IOException {
        for (int i = 1; i < 1000; i+=100) {
            mkmStock.addAll(getStock(i));
        }
        return mkmStock;
    }

    public List<LightArticle> getStock() throws IOException
    {
        return getStock(1);
    }

    public List<LightArticle> getStock(int page) throws IOException
    {
        String link=MkmConstants.MKM_API_URL+"/stock/"+page;

        String xml= Tools.getXMLResponse(link, "GET", getClass());

        //TODO ugly !!!! but need to reforge stockmanagement
        xml = xml.replace("<article>", "<lightArticles>").replace("</article>", "</lightArticles>");

        Response res = (Response)xstream.fromXML(xml);

        return res.getLightArticles();
    }

    public List<Article> getShoppingcartArticles() throws IOException
    {

        String link=MkmConstants.MKM_API_URL+"/stock/shoppingcart-articles";

        String xml= Tools.getXMLResponse(link, "GET", getClass());

        Response res = (Response)xstream.fromXML(xml);

        return res.getArticle();
    }


    public void exportStock(File f,Integer idGame) throws IOException
    {
        String link=MkmConstants.MKM_API_URL+"/stock/file";

        if(idGame!=null)
            link=MkmConstants.MKM_API_URL+"/stock/file?idGame="+idGame;

        String xml= Tools.getXMLResponse(link, "GET", getClass());
        Response res = (Response)xstream.fromXML(xml);


        byte[] bytes = Base64.decodeBase64(res.getPriceguidefile());
        File temp =  new File("mkm_temp.gz");
        FileUtils.writeByteArrayToFile(temp, bytes );
        Tools.unzip(temp, f);
        if(!temp.delete())
        {
            logger.error("couln't remove " + temp.getAbsolutePath());
        }
    }



    public Inserted addArticle(Article a) throws IOException
    {
        return addArticles(List.of(a)).get(0);
    }

    public Inserted updateArticles(Article a) throws IOException
    {
        return updateArticles(List.of(a)).get(0);
    }


    public List<Inserted> updateArticles(List<Article> list) throws IOException
    {
        String link =MkmConstants.MKM_API_URL+"/stock";
        StringBuilder temp = new StringBuilder();
        temp.append(MkmConstants.XML_HEADER);
        temp.append("<request>");

        for(Article a : list)
        {
            temp.append("<article>");
            temp.append("<idProduct>").append(a.getIdProduct()).append("</idProduct>");
            temp.append("<idArticle>").append(a.getIdArticle()).append("</idArticle>");
            temp.append("<count>").append(a.getCount()).append("</count>");
            if(a.getLanguage()!=null)
                temp.append("<idLanguage>").append(a.getLanguage().getIdLanguage()).append("</idLanguage>");

            if(a.getComments()!=null)
                temp.append("<comments>").append(a.getComments()).append("</comments>");

            temp.append("<price>").append(a.getPrice()).append("</price>");
            temp.append("<condition>").append(a.getCondition()).append("</condition>");
            temp.append("<isFoil>").append(a.isFoil()).append("</isFoil>");
            temp.append("<isSigned>").append(a.isSigned()).append("</isSigned>");
            temp.append("<isPlayset>").append(a.isPlayset()).append("</isPlayset>");
            temp.append("</article>");
        }
        temp.append("</request>");

        String xml = Tools.getXMLResponse(link, "PUT", getClass(), temp.toString());

        Response res = (Response)xstream.fromXML(xml);

        return res.getUpdatedArticles();

    }

    public List<Inserted> addArticles(List<Article> list) throws IOException
    {
        String link =MkmConstants.MKM_API_URL+"/stock";
        StringBuilder temp = new StringBuilder();

        temp.append(MkmConstants.XML_HEADER);
        temp.append("<request>");

        for(Article a : list)
        {
            temp.append("<article>");
            temp.append("<idProduct>").append(a.getIdProduct()).append("</idProduct>");
            temp.append("<count>").append(a.getCount()).append("</count>");
            if(a.getLanguage()!=null)
                temp.append("<idLanguage>").append(a.getLanguage().getIdLanguage()).append("</idLanguage>");

            if(a.getComments()!=null)
                temp.append("<comments>").append(a.getComments()).append("</comments>");

            temp.append("<price>").append(a.getPrice()).append("</price>");
            temp.append("<condition>").append(a.getCondition()).append("</condition>");
            temp.append("<isFoil>").append(a.isFoil()).append("</isFoil>");
            temp.append("<isSigned>").append(a.isSigned()).append("</isSigned>");
            temp.append("<isPlayset>").append(a.isPlayset()).append("</isPlayset>");
            temp.append("</article>");
        }
        temp.append("</request>");

        String xml = Tools.getXMLResponse(link, "POST", getClass(), temp.toString());

        Response res = (Response)xstream.fromXML(xml);

        return res.getInserted();

    }

    public void removeArticle(Article a) throws IOException
    {
        removeArticles(List.of(a));
    }

    public void removeArticles(List<Article> list) throws IOException
    {
        String link =MkmConstants.MKM_API_URL+"/stock";
        StringBuilder temp = new StringBuilder();
        temp.append(MkmConstants.XML_HEADER);
        temp.append("<request>");
        for(Article a : list)
        {
            temp.append("<article>");
            temp.append("<idArticle>").append(a.getIdArticle()).append("</idArticle>");
            temp.append("<count>").append(a.getCount()).append("</count>");
            temp.append("</article>");
        }
        temp.append("</request>");
        Tools.getXMLResponse(link, "DELETE", this.getClass(), temp.toString());
    }

    public void exportStockFile(File f) throws IOException
    {
        exportStockFile(f,null);
    }

    public void exportStockFile(File f,Map<ARTICLES_ATT,String> atts) throws IOException
    {
        String link=MkmConstants.MKM_API_URL+"/stock/file";

        if(atts!=null)
        {
            link+="?";
            List<String> paramStrings = new ArrayList<>();
            for(Entry<ARTICLES_ATT, String> parameter:atts.entrySet())
                paramStrings.add(parameter.getKey() + "=" + parameter.getValue());

            link+=Tools.join(paramStrings, "&");
        }

        String xml= Tools.getXMLResponse(link, "GET", this.getClass());
        Response res = (Response)xstream.fromXML(xml);

        byte[] bytes = Base64.decodeBase64(res.getStock());
        File temp =  new File("mkm_stock_temp.gz");
        FileUtils.writeByteArrayToFile(temp, bytes );
        Tools.unzip(temp, f);
        if(!temp.delete())
        {
            logger.error("couldn't delete "+ temp.getAbsolutePath());
        }
    }

    public List<Article> getStockInShoppingCarts() throws IOException
    {
        String link=MkmConstants.MKM_API_URL+"/stock/shoppingcart-articles";
        String xml = Tools.getXMLResponse(link, "GET", this.getClass());
        Response res = (Response)xstream.fromXML(xml);
        return res.getArticle();
    }

    public void changeQte(LightArticle a, int qte) throws IOException
    {
        changeQte(List.of(a), qte);
    }

    public void changeQte(List<LightArticle> list, int qte) throws IOException
    {
        String link =MkmConstants.MKM_API_URL+"/stock";

        if(qte>0)
            link+="/increase";
        else
            link+="/decrease";

        StringBuilder temp = new StringBuilder();

        temp.append(MkmConstants.XML_HEADER);
        temp.append("<request>");

        for(LightArticle a : list)
        {
            temp.append("<article>");
            temp.append("<idArticle>").append(a.getIdArticle()).append("</idArticle>");
            temp.append("<amount>").append(Math.abs(qte)).append("</amount>");
            temp.append("</article>");

            a.setCount(a.getCount()+qte);

        }
        temp.append("</request>");
        Tools.getXMLResponse(link, "PUT", this.getClass(), temp.toString());
    }

    public List<Card> restock(Long id) {
        List<Card> mkmStockCard = mkmStock.stream().map(mapper::toEntitiesLight).collect(Collectors.toList());

        Collec collecToRestock = collecMapper.toEntities( collectService.getOne(id) );
        List<Card> overstockToCSV = new ArrayList<>();

        List<CollectCard> overstock = collecToRestock.getCardList().stream()
                .filter(overstockCollec ->
                    mkmStockCard.stream().anyMatch(stockOnline -> {
//                      if (overstockCollec.getCard().getCardmarketId() != stockOnline.getCardmarketId()) {
//                          overstockCollec.getCard().setCount(4 - stockOnline.getCount());
//                          overstockToCSV.add(overstockCollec.getCard());
//                        }
                        boolean same =
                            stockOnline.isFoil() == overstockCollec.getCard().isFoil() &&
                            stockOnline.getCardmarketId() == overstockCollec.getCard().getCardmarketId() &&
                            stockOnline.getLanguage().equals(overstockCollec.getCard().getLanguage()) &&
                            stockOnline.isSigned() == overstockCollec.getCard().isSigned() &&
                            stockOnline.getState().equals( overstockCollec.getCard().getState() );
                        if (same && stockOnline.getCount() < 4) {
                            overstockCollec.getCard().setCount(4 - stockOnline.getCount());


                            overstockCollec.setQtt(
                                    Math.max(overstockCollec.getQtt() - (4 - stockOnline.getCount()), 0)
                            );


                            overstockToCSV.add(overstockCollec.getCard());

                            // Rajouter les cartes sur le site via L'API MKM, en attendant, g??n??re une liste de carte ?? restock
                            return true;
                        }
                        return false;
                    })
                ).collect(Collectors.toList());

        return  overstockToCSV;
    }
}
