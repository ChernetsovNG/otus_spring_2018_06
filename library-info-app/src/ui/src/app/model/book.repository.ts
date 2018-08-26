import {Book} from "./book.model";
import {Injectable} from "@angular/core";
import {StaticDataSource} from "./static.datasource";

@Injectable()
export class BookRepository {
    private books: Book[] = [];

    constructor(private dataSource: StaticDataSource) {
        dataSource.getBooks().subscribe(data => {
            this.books = data;
        });
    }

    getBooks(): Book[] {
        return this.books;
    }

    getBook(id: string): Book {
        return this.books.find(b => b.id == id);
    }
}
