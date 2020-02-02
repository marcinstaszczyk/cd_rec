import {Component, OnInit} from '@angular/core';
import Article from "./model/article";
import {NewsService} from "./services/news.service";

@Component({
  selector: 'cdr-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements  OnInit{
  list: Array<Article>;

  constructor(private newsService: NewsService) {
  }

  ngOnInit(): void {
    this.newsService.getNews("pl", "technology")
      .then(result => {this.list = result.articles});
  }

}
