package com.appsball.rapidpoll.commons.communication.response;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class ResponseContainer<T> {
   public T result;
   public String status;
   public List<String> messages;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ResponseContainer<?> that = (ResponseContainer<?>) o;

      if (result != null ? !result.equals(that.result) : that.result != null) return false;
      if (status != null ? !status.equals(that.status) : that.status != null) return false;
      return !(messages != null ? !messages.equals(that.messages) : that.messages != null);

   }

   @Override
   public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
   }

   @Override
   public String toString() {
      return "ResponseContainer{" +
              "result=" + result +
              ", status='" + status + '\'' +
              ", messages=" + messages +
              '}';
   }
}
