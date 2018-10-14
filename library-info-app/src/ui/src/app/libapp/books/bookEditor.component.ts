import {Component} from "@angular/core";
import {DataRepository} from "../../model/data.repository";
import {ActivatedRoute, Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {Book} from "../../model/book.model";
import {Author} from "../../model/author.model";
import {Genre} from "../../model/genre.model";

@Component({
    moduleId: module.id,
    templateUrl: "bookEditor.component.html"
})
export class BookEditorComponent {
    editing: boolean = false;
    book: Book = new Book(null, null, null, null);

    constructor(private repository: DataRepository, private router: Router, activeRoute: ActivatedRoute) {
        this.editing = activeRoute.snapshot.params["mode"] == "edit";
        if (this.editing) {
            Object.assign(this.book, repository.getBook(activeRoute.snapshot.params["id"]));
        }
    }

    getAllAuthors(): Author[] {
        return this.repository.getAuthors();
    }

    getAllGenres(): Genre[] {
        return this.repository.getGenres();
    }

    save(form: NgForm) {
        this.repository.saveBook(this.book);
        this.router.navigateByUrl("/books");
    }

}
