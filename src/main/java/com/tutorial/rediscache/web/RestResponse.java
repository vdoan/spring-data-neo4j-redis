package com.tutorial.rediscache.web;

import com.tutorial.rediscache.constant.ResultCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Collections;

@Data
public class RestResponse implements Serializable {

    private Integer status;

    private Integer resultCode;
    private String message;

    private Object data;


    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Object data) {
        Builder builder = new Builder();
        builder.data(data);
        return builder;
    }

    public static class Builder {
        private Integer status;
        private Integer resultCode;
        private String message;
        private Object data;

        private Builder() {
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }


        public Builder resultCode(int resultCode) {
            this.resultCode = resultCode;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public RestResponse build() {
            RestResponse restResponse = new RestResponse();
            if (status == null) {
                status = HttpStatus.OK.value();
            }
            restResponse.setStatus(this.status);


            if (resultCode == null) {
                resultCode = ResultCode.SUCCESS.getId();
                message = "Success";
            }
            restResponse.setResultCode(resultCode);

            if (message != null) {
                restResponse.setMessage(message);
            } else {
                restResponse.setMessage("");
            }

            if (data != null) {
                restResponse.setData(data);
            } else {
                restResponse.setData(Collections.EMPTY_MAP);
            }

            return restResponse;
        }
    }
}
