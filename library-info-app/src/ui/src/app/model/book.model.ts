import {Author} from "./author.model";
import {Genre} from "./genre.model";
import {Comment} from "./comment.model";

export class Book {

    constructor(
        public id: string,
        public title: string,
        public authors: Author[],
        public genres: Genre[],
        public comments?: Comment[]) {
    }

    getAuthorsNames() {
        return this.authors.map(author => author.name).join(", ")
    }

    getGenresNames() {
        return this.genres.map(genre => genre.name).join(", ")
    }

    getComments() {
        return this.comments.map(comment => comment.text)
    }
}
