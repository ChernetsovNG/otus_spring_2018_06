import {Component} from "@angular/core";
import {DataRepository} from "../../model/data.repository";
import {ActivatedRoute, Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {Genre} from "../../model/genre.model";

@Component({
    moduleId: module.id,
    templateUrl: "genreEditor.component.html"
})
export class GenreEditorComponent {
    editing: boolean = false;
    genre: Genre = new Genre(null, null);

    constructor(private repository: DataRepository, private router: Router, activeRoute: ActivatedRoute) {
        this.editing = activeRoute.snapshot.params["mode"] == "edit";
        if (this.editing) {
            Object.assign(this.genre, repository.getGenre(activeRoute.snapshot.params["id"]));
        }
    }

    save(form: NgForm) {
        this.repository.saveGenre(this.genre);
        this.router.navigateByUrl("/genres");
    }

}
