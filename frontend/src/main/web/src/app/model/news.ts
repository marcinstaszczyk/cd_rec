import Article from "./article";

export default interface News {
  country: string;
  category: string;
  articles: Array<Article>;
}
