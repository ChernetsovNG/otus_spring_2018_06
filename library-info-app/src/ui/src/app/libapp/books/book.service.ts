import {Injectable} from "@angular/core";
import {Book} from "../../model/book.model";

@Injectable()
export class BookService {

    static getAuthorsNames(book: Book): string {
        return book.authors.map(author => author.name).join(", ");
    }

    static getGenresNames(book: Book): string {
        return book.genres.map(genre => genre.name).join(", ")
    }

    static getComments(book: Book): string[] {
        return book.comments.map(comment => comment.text)
    }

}
