import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from "./app.component";
import {BooksModule} from "./libapp/books/books.module";
import {RouterModule} from "@angular/router";
import {BooksComponent} from "./libapp/books/books.component";
import {CartDetailComponent} from "./libapp/cart/cartDetail.component";
import {CheckoutComponent} from "./libapp/cart/checkout.component";
import {StoreFirstGuard} from "./storeFirst.guard";
import {CommentsComponent} from "./libapp/comments/comments.component";
import {CommentsModule} from "./libapp/comments/comments.module";
import {AuthorsModule} from "./libapp/authors/authors.module";
import {AuthorsComponent} from "./libapp/authors/authors.component";
import {GenresComponent} from "./libapp/genres/genres.component";
import {GenresModule} from "./libapp/genres/genres.module";

@NgModule({
    imports: [BrowserModule, BooksModule, CommentsModule, AuthorsModule, GenresModule,
        RouterModule.forRoot([
            {path: "books", component: BooksComponent, canActivate: [StoreFirstGuard]},
            {path: "authors", component: AuthorsComponent, canActivate: [StoreFirstGuard]},
            {path: "genres", component: GenresComponent, canActivate: [StoreFirstGuard]},
            {path: "cart", component: CartDetailComponent, canActivate: [StoreFirstGuard]},
            {path: "checkout", component: CheckoutComponent, canActivate: [StoreFirstGuard]},
            {path: "comments/books/:bookId", component: CommentsComponent, canActivate: [StoreFirstGuard]},
            {path: "**", redirectTo: "/books"}
        ])],
    providers: [StoreFirstGuard],
    declarations: [AppComponent],
    bootstrap: [AppComponent]
})
export class AppModule {
}
