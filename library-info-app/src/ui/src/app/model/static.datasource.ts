import {Injectable} from "@angular/core";
import {Book} from "./book.model";
import {Author} from "./author.model";
import {Genre} from "./genre.model";
import {from, Observable} from "rxjs";
import {Order} from "./order.model";

@Injectable()
export class StaticDataSource {
    private authors: Author[] = [
        new Author("1", "Курт Воннегут", []),
        new Author("2", "Джек Лондон", []),
        new Author("3", "Стивен Кинг", []),
        new Author("4", "Лев Толстой", []),
        new Author("5", "Брюс Эккель", []),
        new Author("6", "Эрнест Хеммингуэй", [])];

    private genres: Genre[] = [
        new Genre("7", "Фантастика", []),
        new Genre("8", "Приключения", []),
        new Genre("9", "Хоррор", []),
        new Genre("10", "Проза", []),
        new Genre("11", "Программирование", [])];

    private books: Book[] = [
        new Book("12", "Бойня номер пять, или Крестовый поход детей", [this.authors[0]], [this.genres[0]], []),
        new Book("13", "Белый клык", [this.authors[1]], [this.genres[1], this.genres[3]], []),
        new Book("14", "Оно", [this.authors[2]], [this.genres[0], this.genres[2]], []),
        new Book("15", "Война и мир", [this.authors[3]], [this.genres[3]], []),
        new Book("16", "Философия Java", [this.authors[4]], [this.genres[4]], []),
        new Book("17", "Праздник, который всегда с тобой", [this.authors[5]], [this.genres[3]], [])];

    getAuthors(): Observable<Author[]> {
        return from([this.authors]);
    }

    getGenres(): Observable<Genre[]> {
        return from([this.genres]);
    }

    getBooks(): Observable<Book[]> {
        return from([this.books]);
    }

    saveOrder(order: Order): Observable<Order> {
        console.log(JSON.stringify(order));
        return from([order]);
    }

}
