import {Injectable} from "@angular/core";
import {StaticDatasource} from "./static.datasource";
import {Author} from "./author.model";

@Injectable()
export class AuthorRepository {
    private authors: Author[] = [];

    constructor(private dataSource: StaticDatasource) {
        dataSource.getAuthors().subscribe(data => {
            this.authors = data;
        });
    }

    getAuthors(): Author[] {
        return this.authors;
    }

    getAuthor(id: string): Author {
        return this.authors.find(a => a.id == id);
    }
}
