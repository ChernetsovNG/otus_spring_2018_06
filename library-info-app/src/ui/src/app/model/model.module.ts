import {NgModule} from "@angular/core";
import {DataRepository} from "./data.repository";
import {AuthorRepository} from "./author.repository";
import {GenreRepository} from "./genre.repository";
import {StaticDataSource} from "./static.datasource";
import {Cart} from "./cart.model";
import {Order} from "./order.model";
import {OrderRepository} from "./order.repository";
import {HttpClientModule} from "@angular/common/http";
import {RestDataSource} from "./rest.datasource";
import {BookService} from "../libapp/books/book.service";

@NgModule({
    imports: [HttpClientModule],
    providers: [DataRepository, AuthorRepository, GenreRepository, Cart, Order, OrderRepository,
        BookService, {provide: StaticDataSource, useClass: RestDataSource}]
})
export class ModelModule {
}
