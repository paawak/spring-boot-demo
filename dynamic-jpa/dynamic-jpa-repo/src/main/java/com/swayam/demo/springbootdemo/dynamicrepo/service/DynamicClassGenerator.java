package com.swayam.demo.springbootdemo.dynamicrepo.service;

public class DynamicClassGenerator {

    /***
     * Creates the below class dynamically and loads it into the ClassLoader as
     * well as saves the .class file on the disk:
     * 
     * <pre>
     * &#64;Entity
     * &#64;Table(name = "book")
     * public class Book extends BookTemplateImpl {
     * 
     * }
     * </pre>
     */
    public void createJpaEntity() {

    }

    /***
     * Creates the below class dynamically and loads it into the ClassLoader as
     * well as saves the .class file on the disk:
     * 
     * <pre>
     * public interface BookDao extends BookDaoTemplate, CrudRepository&lt;Book, Integer&gt; {
     * 
     *     &#64;Override
     *     &#64;Transactional
     *     &#64;Modifying
     *     &#64;Query("update Book set author.id = :authorId where id = :bookId")
     *     int updateAuthor(int bookId, int authorId);
     * 
     * }
     * </pre>
     */
    public void createJpaRepository() {

    }

}
