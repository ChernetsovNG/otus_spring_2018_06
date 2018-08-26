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

    getBooks(): Observable<Book[]> {
        return this.http
            .get(this.baseUrl + "/books")
            .pipe(map((value: Array<Book>) => value));
    }

    getAuthors(): Observable<Author[]> {
        return this.http
            .get(this.baseUrl + "/authors")
            .pipe(map((value: Array<Author>) => value));
    }

    getGenres(): Observable<Genre[]> {
        return this.http
            .get(this.baseUrl + "/genres")
            .pipe(map((value: Array<Genre>) => value));
    }

    getComments(bookId: string): Observable<Comment[]> {
        return this.http
            .get(this.baseUrl + "/comments/books/" + bookId)
            .pipe(map((value: Array<Comment>) => value));
    }

    saveOrder(order: Order): Observable<Order> {
        alert("Your order is: " + JSON.stringify(order));
        return from([order]);
    }

}
