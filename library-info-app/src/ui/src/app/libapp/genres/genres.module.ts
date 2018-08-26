import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {ModelModule} from "../../model/model.module";
import {GenresComponent} from "./genres.component";
import {RouterModule} from "@angular/router";
import {CounterModule} from "../../model/counter.module";

@NgModule({
    imports: [ModelModule, BrowserModule, FormsModule, RouterModule, CounterModule],
    declarations: [GenresComponent],
    exports: [GenresComponent]
})
export class GenresModule {
}
