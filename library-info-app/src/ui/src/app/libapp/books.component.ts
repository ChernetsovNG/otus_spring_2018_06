import {Component} from "@angular/core";
import {BookRepository} from "../model/book.repository";
import {Book} from "../model/book.model";

@Component({
    selector: "books",
    moduleId: module.id,
    templateUrl: "books.component.html"
})
export class BooksComponent {
    public booksPerPage = 4;
    public selectedPage = 1;

    constructor(private repository: BookRepository) {
    }

    get books(): Book[] {
        let pageIndex = (this.selectedPage - 1) * this.booksPerPage;
        return this.repository.getBooks().slice(pageIndex, pageIndex + this.booksPerPage);
    }

    changePage(newPage: number) {
        this.selectedPage = newPage;
    }

    changePageSize(newSize: number) {
        this.booksPerPage = Number(newSize);
        this.changePage(1);
    }

    get pageCount(): number {
        return Math.ceil(this.repository.getBooks().length / this.booksPerPage);
    }

}
