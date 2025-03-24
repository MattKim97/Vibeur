package learn.vibeur.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Result<T> {

    private ArrayList<String> messages = new ArrayList<>();

    private ResultType resultType = ResultType.SUCCESS;
    private T payload;

    public boolean isSuccess() {
        return this.resultType == ResultType.SUCCESS;
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message) {
        this.resultType = ResultType.INVALID;
        messages.add(message);
    }

    public void addErrorMessage(String format, ResultType type, Object... args) {
        this.resultType = type;
        messages.add(String.format(format, args));
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public ResultType getResultType() {
        return resultType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result<?> result = (Result<?>) o;

        if (!Objects.equals(messages, result.messages)) return false;
        return Objects.equals(payload, result.payload);
    }

    @Override
    public int hashCode() {
        int result = messages != null ? messages.hashCode() : 0;
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}
