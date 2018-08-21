import {Injectable} from "@angular/core";
import {StaticDatasource} from "./static.datasource";
import {Genre} from "./genre.model";

@Injectable()
export class GenreRepository {
    private genres: Genre[] = [];

    constructor(private dataSource: StaticDatasource) {
        dataSource.getGenres().subscribe(data => {
            this.genres = data;
        });
    }

    getGenres(): Genre[] {
        return this.genres;
    }

    getGenre(id: string): Genre {
        return this.genres.find(g => g.id == id);
    }
}
