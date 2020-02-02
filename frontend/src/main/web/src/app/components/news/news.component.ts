import {Component, Input, OnInit} from '@angular/core';
import Article from "../../model/article";

@Component({
  selector: 'cdr-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss']
})
export class NewsComponent implements OnInit {

  @Input() data: Array<Article>;

  constructor() { }

  ngOnInit() {
  }

}
