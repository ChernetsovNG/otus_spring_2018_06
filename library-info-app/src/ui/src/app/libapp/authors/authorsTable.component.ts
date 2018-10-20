import {Component} from "@angular/core";
import {DataRepository} from "../../model/data.repository";
import {Author} from "../../model/author.model";
import {Router} from "@angular/router";

@Component({
    selector: "authors",
    moduleId: module.id,
    templateUrl: "authorsTable.component.html"
})
export class AuthorsTableComponent {
    public elementsPerPage = 4;
    public selectedPage = 1;

    constructor(private repository: DataRepository, private router: Router) {
    }

    getAuthors(): Author[] {
        let pageIndex = (this.selectedPage - 1) * this.elementsPerPage;
        return this.repository.getAuthors().slice(pageIndex, pageIndex + this.elementsPerPage);
    }

    deleteAuthor(id: string) {
        this.repository.deleteAuthor(id);
    }

    changePage(newPage: number) {
        this.selectedPage = newPage;
    }

    changePageSize(newSize: number) {
        this.elementsPerPage = Number(newSize);
        this.changePage(1);
    }

    get pageCount(): number {
        return Math.ceil(this.repository.getAuthors().length / this.elementsPerPage);
    }

    showBooksList() {
        this.router.navigateByUrl("/books");
    }

    showGenresList() {
        this.router.navigateByUrl("/genres");
    }

}
