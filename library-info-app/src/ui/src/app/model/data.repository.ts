import {Book} from "./book.model";
import {Injectable} from "@angular/core";
import {Author} from "./author.model";
import {Genre} from "./genre.model";
import {Comment} from "./comment.model";
import {RestDataSource} from "./rest.datasource";

@Injectable()
export class DataRepository {
    private books: Book[] = [];
    private authors: Author[] = [];
    private genres: Genre[] = [];
    private comments: Comment[] = [];

    constructor(private dataSource: RestDataSource) {
        dataSource.getBooks().subscribe(data => {
            this.books = data;
        });

        dataSource.getAuthors().subscribe(data => {
            this.authors = data;
        });

        dataSource.getGenres().subscribe(data => {
            this.genres = data;
        });

        dataSource.getComments().subscribe(data => {
            this.comments = data;
        });
    }

    // Books

    getBooks(): Book[] {
        return this.books;
    }

    getBook(id: string): Book {
        return this.books.find(b => b.id == id);
    }

    saveBook(book: Book) {
        if (book.id == null) {
            this.dataSource.saveBook(book)
                .subscribe(b => this.books.push(b));
        } else {
            this.dataSource.updateBook(book)
                .subscribe(b => {
                    this.books.splice(this.books.findIndex(b => b.id == book.id), 1, book);
                });
        }
    }

    deleteBook(id: string) {
        this.dataSource.deleteBook(id)
            .subscribe(() => {
                this.books.splice(this.books.findIndex(b => b.id == id), 1);
            });
    }

    updateBooksList() {
        this.dataSource.getBooks().subscribe(data => {
            this.books = data;
        });
    }

    // Authors

    getAuthors(): Author[] {
        return this.authors;
    }

    getAuthor(id: string): Author {
        return this.authors.find(a => a.id == id);
    }

    saveAuthor(author: Author) {
        if (author.id == null) {
            this.dataSource.saveAuthor(author)
                .subscribe(a => {
                    this.authors.push(a);
                    this.updateBooksList();
                });
        } else {
            this.dataSource.updateAuthor(author)
                .subscribe(a => {
                    this.authors.splice(this.authors.findIndex(a => a.id == author.id), 1, author);
                    this.updateBooksList();
                });
        }
    }

    deleteAuthor(id: string) {
        this.dataSource.deleteAuthor(id)
            .subscribe(a => {
                this.authors.splice(this.authors.findIndex(a => a.id == id), 1);
                this.updateBooksList();
            });
    }

    // Genres

    getGenres(): Genre[] {
        return this.genres;
    }

    getGenre(id: string): Genre {
        return this.genres.find(g => g.id == id);
    }

    saveGenre(genre: Genre) {
        if (genre.id == null) {
            this.dataSource.saveGenre(genre)
                .subscribe(g => {
                    this.genres.push(g);
                    this.updateBooksList();
                });
        } else {
            this.dataSource.updateGenre(genre)
                .subscribe(g => {
                    this.genres.splice(this.genres.findIndex(g => g.id == genre.id), 1, genre);
                    this.updateBooksList();
                });
        }
    }

    deleteGenre(id: string) {
        this.dataSource.deleteGenre(id)
            .subscribe(() => {
                this.genres.splice(this.genres.findIndex(g => g.id == id), 1);
                this.updateBooksList();
            });
    }

    // comments

    getComments(): Comment[] {
        return this.comments;
    }

    getComment(id: string): Comment {
        return this.comments.find(c => c.id == id);
    }

    getCommentsByBookId(bookId: string): Comment[] {
        return this.comments.filter(c => c.bookId == bookId);
    }

    saveComment(comment: Comment) {
        console.log(comment);
        if (comment.id == null) {
            this.dataSource.saveComment(comment)
                .subscribe(c => this.comments.push(c));
        } else {
            this.dataSource.updateComment(comment)
                .subscribe(c =>
                    this.comments.splice(this.comments.findIndex(c => c.id == comment.id), 1, comment));
        }
    }

    deleteComment(id: string) {
        this.dataSource.deleteComment(id)
            .subscribe(c => {
                this.comments.splice(this.comments.findIndex(c => c.id == id), 1);
            });
    }

}
