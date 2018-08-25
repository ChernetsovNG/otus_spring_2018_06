import {NgModule} from "@angular/core";
import {BookRepository} from "./book.repository";
import {AuthorRepository} from "./author.repository";
import {GenreRepository} from "./genre.repository";
import {StaticDatasource} from "./static.datasource";
import {Cart} from "./cart.model";

@NgModule({
    providers: [BookRepository, AuthorRepository, GenreRepository, StaticDatasource, Cart]
})
export class ModelModule {
}
