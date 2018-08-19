import { Component } from '@angular/core';
import { Model, Book, Author, Genre } from './model';

@Component({
  selector: 'library-info-app',
  templateUrl: './app.component.html'
})
export class AppComponent {
  model = new Model();

  getBooks() {
    return this.model.books;
  }

  getAuthors() {
    return this.model.authors;
  }

  getGenres() {
    return this.model.genres;
  }

  addBook(bookTitle: string) {
    if (bookTitle != "") {
      this.model.books.push(new Book(null, bookTitle, [], [], []));
    }
  }

}
