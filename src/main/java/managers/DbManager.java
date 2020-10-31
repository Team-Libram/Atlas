package managers;

import consts.Genre;
import consts.UserType;
import entities.identity.Account;
import entities.Book;
import entities.identity.IEntity;
import globals.Globals;
import models.AccountModel;
import results.AtlasResult;
import models.BookModel;
import results.IdentityResult;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DbManager {
    private final EntityManager entityManager;

    public DbManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void seed() {
        System.out.println("Started initial seed...");

            if (!this.entityManager.isOpen()) {
                this.entityManager.getTransaction().begin();
            }

        if (this.getUserByUsername("admin") == null) {
            AccountModel user = new AccountModel("admin", null, null, UserType.Administrator);

            IdentityResult result = Globals.identityManager.create(user, "admin");
            if (!result.succeeded) {
                System.out.println(result);
            }
        }

        if (this.getBooksByTitle("Flight 19").stream().findFirst().isEmpty()) {
            BookModel bookModel = new BookModel("Flight 19", "Grant Finnegan", Genre.Fiction);
            AtlasResult result = Globals.bookManager.addBook(bookModel);
            if (!result.succeeded) {
                System.out.println(result);
            }
        }

        System.out.println("Initial seed successful.");
    }

    public Account getUserById(String id) {
        Query query = this.entityManager.createQuery("select account from Account account where account.id = :id");
        query.setParameter("id", Integer.parseInt(id));

        return (Account) query.getSingleResult();
    }

    public AccountModel getUserByUsername(String username) {
        Query query = this.entityManager.createQuery("select account from Account account where account.username = :username");
        query.setParameter("username", username);
        try {
            Account account = (Account) query.getSingleResult();

            return new AccountModel(account.getId(), account.getUsername(), account.getName(), account.getAge(), account.getType());
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<AccountModel> getUsersByName(String name) {
        Query query = this.entityManager.createQuery("select account from Account account where account.name = :name");
        query.setParameter("name", name);

        List<Account> accounts = (List<Account>) this.collectEntities(query);
        return accounts.stream().map(AccountModel::from).collect(Collectors.toList());
    }

    public List<AccountModel> getUsersByAge(Long age) {
        Query query = this.entityManager.createQuery("select account from Account account where account.age = :age");
        query.setParameter("age", age);

        List<Account> accounts = (List<Account>) this.collectEntities(query);
        return accounts.stream().map(AccountModel::from).collect(Collectors.toList());
    }

    public List<AccountModel> getUsersByType(UserType type) {
        Query query = this.entityManager.createQuery("select account from Account account where account.type = :type");
        query.setParameter("type", type);

        List<Account> accounts = (List<Account>) this.collectEntities(query);
        return accounts.stream().map(AccountModel::from).collect(Collectors.toList());
    }

    public List<BookModel> getBooksByTitle(String title) {
        Query query = this.entityManager.createQuery("select book from Book book where book.title = :title");
        query.setParameter("title", title);

        List<Book> books = (List<Book>) this.collectEntities(query);
        return books.stream().map(BookModel::from).collect(Collectors.toList());
    }

    public List<BookModel> getBooksByAuthor(String author) {
        Query query = this.entityManager.createQuery("select book from Book book where book.author = :author");
        query.setParameter("author", author);

        List<Book> books = (List<Book>) this.collectEntities(query);
        return books.stream().map(BookModel::from).collect(Collectors.toList());
    }

    public List<BookModel> getBooksByGenre(Genre genre) {
        Query query = this.entityManager.createQuery("select book from Book book where book.genre = :genre");
        query.setParameter("genre", genre);

        List<Book> books = (List<Book>) this.collectEntities(query);
        return books.stream().map(BookModel::from).collect(Collectors.toList());
    }

    public List<BookModel> getBooksWithIntegrityOver(double integrity) {
        Query query = this.entityManager.createQuery("select book from Book book where book.genre >= :integrity");
        query.setParameter("integrity", integrity);

        List<Book> books = (List<Book>) this.collectEntities(query);
        return books.stream().map(BookModel::from).collect(Collectors.toList());
    }

    public List<BookModel> getBooksWithIntegrityUnder(double integrity) {
        Query query = this.entityManager.createQuery("select book from Book book where book.genre < :integrity");
        query.setParameter("integrity", integrity);

        List<Book> books = (List<Book>) this.collectEntities(query);
        return books.stream().map(BookModel::from).collect(Collectors.toList());
    }

    private <E> List<?> collectEntities(Query query) {
        List<E> result = query.getResultList();
        if (result == null) {
            return Collections.emptyList();
        }

        return result;
    }

    public void insertEntity(IEntity entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(entity);
        this.entityManager.getTransaction().commit();
    }
}
