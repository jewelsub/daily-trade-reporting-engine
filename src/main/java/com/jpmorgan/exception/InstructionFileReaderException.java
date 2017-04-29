package com.jpmorgan.exception;

public class InstructionFileReaderException extends CustomException {

    public InstructionFileReaderException(String errorMessage, String fileName) {
        super(errorMessage + ", file path " + fileName);
    }
}
