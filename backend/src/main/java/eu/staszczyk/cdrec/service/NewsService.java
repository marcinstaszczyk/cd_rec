package eu.staszczyk.cdrec.service;

import eu.staszczyk.cdrec.model.News;

public interface NewsService {
    News getNews(String country, String category);
}
