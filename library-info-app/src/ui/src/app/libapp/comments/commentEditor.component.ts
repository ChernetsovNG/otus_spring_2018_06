import {Component} from "@angular/core";
import {DataRepository} from "../../model/data.repository";
import {ActivatedRoute, Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {Comment} from "../../model/comment.model";

@Component({
    moduleId: module.id,
    templateUrl: "commentEditor.component.html"
})
export class CommentEditorComponent {
    editing: boolean = false;
    bookId: string;
    comment: Comment = new Comment(null, null, new Date());

    constructor(private repository: DataRepository, private router: Router, activeRoute: ActivatedRoute) {
        this.bookId = activeRoute.snapshot.params["bookId"];
        this.editing = activeRoute.snapshot.params["mode"] == "edit";
        if (this.editing) {
            Object.assign(this.comment, repository.getComment(activeRoute.snapshot.params["id"]));
        }
    }

    save(form: NgForm) {
        console.log(this.bookId);
        this.comment.bookId = this.bookId;
        this.repository.saveComment(this.comment);
        this.router.navigateByUrl("/comments/books/" + this.bookId);
    }

}
