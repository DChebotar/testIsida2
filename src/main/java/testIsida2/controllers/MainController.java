package testIsida2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import testIsida2.service.LinkSearcherRegexp;

import java.util.Map;
/**<b>Класс-контроллер</b>*/
@Controller
public class MainController {

    @Autowired
    @Qualifier("linkSearcherJsoup")
    private LinkSearcher linkSearcher;
    /**
     * Метод принимает HTTP запросы GET по корневому URL
     * @return - главную страницу проекта mainPage.jsp
     */
    @GetMapping("/")
    public String start(){
        return "mainPage";
    }
    /**
     * Принимает HTTP запрос методом POST на URL /analyze
     * @param url - url страницы для поиска ссылок
     * @return - Map<String, String> key - ссылка, value - имя ссылки
     */
    @PostMapping("/analyze")
    public @ResponseBody Map<String, String> analyze(@RequestParam String url){
        //вызов метода для поиска ссылок на странице
        return linkSearcher.analyzePage(url);
    }
}
