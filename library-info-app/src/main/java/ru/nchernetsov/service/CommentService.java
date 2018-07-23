package ru.nchernetsov.service;

public interface CommentService {

    /**
     * Добавить комментарий к книге
     *
     * @param bookTitle   - название книги
     * @param commentText - текст комментария
     */
    void addCommentToBook(String bookTitle, String commentText);

}
