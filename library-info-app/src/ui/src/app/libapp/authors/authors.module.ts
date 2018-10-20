import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {ModelModule} from "../../model/model.module";
import {AuthorsTableComponent} from "./authorsTable.component";
import {RouterModule} from "@angular/router";
import {CounterModule} from "../../model/counter.module";
import {AuthorEditorComponent} from "./authorEditor.component";

@NgModule({
    imports: [ModelModule, BrowserModule, FormsModule, RouterModule, CounterModule],
    declarations: [AuthorsTableComponent, AuthorEditorComponent],
    exports: [AuthorsTableComponent, AuthorEditorComponent]
})
export class AuthorsModule {
}
