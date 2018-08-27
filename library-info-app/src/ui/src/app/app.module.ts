import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from "./app.component";
import {BooksModule} from "./libapp/books/books.module";
import {RouterModule} from "@angular/router";
import {BooksTableComponent} from "./libapp/books/booksTable.component";
import {CartDetailComponent} from "./libapp/cart/cartDetail.component";
import {CheckoutComponent} from "./libapp/cart/checkout.component";
import {StoreFirstGuard} from "./storeFirst.guard";
import {CommentsTableComponent} from "./libapp/comments/commentsTable.component";
import {CommentsModule} from "./libapp/comments/comments.module";
import {AuthorsModule} from "./libapp/authors/authors.module";
import {AuthorsTableComponent} from "./libapp/authors/authorsTable.component";
import {GenresTableComponent} from "./libapp/genres/genresTable.component";
import {GenresModule} from "./libapp/genres/genres.module";
import {AuthorEditorComponent} from "./libapp/authors/authorEditor.component";
import {GenreEditorComponent} from "./libapp/genres/genreEditor.component";
import {CommentEditorComponent} from "./libapp/comments/commentEditor.component";
import {BookEditorComponent} from "./libapp/books/bookEditor.component";

@NgModule({
    imports: [BrowserModule, BooksModule, CommentsModule, AuthorsModule, GenresModule,
        RouterModule.forRoot([
            {path: "books/:mode/:id", component: BookEditorComponent, canActivate: [StoreFirstGuard]},
            {path: "books/:mode", component: BookEditorComponent, canActivate: [StoreFirstGuard]},
            {path: "books", component: BooksTableComponent, canActivate: [StoreFirstGuard]},
            {path: "authors/:mode/:id", component: AuthorEditorComponent, canActivate: [StoreFirstGuard]},
            {path: "authors/:mode", component: AuthorEditorComponent, canActivate: [StoreFirstGuard]},
            {path: "authors", component: AuthorsTableComponent, canActivate: [StoreFirstGuard]},
            {path: "genres/:mode/:id", component: GenreEditorComponent, canActivate: [StoreFirstGuard]},
            {path: "genres/:mode", component: GenreEditorComponent, canActivate: [StoreFirstGuard]},
            {path: "genres", component: GenresTableComponent, canActivate: [StoreFirstGuard]},
            {path: "cart", component: CartDetailComponent, canActivate: [StoreFirstGuard]},
            {path: "checkout", component: CheckoutComponent, canActivate: [StoreFirstGuard]},
            {
                path: "comments/books/:bookId/:mode/:id",
                component: CommentEditorComponent,
                canActivate: [StoreFirstGuard]
            },
            {path: "comments/books/:bookId/:mode", component: CommentEditorComponent, canActivate: [StoreFirstGuard]},
            {path: "comments/books/:bookId", component: CommentsTableComponent, canActivate: [StoreFirstGuard]},
            {path: "admin", loadChildren: "./admin/admin.module#AdminModule", canActivate: [StoreFirstGuard]},
            {path: "**", redirectTo: "/books"}
        ])],
    providers: [StoreFirstGuard],
    declarations: [AppComponent],
    bootstrap: [AppComponent]
})
export class AppModule {
}
