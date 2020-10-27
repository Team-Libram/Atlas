package managers;

import consts.Genre;
import consts.StatusCode;
import entities.identity.Book;
import globals.Globals;
import models.AtlasResult;
import models.BookModel;

public class BookManager {
    public AtlasResult addBook(BookModel model) {
        assert  model != null : "The provided model is null!";

        if (model.getTitle() == null || model.getTitle().trim().isEmpty()) {
            return AtlasResult.Failure(StatusCode.InvalidModel, "The provided book title is invalid.");
        }

        if (model.getAuthor() == null || model.getAuthor().trim().isEmpty()) {
            return AtlasResult.Failure(StatusCode.InvalidModel, "The provided book author is invalid.");
        }

        if (model.getGenre() == null || !Genre.contains(model.getGenre().name())) {
            return AtlasResult.Failure(StatusCode.InvalidModel, "The provided book genre is invalid.");
        }

        Book book = new Book(model.getTitle(), model.getAuthor(), model.getGenre());

        try {
            Globals.entityManager.getTransaction().begin();
            Globals.entityManager.persist(book);
            Globals.entityManager.getTransaction().commit();

            return AtlasResult.Success();
        } catch (Exception e) {
            return AtlasResult.Failure(StatusCode.UnknownError, e.getLocalizedMessage());
        }
    }
}