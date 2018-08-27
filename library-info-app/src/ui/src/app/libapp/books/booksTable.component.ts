import {Component} from "@angular/core";
import {DataRepository} from "../../model/data.repository";
import {Book} from "../../model/book.model";
import {Cart} from "../../model/cart.model";
import {Router} from "@angular/router";
import {BookService} from "./book.service";

@Component({
    selector: "books",
    moduleId: module.id,
    templateUrl: "booksTable.component.html"
})
export class BooksTableComponent {
    public elementsPerPage = 4;
    public selectedPage = 1;

    constructor(private repository: DataRepository, private cart: Cart, private router: Router) {
    }

    getBooks(): Book[] {
        let pageIndex = (this.selectedPage - 1) * this.elementsPerPage;
        return this.repository.getBooks().slice(pageIndex, pageIndex + this.elementsPerPage);
    }

    deleteBook(id: string) {
        this.repository.deleteBook(id);
    }

    bookAuthorNames(book: Book): string {
        return BookService.getAuthorsNames(book);
    }

    bookGenreNames(book: Book): string {
        return BookService.getGenresNames(book);
    }

    changePage(newPage: number) {
        this.selectedPage = newPage;
    }

    changePageSize(newSize: number) {
        this.elementsPerPage = Number(newSize);
        this.changePage(1);
    }

    get pageCount(): number {
        return Math.ceil(this.repository.getBooks().length / this.elementsPerPage);
    }

    addBookToCart(book: Book) {
        this.cart.addLine(book);
        this.router.navigateByUrl("/cart");
    }

    showBookComments(book: Book) {
        this.router.navigateByUrl("/comments/books/" + book.id);
    }

    showAuthorsList() {
        this.router.navigateByUrl("/authors");
    }

    showGenresList() {
        this.router.navigateByUrl("/genres");
    }

}
