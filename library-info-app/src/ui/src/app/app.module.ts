import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from "./app.component";
import {BooksModule} from "./libapp/books.module";
import {RouterModule} from "@angular/router";
import {BooksComponent} from "./libapp/books.component";
import {CartDetailComponent} from "./libapp/cartDetail.component";
import {CheckoutComponent} from "./libapp/checkout.component";
import {StoreFirstGuard} from "./storeFirst.guard";

@NgModule({
    imports: [BrowserModule, BooksModule,
        RouterModule.forRoot([
            {path: "books", component: BooksComponent, canActivate: [StoreFirstGuard]},
            {path: "cart", component: CartDetailComponent, canActivate: [StoreFirstGuard]},
            {path: "checkout", component: CheckoutComponent, canActivate: [StoreFirstGuard]},
            {path: "**", redirectTo: "/books"}
        ])],
    providers: [StoreFirstGuard],
    declarations: [AppComponent],
    bootstrap: [AppComponent]
})
export class AppModule {
}
