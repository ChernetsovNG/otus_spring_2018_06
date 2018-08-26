import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {ModelModule} from "../../model/model.module";
import {GenresTableComponent} from "./genresTable.component";
import {RouterModule} from "@angular/router";
import {CounterModule} from "../../model/counter.module";

@NgModule({
    imports: [ModelModule, BrowserModule, FormsModule, RouterModule, CounterModule],
    declarations: [GenresTableComponent],
    exports: [GenresTableComponent]
})
export class GenresModule {
}
