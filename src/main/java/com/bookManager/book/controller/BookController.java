package com.bookManager.book.controller;

import com.bookManager.book.model.Book;
import com.bookManager.book.model.User;
import com.bookManager.book.service.BookService;
import com.bookManager.book.service.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ilovejava1314
 * @date 2019/7/21 12:30
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 加载所有书
     * @param model
     * @return
     */
    @RequestMapping(path = {"/index"},method = RequestMethod.GET)
    public String bookList(Model model){
        User host = hostHolder.getUser();
        if (host!=null){
            System.out.println(host.getName());
            model.addAttribute("host",host);
        }
        loadAllBooksView(model);
        return "book/books";
    }

    /**
     * 跳转到addbook页面
     * @return
     */
    @RequestMapping(path = {"/books/add"},method = RequestMethod.GET)
    public String addBook(){
        return "book/addbook";
    }

    /**
     * 添加书籍
     * @param name
     * @param author
     * @param price
     * @return
     */
    @RequestMapping(path = {"/books/add/do"},method = RequestMethod.POST)
    public String doAddBook(
            @RequestParam("name") String name,
            @RequestParam("author") String author,
            @RequestParam("price") String price
    ){
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        bookService.addBook(book);
        return "redirect:/index";
    }

    /**
     * 将书籍放入回收站
     * @param bookId
     * @return
     */
    @RequestMapping(path = {"/books/{bookId:[0-9]+}/delete"},method = RequestMethod.GET)
    public String deleteBook(
            @PathVariable("bookId") int bookId
    ){
        bookService.deleteBook(bookId);
        return "redirect:/index";
    }

    /**
     * 从回收站恢复书籍
     * @param bookId
     * @return
     */
    @RequestMapping(path = {"/books/{bookId:[0-9]+}/recover"},method = RequestMethod.GET)
    public String recoverBook(
            @PathVariable("bookId") int bookId
    ){
        bookService.recoverBook(bookId);
        return "redirect:/index";
    }


    /**
     * 获取所有书列表
     * @param model
     */
    private void loadAllBooksView(Model model) {
        model.addAttribute("books",bookService.getAllBooks());
    }

}
