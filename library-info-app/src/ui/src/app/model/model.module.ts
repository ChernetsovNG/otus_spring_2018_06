import {NgModule} from "@angular/core";
import {BookRepository} from "./book.repository";
import {AuthorRepository} from "./author.repository";
import {GenreRepository} from "./genre.repository";
import {StaticDataSource} from "./static.datasource";
import {Cart} from "./cart.model";
import {Order} from "./order.model";
import {OrderRepository} from "./order.repository";

@NgModule({
    providers: [BookRepository, AuthorRepository, GenreRepository, StaticDataSource,
        Cart, Order, OrderRepository]
})
export class ModelModule {
}
