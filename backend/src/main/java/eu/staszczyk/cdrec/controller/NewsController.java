package eu.staszczyk.cdrec.controller;

import eu.staszczyk.cdrec.model.News;
import eu.staszczyk.cdrec.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news/{country}/{category}")
    public News getNews(@PathVariable("country") String country, @PathVariable("category") String category) {
        return newsService.getNews(country, category);
    }
}
