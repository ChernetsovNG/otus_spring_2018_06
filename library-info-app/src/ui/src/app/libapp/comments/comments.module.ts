import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {ModelModule} from "../../model/model.module";
import {RouterModule} from "@angular/router";
import {CommentsComponent} from "./comments.component";
import {CounterModule} from "../../model/counter.module";

@NgModule({
    imports: [ModelModule, BrowserModule, FormsModule, RouterModule, CounterModule],
    declarations: [CommentsComponent],
    exports: [CommentsComponent]
})
export class CommentsModule {
}
