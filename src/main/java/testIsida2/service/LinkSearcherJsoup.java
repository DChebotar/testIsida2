package testIsida2.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import testIsida2.controllers.LinkSearcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**<b>Класс реализует интерфейс LinkSearcher, предназначен для поска ссылок на странице с использованием DOM парсера JSOUP</b>*/
@Service("linkSearcherJsoup")
public class LinkSearcherJsoup implements LinkSearcher {
    @Override
    public Map<String, String> analyzePage(String url) {
        Map<String, String> result = new HashMap<>();
        try {
            //получение страницы по URL
            Document document = Jsoup.connect(url).get();
            //получение элементов с тегом <a>
            Elements elements = document.getElementsByTag("a");
            for (Element element : elements){
                //получение ссылок
                String link = element.attr("href");
                //получение содержимого тега <a>, оно же есть имя ссылки
                String linkName = element.html();
                //если ссылка соответствует URL (внутренние ссылки и заглушки нас не интересуют)
                if (link.matches(REGEX_URL)){
                    //добавляем ссылку и имя в map
                    result.put(link, linkName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
