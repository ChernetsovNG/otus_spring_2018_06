import {Component} from "@angular/core";
import {Author} from "../../model/author.model";
import {DataRepository} from "../../model/data.repository";
import {ActivatedRoute, Router} from "@angular/router";
import {NgForm} from "@angular/forms";

@Component({
    moduleId: module.id,
    templateUrl: "authorEditor.component.html"
})
export class AuthorEditorComponent {
    editing: boolean = false;
    author: Author = new Author(null, null);

    constructor(private repository: DataRepository, private router: Router, activeRoute: ActivatedRoute) {
        this.editing = activeRoute.snapshot.params["mode"] == "edit";
        if (this.editing) {
            Object.assign(this.author, repository.getAuthor(activeRoute.snapshot.params["id"]));
        }
    }

    save(form: NgForm) {
        this.repository.saveAuthor(this.author);
        this.router.navigateByUrl("/authors");
    }

}
