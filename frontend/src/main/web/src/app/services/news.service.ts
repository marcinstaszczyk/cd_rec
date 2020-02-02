import { Injectable } from '@angular/core';
import News from "../model/news";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient) { }

  public getNews(country: string, category: string): Promise<News> {
    return this.http.get<News>(`/news/${country}/${category}`).toPromise();
  }
}
