import {NgModule} from "@angular/core";
import {CounterDirective} from "./counter.directive";

@NgModule({
    declarations: [CounterDirective],
    exports: [CounterDirective]
})
export class CounterModule {
}
