package br.com.alura.screenmatch.service;

public interface IConvertData {
  <T> T getData(String json, Class<T> referenceClass);
}
