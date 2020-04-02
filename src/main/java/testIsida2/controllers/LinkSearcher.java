package testIsida2.controllers;

import java.util.Map;


public interface LinkSearcher {
    static final String REGEX_A_TEG = "(?i)<a(.+?)>(.+?)</a>";
    static final String REGEX_LINK = "(?i)href\\s*=\\s*((\"[^\"]*\")|('[^']*')|([^'\">\\s]+))";
    static final String REGEX_URL = "^(?i)(https?://)(www\\.)?([-a-z0-9]{1,63}\\.)*?[a-z0-9][-a-z0-9]{0,61}[a-z0-9]\\.[a-z]{2,6}(/[-\\w@\\+\\.~#\\?&/=%]*)?$";
    /**
     * Метод для поиска ссылок на странице
     * @param url - url страницы для поиска ссылок
     * @return - Map<String, String> key - ссылка, value - имя ссылки
     */
    public Map<String, String> analyzePage(String url);
}
