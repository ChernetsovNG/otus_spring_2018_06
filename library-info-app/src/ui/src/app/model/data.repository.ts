import {Book} from "./book.model";
import {Injectable} from "@angular/core";
import {StaticDataSource} from "./static.datasource";
import {Author} from "./author.model";
import {Genre} from "./genre.model";

@Injectable()
export class DataRepository {
    private books: Book[] = [];
    private authors: Author[] = [];
    private genres: Genre[] = [];

    constructor(private dataSource: StaticDataSource) {
        dataSource.getBooks().subscribe(data => {
            this.books = data;
        });

        dataSource.getAuthors().subscribe(data => {
            this.authors = data;
        });

        dataSource.getGenres().subscribe(data => {
            this.genres = data;
        });
    }

    getBooks(): Book[] {
        return this.books;
    }

    getAuthors(): Author[] {
        return this.authors;
    }

    getGenres(): Genre[] {
        return this.genres;
    }

    getBook(id: string): Book {
        return this.books.find(b => b.id == id);
    }

    getAuthor(id: string): Author {
        return this.authors.find(a => a.id == id);
    }

    getGenre(id: string): Genre {
        return this.genres.find(g => g.id == id);
    }
}
