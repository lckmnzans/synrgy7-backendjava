package com.synrgy.binarfud.Binarfud.payload;

public sealed class Response {
    private final String status;

    protected Response(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static final class Success extends Response {
        private final Object data;

        public Success(Object data) {
            super("success");
            this.data = data;
        }

        public Object getData() {
            return data;
        }
    }

    public static final class Error extends Response {
        private final String message;

        public Error(String message) {
            super("error");
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static final class SuccessNull extends Response {
        private final String message;

        public SuccessNull(String message) {
            super("success");
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}