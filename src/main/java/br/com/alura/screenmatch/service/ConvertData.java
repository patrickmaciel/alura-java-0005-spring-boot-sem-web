package br.com.alura.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public <T> T getData(String json, Class<T> referenceClass) {
    try {
      return mapper.readValue(json, referenceClass);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
