import {from, Observable} from "rxjs";
import {Book} from "./book.model";
import {HttpClient} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {Order} from "./order.model";
import {Author} from "./author.model";
import {Genre} from "./genre.model";
import {Comment} from "./comment.model";

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
            .get<Book[]>(this.baseUrl + "/books");
    }

    saveBook(book: Book): Observable<Book> {
        return this.http
            .post<Book>(this.baseUrl + "/books", book);
    }

    updateBook(book: Book): Observable<Book> {
        return this.http
            .put<Book>(this.baseUrl + "/books", book);
    }

    deleteBook(id: string): Observable<Book> {
        return this.http
            .delete<Book>(this.baseUrl + `/books/${id}`);
    }

    // Authors

    getAuthors(): Observable<Author[]> {
        return this.http
            .get<Author[]>(this.baseUrl + "/authors");
    }

    saveAuthor(author: Author): Observable<Author> {
        return this.http
            .post<Author>(this.baseUrl + "/authors", author);
    }

    updateAuthor(author: Author): Observable<Author> {
        return this.http
            .put<Author>(this.baseUrl + "/authors", author);
    }

    deleteAuthor(id: string): Observable<Author> {
        return this.http
            .delete<Author>(this.baseUrl + `/authors/${id}`);
    }

    // Genres

    getGenres(): Observable<Genre[]> {
        return this.http
            .get<Genre[]>(this.baseUrl + "/genres");
    }

    saveGenre(genre: Genre): Observable<Genre> {
        return this.http
            .post<Genre>(this.baseUrl + "/genres", genre);
    }

    updateGenre(genre: Genre): Observable<Genre> {
        return this.http
            .put<Genre>(this.baseUrl + "/genres", genre);
    }

    deleteGenre(id: string): Observable<Genre> {
        return this.http
            .delete<Genre>(this.baseUrl + `/genres/${id}`);
    }

    // Comments

    getComments(): Observable<Comment[]> {
        return this.http
            .get<Comment[]>(this.baseUrl + "/comments");
    }

    getCommentsForBook(bookId: string): Observable<Comment[]> {
        return this.http
            .get<Comment[]>(this.baseUrl + "/comments/books/" + bookId);
    }

    saveComment(comment: Comment): Observable<Comment> {
        return this.http
            .post<Comment>(this.baseUrl + "/comments", comment);
    }

    updateComment(comment: Comment): Observable<Comment> {
        return this.http
            .put<Comment>(this.baseUrl + "/comments", comment);
    }

    deleteComment(id: string): Observable<Comment> {
        return this.http
            .delete<Comment>(this.baseUrl + `/comments/${id}`);
    }

    // Orders

    saveOrder(order: Order): Observable<Order> {
        alert("Your order is: " + JSON.stringify(order));
        return from([order]);
    }

}
