import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from "./app.component";
import {BooksModule} from "./libapp/books.module";

@NgModule({
    imports: [BrowserModule, BooksModule],
    declarations: [AppComponent],
    bootstrap: [AppComponent]
})
export class AppModule {
}
