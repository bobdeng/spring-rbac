package cn.bobdeng.rbac.archtype;

import lombok.Getter;

import java.util.List;

public class Page<T> {
    @Getter
    List<T> elements;
    int totalPage;
    long totalElements;
    int number;

    public Page(List<T> elements, int totalPage, long totalElements, int number) {
        this.elements = elements;
        this.totalPage = totalPage;
        this.totalElements = totalElements;
        this.number = number;
    }
}
