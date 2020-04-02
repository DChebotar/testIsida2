package testIsida2.service;

import org.springframework.stereotype.Service;
import testIsida2.controllers.LinkSearcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**<b>Класс реализует интерфейс LinkSearcher, предназначен для поска ссылок на странице с использованием регулярных выражений</b>*/
@Service("linkSearcherRegexp")
public class LinkSearcherRegexp implements LinkSearcher{
    @Override
    public Map<String, String> analyzePage(String url) {
        Map<String, String> result = new HashMap<>();
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try{
            //открываем поток ввода для считывания страницы
            isr = new InputStreamReader(new URL(url).openConnection().getInputStream(), "UTF-8");
            reader = new BufferedReader(isr);
            //считываем страницу
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            String page = sb.toString();
            Pattern patternTag = Pattern.compile(REGEX_A_TEG);
            Matcher matcherTag = patternTag.matcher(page);
            Pattern patternLink;
            Matcher matcherLink;
            String attrs;
            String link;
            String linkName;
            //пока есть теги <a>
            while (matcherTag.find()){
                //получаем атрибуты тега <a>
                attrs = matcherTag.group(1);
                //получение содержимого тега <a>, оно же есть имя ссылки
                linkName = matcherTag.group(2);
                patternLink = Pattern.compile(REGEX_LINK);
                matcherLink = patternLink.matcher(attrs);
                //пока есть атрибут href
                while (matcherLink.find()){
                    //получение ссылки
                    link = matcherLink.group(1).replaceAll("'","").replaceAll("\"", "");
                    //если ссылка соответствует URL (внутренние ссылки и заглушки нас не интересуют)
                    if(link.matches(REGEX_URL)){
                        //добавляем ссылку и имя в map
                        result.put(link, linkName);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //закрываем ресурсы
            try {
                if (reader != null){
                    reader.close();
                }
                if (isr != null){
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
