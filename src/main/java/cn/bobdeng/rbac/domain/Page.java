package cn.bobdeng.rbac.domain;

import lombok.Data;

import java.util.List;
@Data
public class Page <T>{
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
