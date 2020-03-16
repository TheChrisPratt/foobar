package com.anodyzed.foobar.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * GsonProvider - Google JavaScript Object Notation Provider
 *
 * @author Chris Pratt
 * @since 2020-03-07
 */
@Provider
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class GsonProvider<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {

  /**
   * ISO 8601 compliant date time format (requires Java 7+)
   */
  public final static String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";

  private final Gson gson;

  public GsonProvider () {
    gson = getGsonBuilder().create();
  } //GsonProvider

  public static GsonBuilder getGsonBuilder () {
    return new GsonBuilder().setDateFormat(DATE_TIME_FORMAT);
  } //getGsonBuilder

  @Override
  public long getSize (T t,Class<?> type,Type genericType,Annotation[] annotations,MediaType mediaType) {
    return -1;
  } //getSize

  @Override
  public boolean isWriteable (Class<?> type,Type genericType,Annotation[] annotations,MediaType mediaType) {
    return true;
  } //isWriteable

  @Override
  public void writeTo(T object,Class<?> type,Type genericType,Annotation[] annotations,MediaType mediaType,MultivaluedMap<String,Object> httpHeaders,OutputStream entityStream) throws WebApplicationException {
    try(PrintWriter printWriter = new PrintWriter(entityStream)) {
      printWriter.write(gson.toJson(object));
      printWriter.flush();
    }
  } //writeTo

  @Override
  public boolean isReadable (Class<?> type,Type genericType,Annotation[] annotations,MediaType mediaType) {
    return true;
  } //isReadable

  @Override
  public T readFrom (Class<T> type,Type genericType,Annotation[] annotations,MediaType mediaType,MultivaluedMap<String,String> httpHeaders,InputStream entityStream) throws IOException, WebApplicationException {
    try(InputStreamReader reader = new InputStreamReader(entityStream,StandardCharsets.UTF_8)) {
      return gson.fromJson(reader,type);
    }
  } //readFrom

} //*GsonProvider
