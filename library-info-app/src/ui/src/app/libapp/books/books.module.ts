import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {ModelModule} from "../../model/model.module";
import {BooksTableComponent} from "./booksTable.component";
import {CartSummaryComponent} from "../cart/cartSummary.component";
import {CartDetailComponent} from "../cart/cartDetail.component";
import {CheckoutComponent} from "../cart/checkout.component";
import {RouterModule} from "@angular/router";
import {CounterModule} from "../../model/counter.module";
import {BookEditorComponent} from "./bookEditor.component";

@NgModule({
    imports: [ModelModule, BrowserModule, FormsModule, RouterModule, CounterModule],
    declarations: [BooksTableComponent, BookEditorComponent, CartSummaryComponent, CartDetailComponent, CheckoutComponent],
    exports: [BooksTableComponent, BookEditorComponent, CartDetailComponent, CheckoutComponent]
})
export class BooksModule {
}
