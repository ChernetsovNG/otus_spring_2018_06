import {Component} from "@angular/core";
import {DataRepository} from "../../model/data.repository";
import {Router} from "@angular/router";
import {Genre} from "../../model/genre.model";

@Component({
    selector: "genres",
    moduleId: module.id,
    templateUrl: "genres.component.html"
})
export class GenresTableComponent {
    public elementsPerPage = 4;
    public selectedPage = 1;

    constructor(private repository: DataRepository, private router: Router) {
    }

    get genres(): Genre[] {
        let pageIndex = (this.selectedPage - 1) * this.elementsPerPage;
        return this.repository.getGenres().slice(pageIndex, pageIndex + this.elementsPerPage);
    }

    changePage(newPage: number) {
        this.selectedPage = newPage;
    }

    changePageSize(newSize: number) {
        this.elementsPerPage = Number(newSize);
        this.changePage(1);
    }

    get pageCount(): number {
        return Math.ceil(this.repository.getGenres().length / this.elementsPerPage);
    }

    showBooksList() {
        this.router.navigateByUrl("/books");
    }

    showAuthorsList() {
        this.router.navigateByUrl("/authors");
    }

}
