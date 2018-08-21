import {NgModule} from "@angular/core";
import {BookRepository} from "./book.repository";
import {AuthorRepository} from "./author.repository";
import {GenreRepository} from "./genre.repository";
import {StaticDatasource} from "./static.datasource";

@NgModule({
    providers: [BookRepository, AuthorRepository, GenreRepository, StaticDatasource]
})
export class ModelModule {
}
