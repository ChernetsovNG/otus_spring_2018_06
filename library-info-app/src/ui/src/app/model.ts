export class Model {
    books: Book[];
    authors: Author[];
    genres: Genre[];
    comments: Comment[];

    constructor() {
        this.authors = [
            new Author(null, "Курт Воннегут", []),
            new Author(null, "Джек Лондон", []),
            new Author(null, "Стивен Кинг", []),
            new Author(null, "Лев Толстой", []),
            new Author(null, "Брюс Эккель", []),
            new Author(null, "Эрнест Хеммингуэй", [])];
        this.genres = [
            new Genre(null, "Фантастика", []),
            new Genre(null, "Приключения", []),
            new Genre(null, "Хоррор", []),
            new Genre(null, "Проза", []),
            new Genre(null, "Программирование", [])];
        this.comments = [];
        this.books = [
            new Book(null, "Бойня номер пять, или Крестовый поход детей", [this.authors[0]], [this.genres[0]], []),
            new Book(null, "Белый клык", [this.authors[1]], [this.genres[1], this.genres[3]], []),
            new Book(null, "Оно", [this.authors[2]], [this.genres[0], this.genres[2]], []),
            new Book(null, "Война и мир", [this.authors[3]], [this.genres[3]], []),
            new Book(null, "Философия Java", [this.authors[4]], [this.genres[4]], []),
            new Book(null, "Праздник, который всегда с тобой", [this.authors[5]], [this.genres[3]], [])];
    }
}

export class Book {
    id: string;
    title: string;
    authors: Author[];
    genres: Genre[];
    comments: Comment[];

    constructor(id, title, authors, genres, comments) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    getAuthorsNames() {
        return this.authors.map(author => author.name)
    }

    getGenresNames() {
        return this.genres.map(genre => genre.name)
    }
}

export class Author {
    id: string;
    name: string;
    bookIds: string[];

    constructor(id, name, bookIds) {
        this.id = id;
        this.name = name;
        this.bookIds = bookIds;
    }
}

export class Genre {
    id: string;
    name: string;
    bookIds: string[];

    constructor(id, name, bookIds) {
        this.id = id;
        this.name = name;
        this.bookIds = bookIds;
    }
}

export class Comment {
    id: string;
    comment: string;
    timestamp: Date;
    bookId: string;

    constructor(id, comment, timestamp, bookId) {
        this.id = id;
        this.comment = comment;
        this.timestamp = timestamp;
        this.bookId = bookId;
    }
}
