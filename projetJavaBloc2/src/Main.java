//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import model.Book;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "root");
            /*String sqlInstruction = "insert into book (isbn, title, pages, for_adult) values (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, "3146");
            preparedStatement.setString(2, "hunger game");
            preparedStatement.setInt(3, 75);
            preparedStatement.setBoolean(4, false);
            int nbUpdatedLines = preparedStatement.executeUpdate();
            System.out.println(nbUpdatedLines);*/

            PreparedStatement preparedStatement;
            String sqlInstruction;



            /*String sqlInstruction = "insert into book (isbn) values (?)";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.executeUpdate();*/
            /*book.setPages(54);
            System.out.println(book.getPages());
            String sqlInstruction2;
            if (book.getPages() != null) {
                sqlInstruction2 = "update book set pages = ? where isbn = ? ";
                preparedStatement = connection.prepareStatement(sqlInstruction2);
                preparedStatement.setInt(1, book.getPages());
                preparedStatement.setString(2,book.getIsbn());
                preparedStatement.executeUpdate();
            }*/

            /*book.setTitle("Jordan");

            if (book.getTitle() != null) {
                sqlInstruction = "update book set title = ? where isbn = ? ";
                preparedStatement = connection.prepareStatement(sqlInstruction);
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setString(2, book.getIsbn());
                preparedStatement.executeUpdate();
            }

            String getBookInstruction = "select * from book where isbn = ? ";
            preparedStatement = connection.prepareStatement(getBookInstruction);
            preparedStatement.setString(1, book.getIsbn());

            ResultSet data = preparedStatement.executeQuery();
            data.next();
            String title = data.getString("title");

            if (!data.wasNull()) {
                System.out.println(title);
            }*/


            sqlInstruction = "select * from book";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();

            Book book;
            int pages;
            String title;
            LocalDate edition;
            Boolean forAdult;
            ArrayList<Book> books = new ArrayList<>();

            while (data.next()) {
                book = new Book(data.getString("isbn"));
                pages = data.getInt("pages");

                if (!data.wasNull()){
                    book.setPages(pages);
                }

                title = data.getString("title");
                if (!data.wasNull()){
                    book.setTitle(title);
                }


                forAdult = data.getBoolean("for_adult");
                if (!data.wasNull()){
                    book.setForAdult(forAdult);
                }

                books.add(book);
            }



            book = books.get(0);

            LocalDate date = LocalDate.now();
            book.setEditionDate(date);


            sqlInstruction = "update book set edition_date = ? where isbn = ?";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setDate(1, java.sql.Date.valueOf(book.getEditionDate()));
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.executeUpdate();
            System.out.println(book);

            sqlInstruction = "select * from book limit 1";
            preparedStatement = connection.prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();

            data.next();

            java.sql.Date editionDate = data.getDate("edition_date");

            if (!data.wasNull()){
                System.out.println(editionDate);
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}