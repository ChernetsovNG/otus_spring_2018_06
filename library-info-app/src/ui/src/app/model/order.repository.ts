import {Order} from "./order.model";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {RestDataSource} from "./rest.datasource";

@Injectable()
export class OrderRepository {
    private orders: Order[] = [];

    constructor(private dataSource: RestDataSource) {
    }

    getOrders(): Order[] {
        return this.orders;
    }

    public saveOrder(order: Order): Observable<Order> {
        return this.dataSource.saveOrder(order);
    }
}
