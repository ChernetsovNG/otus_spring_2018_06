import {Component} from "@angular/core";
import {ActivatedRoute, Router} from '@angular/router';
import {DataRepository} from "../../model/data.repository";
import {Comment} from "../../model/comment.model";

@Component({
    selector: "comments",
    moduleId: module.id,
    templateUrl: "comments.component.html"
})
export class CommentsComponent {
    public elementsPerPage = 4;
    public selectedPage = 1;

    constructor(private repository: DataRepository, private route: ActivatedRoute, private router: Router) {
    }

    get comments(): Comment[] {
        const id = this.route.snapshot.paramMap.get("bookId");
        let pageIndex = (this.selectedPage - 1) * this.elementsPerPage;
        return this.repository.getBook(id).comments.slice(pageIndex, pageIndex + this.elementsPerPage);
    }

    changePage(newPage: number) {
        this.selectedPage = newPage;
    }

    changePageSize(newSize: number) {
        this.elementsPerPage = Number(newSize);
        this.changePage(1);
    }

    get pageCount(): number {
        const id = this.route.snapshot.paramMap.get("bookId");
        return Math.ceil(this.repository.getBook(id).comments.length / this.elementsPerPage);
    }

    showBooksList() {
        this.router.navigateByUrl("/books");
    }

    showAuthorsList() {
        this.router.navigateByUrl("/authors");
    }

    showGenresList() {
        this.router.navigateByUrl("/genres");
    }

}
