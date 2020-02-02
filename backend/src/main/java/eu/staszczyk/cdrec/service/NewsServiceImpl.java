package eu.staszczyk.cdrec.service;

import eu.staszczyk.cdrec.model.Article;
import eu.staszczyk.cdrec.model.News;
import eu.staszczyk.cdrec.service.dto.ArticleDto;
import eu.staszczyk.cdrec.service.dto.NewsResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final RestTemplate restTemplate;

    public NewsServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public News getNews(String country, String category) {
        /*
        newsapi.org no longer supports sorting parameter.
        So so simulate we load all available data pages (free account up to 5 pages) and sort it locally.
         */

        List<ArticleDto> articleDtos = loadAllPages(country, category);
        sortArticles(articleDtos);

        News news = new News();
        news.setCountry(country);
        news.setCategory(category);
        news.setArticles(articleDtos.stream().map(this::convertToArticle).collect(Collectors.toList()));

        return news;
    }

    private NewsResponseDto getNewsPage(String country, String category, Integer page) {
        return restTemplate.getForObject(
                "https://newsapi.org/v2/top-headlines?apiKey={apiKey}&country={country}&category={category}&page={page}",
                NewsResponseDto.class,
                "5af00b8a2dc248708a5cfa72f97431f5",
                country,
                category,
                page
        );
    }

    private static final int PAGE_SIZE = 20;
    private static final int MAX_FREE_ACCOUNT_PAGES = 5;
    private List<ArticleDto> loadAllPages(String country, String category) {
        NewsResponseDto newsDto = getNewsPage(country, category, 1);
        List<ArticleDto> articles = newsDto.getArticles();

        int totalPages = ((newsDto.getTotalResults() + PAGE_SIZE - 1) / PAGE_SIZE);
        totalPages = Math.min(totalPages, MAX_FREE_ACCOUNT_PAGES);

        for(int page = 2; page <= totalPages; ++page) {
            articles.addAll(getNewsPage(country, category, page).getArticles());
        }

        return articles;
    }

    private static final Comparator<ArticleDto> NEWEST_FIRST_COMPARATOR =
            (a1, a2) -> a2.getPublishedAt().compareTo(a1.getPublishedAt());
    private void sortArticles(List<ArticleDto> articles) {
        articles.sort(NewsServiceImpl.NEWEST_FIRST_COMPARATOR);
    }

    private Article convertToArticle(ArticleDto articleDto) {
        Article article = new Article();

        article.setAuthor(articleDto.getAuthor());
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setDate(articleDto.getPublishedAt());
        article.setSourceName(articleDto.getSource().getName());
        article.setArticleUrl(articleDto.getUrl());
        article.setImageUrl(articleDto.getUrlToImage());

        return article;
    }
}
