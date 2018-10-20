import {Component} from "@angular/core";
import {Router} from "@angular/router";

@Component({
    moduleId: module.id,
    templateUrl: "admin.component.html"
})
export class AdminComponent {

    constructor(private router: Router) {
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
