import {Cart} from "../model/cart.model";
import {Component} from "@angular/core";

@Component({
    selector: "cart-summary",
    moduleId: module.id,
    templateUrl: "cartSummary.component.html"
})
export class CartSummaryComponent {

    constructor(public cart: Cart) {
    }
}
