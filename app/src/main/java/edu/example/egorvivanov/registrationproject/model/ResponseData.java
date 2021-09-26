package edu.example.egorvivanov.registrationproject.model;

// Специальный класс, т.к. от GET запроса к API мы получаем модель "data"
public class ResponseData<T> {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseData(T data) {
        this.data = data;
    }
}