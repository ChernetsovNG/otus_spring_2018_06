import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {Component} from "@angular/core";

@Component({
    moduleId: module.id,
    templateUrl: "auth.component.html"
})
export class AuthComponent {
    public username: string;
    public password: string;
    public errorMessage: string;

    constructor(private router: Router) {
    }

    authenticate(form: NgForm) {
        if (form.valid) {
            this.router.navigateByUrl("/admin/main");
        } else {
            this.errorMessage = "Form Data Invalid";
        }
    }
}
