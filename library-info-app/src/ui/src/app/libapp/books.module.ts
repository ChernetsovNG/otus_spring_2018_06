import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {ModelModule} from "../model/model.module";
import {BooksComponent} from "./books.component";
import {CounterDirective} from "./counter.directive";

@NgModule({
    imports: [ModelModule, BrowserModule, FormsModule],
    declarations: [BooksComponent, CounterDirective],
    exports: [BooksComponent]
})
export class BooksModule {
}
