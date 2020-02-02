import {Component, Input, OnInit} from '@angular/core';
import Article from "../../model/article";

@Component({
  selector: 'cdr-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit {

  @Input() article: Article;

  constructor() { }

  ngOnInit() {
  }

}
