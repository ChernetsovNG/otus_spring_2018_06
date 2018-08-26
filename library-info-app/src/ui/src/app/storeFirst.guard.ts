import {ActivatedRouteSnapshot, Router, RouterStateSnapshot} from "@angular/router";
import {BooksTableComponent} from "./libapp/books/booksTable.component";
import {Injectable} from "@angular/core";

@Injectable()
export class StoreFirstGuard {
    private firstNavigation = true;

    constructor(private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        if (this.firstNavigation) {
            this.firstNavigation = false;
            if (route.component != BooksTableComponent) {
                this.router.navigateByUrl("/");
                return false;
            }
        }
        return true;
    }
}
