import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {ModelModule} from "../../model/model.module";
import {RouterModule} from "@angular/router";
import {CommentsTableComponent} from "./commentsTable.component";
import {CounterModule} from "../../model/counter.module";
import {CommentEditorComponent} from "./commentEditor.component";

@NgModule({
    imports: [ModelModule, BrowserModule, FormsModule, RouterModule, CounterModule],
    declarations: [CommentsTableComponent, CommentEditorComponent],
    exports: [CommentsTableComponent, CommentEditorComponent]
})
export class CommentsModule {
}
