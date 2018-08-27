import {Component} from "@angular/core";
import {ActivatedRoute, Router} from '@angular/router';
import {DataRepository} from "../../model/data.repository";
import {Comment} from "../../model/comment.model";

@Component({
    selector: "comments",
    moduleId: module.id,
    templateUrl: "commentsTable.component.html"
})
export class CommentsTableComponent {
    public elementsPerPage = 4;
    public selectedPage = 1;

    private readonly bookId: string;

    constructor(private repository: DataRepository, private route: ActivatedRoute, private router: Router) {
        this.bookId = this.route.snapshot.paramMap.get("bookId");
    }

    getBookId(): string {
        return this.bookId;
    }

    getBookTitle(): string {
        return this.repository.getBook(this.bookId).title;
    }

    getComments(): Comment[] {
        let pageIndex = (this.selectedPage - 1) * this.elementsPerPage;
        return this.repository.getCommentsByBookId(this.bookId).slice(pageIndex, pageIndex + this.elementsPerPage);
    }

    deleteComment(id: string) {
        this.repository.deleteComment(id);
    }

    changePage(newPage: number) {
        this.selectedPage = newPage;
    }

    changePageSize(newSize: number) {
        this.elementsPerPage = Number(newSize);
        this.changePage(1);
    }

    get pageCount(): number {
        return Math.ceil(this.repository.getBook(this.bookId).comments.length / this.elementsPerPage);
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
