import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from "@angular/router";
import {BooksComponent} from "./libapp/books/books.component";
import {Injectable} from "@angular/core";

@Injectable()
export class StoreFirstGuard {
    private firstNavigation = true;

    constructor(private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        if (this.firstNavigation) {
            this.firstNavigation = false;
            if (route.component != BooksComponent) {
                this.router.navigateByUrl("/");
                return false;
            }
        }
        return true;
    }
}
