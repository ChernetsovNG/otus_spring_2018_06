import {Injectable} from "@angular/core";
import {Book} from "../../model/book.model";

@Injectable()
export class BookService {

    static getAuthorsNames(book: Book): string {
        return book.authors
            .filter(author => author != null)
            .map(author => author.name).join(", ");
    }

    static getGenresNames(book: Book): string {
        return book.genres
            .filter(genre => genre != null)
            .map(genre => genre.name).join(", ");
    }

    static getComments(book: Book): string[] {
        return book.comments
            .filter(comment => comment != null)
            .map(comment => comment.text);
    }

}
