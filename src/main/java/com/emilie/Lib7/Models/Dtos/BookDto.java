package com.emilie.Lib7.Models.Dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
public class BookDto implements Serializable/*, List<BookDto> */{

    public static final long serialVersionUID = 1L;

    private Long bookId;
    private String title;
    private String isbn;
    private String summary;
    private AuthorDto authorDto;
    private Set<CopyDto> copyDtos = new HashSet<>(); //instancie le tableau vide


   /* @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<BookDto> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(BookDto bookDto) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends BookDto> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends BookDto> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public BookDto get(int index) {
        return null;
    }

    @Override
    public BookDto set(int index, BookDto element) {
        return null;
    }

    @Override
    public void add(int index, BookDto element) {

    }

    @Override
    public BookDto remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<BookDto> listIterator() {
        return null;
    }

    @Override
    public ListIterator<BookDto> listIterator(int index) {
        return null;
    }

    @Override
    public List<BookDto> subList(int fromIndex, int toIndex) {
        return null;
    }*/
}
