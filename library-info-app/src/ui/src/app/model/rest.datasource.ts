import {from, Observable} from "rxjs";
import {Book} from "./book.model";
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {Order} from "./order.model";
import {Author} from "./author.model";
import {Genre} from "./genre.model";

const PROTOCOL = "http";
const PORT = 3500;

@Injectable()
export class RestDataSource {
    baseUrl: string;

    constructor(private http: HttpClient) {
        this.baseUrl = `${PROTOCOL}://${location.hostname}:${PORT}`;
    }

    // Books

    getBooks(): Observable<Book[]> {
        return this.http
            .get(this.baseUrl + "/books")
            .pipe(map((value: Array<Book>) => value));
    }

    saveBook(book: Book): Observable<Book> {
        return this.http
            .post(this.baseUrl + "/books", book)
            .pipe(map((value: Book) => value));
    }

    updateBook(book: Book): Observable<Book> {
        return this.http
            .put(this.baseUrl + "/books", book)
            .pipe(map((value: Book) => value));
    }

    deleteBook(id: string): Observable<Book> {
        return this.http
            .delete(this.baseUrl + `/books/${id}`)
            .pipe(map((value: Book) => value));
    }

    // Authors

    getAuthors(): Observable<Author[]> {
        return this.http
            .get(this.baseUrl + "/authors")
            .pipe(map((value: Array<Author>) => value));
    }

    saveAuthor(author: Author): Observable<Author> {
        return this.http
            .post(this.baseUrl + "/authors", author)
            .pipe(map((value: Author) => value));
    }

    updateAuthor(author: Author): Observable<Author> {
        return this.http
            .put(this.baseUrl + "/authors", author)
            .pipe(map((value: Author) => value));
    }

    deleteAuthor(id: string): Observable<Author> {
        return this.http
            .delete(this.baseUrl + `/authors/${id}`)
            .pipe(map((value: Author) => value));
    }

    // Genres

    getGenres(): Observable<Genre[]> {
        return this.http
            .get(this.baseUrl + "/genres")
            .pipe(map((value: Array<Genre>) => value));
    }

    saveGenre(genre: Genre): Observable<Genre> {
        return this.http
            .post(this.baseUrl + "/genres", genre)
            .pipe(map((value: Genre) => value));
    }

    updateGenre(genre: Genre): Observable<Genre> {
        return this.http
            .put(this.baseUrl + "/genres", genre)
            .pipe(map((value: Genre) => value));
    }

    deleteGenre(id: string): Observable<Genre> {
        return this.http
            .delete(this.baseUrl + `/genres/${id}`)
            .pipe(map((value: Genre) => value));
    }

    // Comments

    getComments(bookId: string): Observable<Comment[]> {
        return this.http
            .get(this.baseUrl + "/comments/books/" + bookId)
            .pipe(map((value: Array<Comment>) => value));
    }

    saveComment(comment: Comment): Observable<Comment> {
        return this.http
            .post(this.baseUrl + "/comments", comment)
            .pipe(map((value: Comment) => value));
    }

    deleteComment(id: string): Observable<Comment> {
        return this.http
            .delete(this.baseUrl + `/comments/${id}`)
            .pipe(map((value: Comment) => value));
    }

    // Orders

    saveOrder(order: Order): Observable<Order> {
        alert("Your order is: " + JSON.stringify(order));
        return from([order]);
    }

}
