import {Injectable} from "@angular/core";
import {Book} from "./book.model";
import {Author} from "./author.model";
import {Genre} from "./genre.model";
import {from, Observable} from "rxjs";

@Injectable()
export class StaticDatasource {
    private authors: Author[] = [
        new Author(null, "Курт Воннегут", []),
        new Author(null, "Джек Лондон", []),
        new Author(null, "Стивен Кинг", []),
        new Author(null, "Лев Толстой", []),
        new Author(null, "Брюс Эккель", []),
        new Author(null, "Эрнест Хеммингуэй", [])];

    private genres: Genre[] = [
        new Genre(null, "Фантастика", []),
        new Genre(null, "Приключения", []),
        new Genre(null, "Хоррор", []),
        new Genre(null, "Проза", []),
        new Genre(null, "Программирование", [])];

    private books: Book[] = [
        new Book(null, "Бойня номер пять, или Крестовый поход детей", [this.authors[0]], [this.genres[0]], []),
        new Book(null, "Белый клык", [this.authors[1]], [this.genres[1], this.genres[3]], []),
        new Book(null, "Оно", [this.authors[2]], [this.genres[0], this.genres[2]], []),
        new Book(null, "Война и мир", [this.authors[3]], [this.genres[3]], []),
        new Book(null, "Философия Java", [this.authors[4]], [this.genres[4]], []),
        new Book(null, "Праздник, который всегда с тобой", [this.authors[5]], [this.genres[3]], [])];

    getAuthors(): Observable<Author[]> {
        return from([this.authors]);
    }

    getGenres(): Observable<Genre[]> {
        return from([this.genres]);
    }

    getBooks(): Observable<Book[]> {
        return from([this.books]);
    }

}
