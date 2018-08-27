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

    public comments: Comment[];

    constructor(private repository: DataRepository, private route: ActivatedRoute, private router: Router) {
    }

    getComments(): Comment[] {
        const id = this.route.snapshot.paramMap.get("bookId");
        let pageIndex = (this.selectedPage - 1) * this.elementsPerPage;
        this.comments = this.repository.getBook(id).comments.slice(pageIndex, pageIndex + this.elementsPerPage);
        return this.comments;
    }

    deleteComment(id: string) {
        this.repository.deleteComment(id)
            .subscribe(c => {
                this.comments.splice(this.comments.findIndex(c => c.id == id), 1);
            });
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
